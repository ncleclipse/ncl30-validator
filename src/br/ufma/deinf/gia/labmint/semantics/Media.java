/******************************************************************************
This file is part of the authoring environment in Nested Context Language -
NCL Eclipse.

Copyright: 2007-2008 UFMA/LAWS (Laboratory of Advanced Web Systems), All Rights Reserved.

This program is free software; you can redistribute it and/or modify it under 
the terms of the GNU General Public License version 2 as published by
the Free Software Foundation.

This program is distributed in the hope that it will be useful, but WITHOUT ANY 
WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
PARTICULAR PURPOSE.  See the GNU General Public License version 2 for more 
details.

You should have received a copy of the GNU General Public License version 2
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA

For further information contact:
ncleclipse@laws.deinf.ufma.br
http://www.laws.deinf.ufma.br/ncleclipse
http://www.laws.deinf.ufma.br

 *******************************************************************************/

package br.ufma.deinf.gia.labmint.semantics;

import java.io.File;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Vector;

import org.w3c.dom.Element;

import br.ufma.deinf.gia.labmint.document.NclValidatorDocument;
import br.ufma.deinf.gia.labmint.message.MessageList;
import br.ufma.deinf.laws.util.MultiHashMap;

public class Media extends ElementValidation {

	public Media(NclValidatorDocument doc) {
		super(doc);
		setTypes();
	}

	private MultiHashMap types = null;
	private String idMedia = null;

	public boolean validate(Element eMedia) {
		boolean resultado = true;

		idMedia = eMedia.getAttribute("id");

		// Verifica se o atributo 'descriptor' da <media> aponta para um
		// <descriptor>.
		if (!hasValidMediaDescriptorAttribute(eMedia))
			resultado = false;

		// Verifica se o atributo 'type' da <media> possui um valor valido.
		if (!hasValidMediaTypeAttribute(eMedia))
			resultado = false;

		// Verifica se o atributo 'src' da <media> possui um valor valido.
		if (!hasValidMediaSrcAttribute(eMedia))
			resultado = false;

		//
		// if(!hasValidMediaIDAttribute(eMedia)) resultado = false;

		// Verifica se o atributo 'refer' da <media> aponta para um outro
		// elemento <media>.
		if (!hasValidMediaReferAttribute(eMedia))
			resultado = false;

		// Verifica se o atributo 'newInstace' da <media> possui um valor
		// valido.
		// if(!hasValidMediaNewInstanceAttribute(eMedia)) resultado = false;

		// Verifica se o arquivo especificado no atributo 'src' tem uma extensao
		// valida.
		// if (!hasValidExtension(eMedia)) resultado = false;

		// Verifica se a extensao diverge da definida pelo atributo type.
		if (!hasValidExtensionForMediaType(eMedia))
			resultado = false;

		// Atributo refer não deve ser utilizado com o src simultaneamente
		if (!eMedia.hasAttribute("refer")) {
			// Atributo type é obrigatório se src não for definido
			if (!eMedia.hasAttribute("src")) {
				if (!eMedia.hasAttribute("type")) {
					MessageList.addError(doc.getId(), 4108, eMedia);
					resultado = false;
				}
			}
		} else if (eMedia.hasAttribute("src")) {
			MessageList.addWarning(doc.getId(), 4107, eMedia);
			resultado = false;
		}

		return resultado;
	}

	private boolean hasValidMediaDescriptorAttribute(Element eMedia) {
		if (eMedia.hasAttribute("descriptor")) {
			String idDescriptor = eMedia.getAttribute("descriptor");
			Element element = doc.getElement(idDescriptor);
			if (element == null) {
				Vector<String> args = new Vector<String>();
				args.add(idMedia);
				MessageList.addError(doc.getId(), 4106, eMedia, args);
				return false;
			} else if (element.getTagName().compareTo("descriptor") != 0
					&& element.getTagName().compareTo("descriptorSwitch") != 0) {
				Vector<String> args = new Vector<String>();
				args.add(idMedia);
				MessageList.addError(doc.getId(), 4106, eMedia, args);
				return false;
			}
		}
		return true;
	}

	private boolean hasValidMediaTypeAttribute(Element eMedia) {
		if (eMedia.hasAttribute("type")) {

			String type = eMedia.getAttribute("type");

			if (!types.containsKey(type)) {
				Vector<String> args = new Vector<String>();
				args.add(type);
				MessageList.addWarning(doc.getId(), 4105, eMedia, args);
				return false;
			}
		} else if (!eMedia.hasAttribute("src") && !eMedia.hasAttribute("refer")) {
			MessageList.addError(doc.getId(), 4104, eMedia);
			return false;
		}
		return true;
	}

	private boolean hasValidMediaSrcAttribute(Element eMedia) {
		if (eMedia.hasAttribute("src")) {
			String src = eMedia.getAttribute("src");

			File fMedia;

			fMedia = new File(src);

			if (fMedia.isFile())
				return true;
			else {
				String absolute = doc.getDir().toString() + src;

				fMedia = new File(absolute.substring(7));

				if (!fMedia.isFile()) {
					Vector<String> args = new Vector<String>();
					args.add(src);
					MessageList.addWarning(doc.getId(), 4103, eMedia, args);
					return false;
				}

			}

		}
		return true;
	}

	private boolean hasValidMediaIDAttribute(Element eMedia) {
		// TODO Validar Atributo id
		return true;
	}

	private boolean hasValidMediaReferAttribute(Element eMedia) {
		if (eMedia.hasAttribute("refer")) {

			String idRefer = eMedia.getAttribute("refer");
			Element element = doc.getElement(idRefer);
			if (element == null) {
				Vector<String> args = new Vector<String>();
				args.add(idRefer);
				MessageList.addWarning(doc.getId(), 4102, eMedia, args);
				return false;
			} else if (element.getTagName().compareTo("media") != 0) {
				Vector<String> args = new Vector<String>();
				args.add(idRefer);
				MessageList.addWarning(doc.getId(), 4102, eMedia, args);
				return false;
			} else if (element.hasAttribute("refer")) {
				// cannot refer a media that has a refer too
				Vector<String> args = new Vector<String>();
				args.add(idRefer);
				MessageList.addWarning(doc.getId(), 4109, eMedia, args);
				return false;
			}
		}
		return true;
	}

	private boolean hasValidMediaNewInstanceAttribute(Element eMedia) {
		// TODO Validar Atributo newIsntance
		return true;
	}

	// FIX: fix this function
	private boolean hasValidExtension(Element eMedia) {
		/*
		 * if (eMedia.hasAttribute("src")) { setTypes();
		 * 
		 * String src = eMedia.getAttribute("src"); String extension =
		 * getExtension(src);
		 * 
		 * if (!types.containsValue(extension)){
		 * MessageList.addError(documentId, "Invalid extension for <media> "+
		 * idMedia + ".", eMedia); return false; }
		 * 
		 * }
		 */
		return true;
	}

	private boolean hasValidExtensionForMediaType(Element eMedia) {
		if (eMedia.hasAttribute("src") && eMedia.hasAttribute("type")) {

			String type = eMedia.getAttribute("type");
			String src = eMedia.getAttribute("src");
			String extension = getExtension(src);
			if (!types.containsKey(type)
					|| !types.get(type).contains(extension)) {
				Vector<String> args = new Vector<String>();
				args.add(type);
				args.add(idMedia);
				MessageList.addWarning(doc.getId(), 4101, eMedia, args);
			}
		}
		return true;
	}

	private String getExtension(String src) {
		String extension = "";
		for (int i = src.length() - 1; i >= 0; i--) {
			if (src.charAt(i) == '.')
				break;
			extension = src.charAt(i) + extension;
		}
		return extension;
	}

	private void setTypes() {
		if (types == null) {
			types = new MultiHashMap();

			types.put("text/html", "html");
			types.put("text/html", "htm");

			types.put("text/css", "css");

			types.put("text/xml", "xml");

			types.put("image/bmp", "bmp");
			types.put("image/png", "png");
			types.put("image/mng", "mng");
			types.put("image/gif", "gif");
			types.put("image/jpeg", "jpg");
			types.put("image/jpeg", "jpeg");
			// types.put("image/mpeg", "");

			types.put("audio/basic", "wav");
			// types.put("audio/ac3", "ac3");
			types.put("audio/mp3", "mp3");
			types.put("audio/mp2", "mp2");
			types.put("audio/mpeg", "mpeg");
			types.put("audio/mpeg", "mpg");
			types.put("audio/mpeg4", "mp4");
			types.put("audio/mpeg4", "mpg4");

			types.put("video/mpeg", "mpeg");
			types.put("video/mpeg", "mpg");

			types.put("application/x-ginga-NCL", "ncl");
			types.put("application/x-ncl-NCL", "ncl");

			types.put("application/x-ginga-NCLua", "lua");
			types.put("application/x-ncl-NCLua", "lua");

			types.put("application/x-ginga-NCLet", "class");
			types.put("application/x-ncl-NCLet", "class");
			types.put("application/x-ginga-NCLet", "jar");
			types.put("application/x-ncl-NCLet", "jar");

			types.put("application/x-ginga-settings", "");
			types.put("application/x-ncl-settings", "");

			types.put("application/x-ginga-time", "");
			types.put("application/x-ncl-time", "");
		}
	}

}

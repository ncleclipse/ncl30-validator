/*******************************************************************************
 * Este arquivo é parte da implementação do ambiente de autoria em Nested 
 * Context Language - NCL Eclipse.
 * Direitos Autorais Reservados (c) 2007-2010 UFMA/LAWS (Laboratório de Sistemas 
 * Avançados da Web)
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou modificá-lo sob
 * os termos da Licença Pública Geral GNU versão 2 conforme publicada pela Free 
 * Software Foundation.
 *
 * Este programa é distribuído na expectativa de que seja útil, porém, SEM 
 * NENHUMA GARANTIA; nem mesmo a garantia implícita de COMERCIABILIDADE OU
 * ADEQUAÇÃO A UMA FINALIDADE ESPECÍFICA. Consulte a Licença Pública Geral do
 * GNU versão 2 para mais detalhes. Você deve ter recebido uma cópia da Licença
 * Pública Geral do GNU versão 2 junto com este programa; se não, escreva para a
 * Free Software Foundation, Inc., no endereço 59 Temple Street, Suite 330,
 * Boston, MA 02111-1307 USA.
 *
 * Para maiores informações:
 * - ncleclipse@laws.deinf.ufma.br
 * - http://www.laws.deinf.ufma.br/ncleclipse
 * - http://www.laws.deinf.ufma.br
 *
 *******************************************************************************
 * This file is part of the authoring environment in Nested Context Language -
 * NCL Eclipse.
 * Copyright: 2007-2010 UFMA/LAWS (Laboratory of Advanced Web Systems), All
 * Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License version 2 as published by
 * the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License version 2 for
 * more details. You should have received a copy of the GNU General Public 
 * License version 2 along with this program; if not, write to the Free 
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 
 * 02110-1301, USA.
 *
 * For further information contact:
 * - ncleclipse@laws.deinf.ufma.br
 * - http://www.laws.deinf.ufma.br/ncleclipse
 * - http://www.laws.deinf.ufma.br
 *
 ******************************************************************************/
package br.ufma.deinf.gia.labmint.document;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import br.ufma.deinf.gia.labmint.message.MessageList;
import br.ufma.deinf.gia.labmint.xml.XMLParserExtend;
import br.ufma.deinf.laws.util.DocumentUtil;

public class NclValidatorDocument {
	protected Map<String, Element> elements;
	protected Map<String, NclValidatorDocument> aliases;
	protected Element root;
	protected String id;
	protected String path;

	NclValidatorDocument father; // Father of this NclDocument

	// This is useful to find if there is a cyclic dependency.

	public NclValidatorDocument(NclValidatorDocument father, Document doc)
			throws ParserConfigurationException, URISyntaxException,
			SAXException, IOException {

		this.father = father;

		this.elements = new HashMap<String, Element>();
		this.aliases = new HashMap<String, NclValidatorDocument>();
		this.root = doc.getDocumentElement();
		this.path = doc.getBaseURI();
		// NclDocumentManager.addDocument(doc, this); //registra o documento que
		// estah lendo no DocumentManager
		this.id = null;
		// JOptionPane.showMessageDialog(null, this.path);
		// System.out.println(">> Validating File = " + this.path);

		if (root.hasAttribute("id")) {
			this.id = root.getAttribute("id");
		} else {
			NodeList nodeList = root.getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					if (element.getTagName().compareTo("body") == 0) {
						if (element.hasAttribute("id")) {
							this.id = element.getAttribute("id");
						}
					}
				}
			}
		}
		parse(this.root);

		// XMLStaticElementPosition.calculatePositions(this);
	}

	public String getId() {
		return id;
	}

	public Element getDocumentElement() {
		return this.root;
	}

	public Element getElement(String id) {
		if (elements.containsKey(id)) {
			return elements.get(id);
		}

		if (id.indexOf("#") < 0) {
			return null;
		}

		if (id.indexOf("#") != id.lastIndexOf("#")) {
			// two or more # chars
			return null;
		}

		int index = id.indexOf("#");
		String alias = id.substring(0, index);
		String docId = id.substring(index + 1);

		if (!aliases.containsKey(alias)) {

			return null;

		}
		NclValidatorDocument doc = aliases.get(alias);
		return doc.getElement(docId);
	}

	protected void parse(Element root) {

		if (root.hasAttribute("id")) {
			String id = root.getAttribute("id");
			if (!elements.containsKey(id)) {
				elements.put(id, root);
			} else {
				Vector<String> args = new Vector<String>();
				args.add(id);
				MessageList.addError(this.id, 3001, root, args);
			}
		}
		if (root.getTagName().equals("importBase")
				|| root.getTagName().equals("importNCL")
				|| root.getTagName().equals("link")) {

			if (root.hasAttribute("alias") || root.hasAttribute("xconnector")) {
				String caminho = "";
				String att = "";
				String alias = "";
				boolean auxiliar = false;

				if (root.hasAttribute("documentURI")) {
					caminho = root.getAttribute("documentURI");
					att = "documentURI";
					alias = root.getAttribute("alias");
				}

				if (caminho.equals("")) {
					if (root.hasAttribute("xconnector")) {
						caminho = root.getAttribute("xconnector");
						int index = caminho.indexOf("#");
						if (index != -1)
							caminho = caminho.substring(0, index);
						caminho = aliases.containsKey(caminho) ? aliases.get(
								caminho).getPath() : caminho;
						att = "xconnector";
						alias = caminho;
						auxiliar = true;
						if (index == -1)
							return;
					}
				}

				if (!caminho.equals("")) {
					try {
						XMLParserExtend parser = new XMLParserExtend();
						Document doc = null;
						URI uri = new URI(caminho);

						// Search for a cyclic dependency.
						NclValidatorDocument father = this;
						String fullpath = DocumentUtil.getAbsoluteFileName(
								this.getPath(), uri.getPath());

						while (father != null) {
							// System.out
							// .println("Testing for a Cyclic dependency Error: "
							// + fullpath
							// + " : "
							// + father.getPath());
							if (fullpath.equals(father.getPath())) {
								// there is a cyclic dependency
								Vector<String> args = new Vector<String>();
								args.add(uri.getPath());
								MessageList.addError(this.id, 3103, root, args);
								return;
							}
							father = father.getFather();
						}

						// Se documentUri eh absoluto
						if (uri.isAbsolute()) {
							parser.parse(uri.getPath());
							doc = parser.getDocument();
						} else {
							parser.parse(DocumentUtil.getAbsoluteFileName(
									this.getPath(), caminho));
							doc = parser.getDocument();
						}
						if (!this.addDocument(alias, new NclValidatorDocument(
								this, doc))
								&& auxiliar == false) {
							Vector<String> args = new Vector<String>();
							args.add(alias);
							MessageList.addError(this.id, 3002, root, args);
						}
					} catch (Exception e) {
						Vector<String> args = new Vector<String>();
						args.add(e.getMessage());
						MessageList.addError(this.id, 1003, root, args);
					}
				} else {
					MessageList.addError(this.getPath(), 3101, root);
				}
			} else {
				MessageList.addError(this.getPath(), 3102, root);
			}
		}

		NodeList nodeList = root.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				parse((Element) node);
			}
		}
	}

	public boolean addDocument(String alias, NclValidatorDocument doc) {
		if (aliases.containsKey(alias))
			return false;
		aliases.put(alias, doc);
		return true;
	}

	public NclValidatorDocument getFather() {
		return this.father;
	}

	public Map<String, NclValidatorDocument> getAliases() {
		return aliases;
	}

	public void setAliases(Map<String, NclValidatorDocument> aliases) {
		this.aliases = aliases;
	}

	public Map<String, Element> getElements() {
		return elements;
	}

	public void setElements(Map<String, Element> elements) {
		this.elements = elements;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Element getRoot() {
		return root;
	}

	public void setRoot(Element root) {
		this.root = root;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDir() {
		String path = this.getPath();
		for (int i = path.length() - 1; i >= 0; i--)
			if (path.charAt(i) == '/')
				return path.substring(0, i + 1);
		return path;
	}

	/**
	 * Retorna o elemento com o id dentro do context idContext.
	 * 
	 * @param idContext
	 * @param id
	 * @return
	 */
	public Element getElementInContext(String idContext, String id) {
		if (elements.containsKey(idContext)) {
			Element context = elements.get(idContext);

			NodeList nodeList = context.getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element child = (Element) node;
					// System.out.println(id + " -> " +
					// child.getAttribute("id"));
					if (child.getAttribute("id").equals(id))
						return child;
				}
			}
		}
		return null;
	}

	/**
	 * Retorna o elemento com o id dentro filho direto do element parent.
	 * 
	 * @param idContext
	 * @param id
	 * @return
	 */
	public Element getElementChild(Element parent, String id) {
		if (parent == null)
			return null;
		NodeList nodeList = parent.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element child = (Element) node;
				// System.out.println(id + " -> " + child.getAttribute("id"));
				if (child.getAttribute("id").equals(id))
					return child;
			}
		}

		return null;
	}

	String[] protocols = { "file:", "http:", "https", "rstp", "rtp",
			"ncl-mirror:", "sbtvd:" };

	/**
	 * Test if the sourceStr is a valid path (absolute or relative) w.r.t. this
	 * document.
	 * 
	 * @param sourceStr The path to be validated.
	 * @param element The element (in case of error, the error will be related
	 * 		with this element.
	 * @return
	 */
	public boolean validateSrc(String src, Element element) {
		File fMedia;

		// TODO: this is not sufficient to working with URL codification
		src = src.replaceAll(" ", "%20");
		for (int i = 0; i < protocols.length; i++) {
			if (src.contains(protocols[i])) {
				try {
					URI uri = new URI(src);
					if (uri.isAbsolute()) {
						fMedia = new File(uri);
					} else {
						uri = new URI(this.getDir() + src);
						if (!uri.isAbsolute()) {
							Vector<String> args = new Vector<String>();
							args.add(src);
							MessageList.addWarning(this.getId(), 4103, element,
									args);
							return false;
						} else {
							fMedia = new File(uri);
						}
						if (!fMedia.exists()) {
							Vector<String> args = new Vector<String>();
							args.add(src);
							MessageList.addWarning(this.getId(), 4103, element,
									args);
							return false;
						}

					}
				} catch (Exception e) {
					Vector<String> args = new Vector<String>();
					args.add(src);
					MessageList.addWarning(this.getId(), 4103, element, args);
					return false;

				}
				return true;
			}
		}

		fMedia = new File(src);
		if (fMedia.isFile())
			return true;
		else {
			URI uri;
			try {
				uri = new URI(this.getDir() + src);
				fMedia = new File(uri);
				if (!fMedia.isFile()) {
					Vector<String> args = new Vector<String>();
					args.add(src);
					MessageList.addWarning(this.getId(), 4103, element, args);
					return false;
				}
			} catch (Exception e) {
				Vector<String> args = new Vector<String>();
				args.add(src);
				MessageList.addWarning(this.getId(), 4103, element, args);
				return false;
			}
		}
		return false;
	}
}

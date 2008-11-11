/******************************************************************************
Este arquivo é parte da implementação do ambiente de autoria em Nested Context
Language - NCL Eclipse.

Direitos Autorais Reservados (c) 2007-2008 UFMA/LAWS (Laboratório de Sistemas Avançados da Web) 

Este programa é software livre; você pode redistribuí-lo e/ou modificá-lo sob 
os termos da Licença Pública Geral GNU versão 2 conforme publicada pela Free 
Software Foundation.

Este programa é distribuído na expectativa de que seja útil, porém, SEM 
NENHUMA GARANTIA; nem mesmo a garantia implícita de COMERCIABILIDADE OU 
ADEQUAÇÃO A UMA FINALIDADE ESPECÍFICA. Consulte a Licença Pública Geral do 
GNU versão 2 para mais detalhes. 

Você deve ter recebido uma cópia da Licença Pública Geral do GNU versão 2 junto 
com este programa; se não, escreva para a Free Software Foundation, Inc., no 
endereço 59 Temple Street, Suite 330, Boston, MA 02111-1307 USA. 

Para maiores informações:
ncleclipse@laws.deinf.ufma.br
http://www.laws.deinf.ufma.br/ncleclipse
http://www.laws.deinf.ufma.br

******************************************************************************
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

import org.w3c.dom.Element;

import br.ufma.deinf.gia.labmint.document.NclValidatorDocument;
import br.ufma.deinf.gia.labmint.message.MessageList;
import br.ufma.deinf.laws.util.MultiHashMap;

public class Media extends ElementValidation{

	public Media(NclValidatorDocument doc) {
		super(doc);
		setTypes();		
	}

	private MultiHashMap types = null;
	private String idMedia = null;
	
    public boolean validate(Element eMedia){
        boolean resultado = true;
        
        idMedia = eMedia.getAttribute("id");
        
        //Verifica se o atributo 'descriptor' da <media> aponta para um <descriptor>.
        if(!hasValidMediaDescriptorAttribute(eMedia)) resultado = false;

		//Verifica se o atributo 'type' da <media> possui um valor valido.
        if(!hasValidMediaTypeAttribute(eMedia)) resultado = false;

		//Verifica se o atributo 'src' da <media> possui um valor valido.
        if(!hasValidMediaSrcAttribute(eMedia)) resultado = false;

        //
        //if(!hasValidMediaIDAttribute(eMedia)) resultado = false;

        //Verifica se o atributo 'refer' da <media> aponta para um outro elemento <media>.
        if(!hasValidMediaReferAttribute(eMedia)) resultado = false;

		//Verifica se o atributo 'newInstace' da <media> possui um valor valido.
        //if(!hasValidMediaNewInstanceAttribute(eMedia)) resultado = false;
        
        //Verifica se o arquivo especificado no atributo 'src' tem uma extensao valida.
        //if (!hasValidExtension(eMedia)) resultado = false;
        
        //Verifica se a extensao diverge da definida pelo atributo type.
        if (!hasValidExtensionForMediaType(eMedia)) resultado = false;

        //Atributo refer não deve ser utilizado com o src simultaneamente
        if(!eMedia.hasAttribute("refer")){
        	//Atributo type é obrigatório se src não for definido        	
        	if(!eMedia.hasAttribute("src")){
        		if(!eMedia.hasAttribute("type")){
        			MessageList.addError(doc.getId(), "Attribute type is required if src is not defined.", eMedia, MessageList.ENGLISH);
        			MessageList.addError(doc.getId(), "Atributo 'type' � necess�rio se 'src' n�o � definido.", eMedia, MessageList.PORTUGUESE);
        			resultado = false;
        		}
        	}
        }
        else if(eMedia.hasAttribute("src")){
        	MessageList.addWarning(doc.getId(), "Attribute refer is not usefull with attribute src.", eMedia, MessageList.ENGLISH);
        	MessageList.addWarning(doc.getId(), "Atributo 'refer' n�o � �til se atributo 'src' � definido.", eMedia, MessageList.PORTUGUESE);
    		resultado = false;
        }
        	
        return resultado;
    }

    private boolean hasValidMediaDescriptorAttribute(Element eMedia){
    	if( eMedia.hasAttribute("descriptor") ) {
			String idDescriptor = eMedia.getAttribute("descriptor");
			Element element = doc.getElement(idDescriptor);
			if( element==null ) {
				MessageList.addError(doc.getId(), 
						"There is not an element pointed by attribute descriptor with value '" + idDescriptor + "'.",
				   		eMedia, MessageList.ENGLISH);
				MessageList.addError(doc.getId(), 
						"O elemento apontado pelo atributo descriptor ('" + idDescriptor + "') n�o � um elemento v�lido.",
				   		eMedia, MessageList.PORTUGUESE);
				return false;
			}
			else if( element.getTagName().compareTo("descriptor")!=0 ) {
				MessageList.addError(doc.getId(), 
						"The element pointed by attributte descriptor in " +
						"the element '" + idMedia + "' must be a <descriptor>.",
				   		eMedia, MessageList.ENGLISH);
				MessageList.addError(doc.getId(), 
						"O elemento apontado pelo atributo descriptor ('" +idDescriptor + "')" +
						"deve ser um elemento <descriptor>.",
				   		eMedia, MessageList.PORTUGUESE);
				return false;				
			}
		}
        return true;
    }

    private boolean hasValidMediaTypeAttribute(Element eMedia){
    	if (eMedia.hasAttribute("type")){
    	
    		String type = eMedia.getAttribute("type");
    		
    		if (!types.containsKey(type)){
    			MessageList.addWarning(doc.getId(), 
						"Invalid value for attribute type in <media> " + idMedia + ".",
				   		eMedia, MessageList.ENGLISH);
    			MessageList.addWarning(doc.getId(), 
						"Valor do atributo type ('"+type+"') � inv�lido.",
				   		eMedia, MessageList.PORTUGUESE);
    			return false;
    		}
    	}else if (!eMedia.hasAttribute("src") && !eMedia.hasAttribute("refer")){
    		MessageList.addError(doc.getId(), 
					"The attribute type is mandatory when the attribute src is not present at media element.",
			   		eMedia, MessageList.ENGLISH);
    		MessageList.addError(doc.getId(), 
					"O atributo 'type' � obrigat�rio quando o atributo 'src' n�o est� presente.",
			   		eMedia, MessageList.PORTUGUESE);
			return false;
    	}
        return true;
    }

    private boolean hasValidMediaSrcAttribute(Element eMedia){
    	if (eMedia.hasAttribute("src"))
        {
        	String src = eMedia.getAttribute("src");
        	
        	File fMedia;
			try {
				URI uri = new URI(URLEncoder.encode(src, "UTF-8"));
				System.out.println(uri);
				if(uri.isAbsolute())
					fMedia = new File(uri);
				else{
					uri = new URI(URLEncoder.encode(doc.getDir()+src,"US-ASCII"));
					System.out.println(uri);
					if(!uri.isAbsolute()){
						MessageList.addWarning(doc.getId(), 
		    					"Invalid path for attribute src in <media> " + idMedia + ".",
		    			   		eMedia, MessageList.ENGLISH);
						MessageList.addWarning(doc.getId(), 
		    					"Atributo src ('"+src+"') � um caminho de arquivo inv�lido.",
		    			   		eMedia, MessageList.PORTUGUESE);
					}
				}
				//System.out.println(doc.getDir()+src);
	        					
			} catch (Exception e) {
				MessageList.addWarning(doc.getId(), 
    					"Invalid path for attribute src in <media> " + idMedia + ".",
    			   		eMedia, MessageList.ENGLISH);
				MessageList.addWarning(doc.getId(), 
    					"Atributo src ('"+src+"') � um caminho de arquivo inv�lido.",
    			   		eMedia, MessageList.PORTUGUESE);
			}
        }
		return true;
    }

    private boolean hasValidMediaIDAttribute(Element eMedia){
        //TODO Validar Atributo id
        return true;
    }

    private boolean hasValidMediaReferAttribute(Element eMedia){
    	if( eMedia.hasAttribute("refer") ) {
			
			String idRefer = eMedia.getAttribute("refer");
			Element element = doc.getElement(idRefer);
			if(element==null) {
				MessageList.addError(doc.getId(), 
						"The refer attribute does not point to an element.",
						eMedia, MessageList.ENGLISH);
				MessageList.addError(doc.getId(), 
						"O elemento apontado pelo atributo refer ('"+idRefer+"') n�o existe.",
						eMedia, MessageList.PORTUGUESE);				
				return false;				
			}
			else if(element.getTagName().compareTo("media")!=0) {
				MessageList.addError(doc.getId(), 
						"There is not a <media> element with id "+ idRefer + ".",
						eMedia, MessageList.PORTUGUESE);
				MessageList.addError(doc.getId(), 
						"O elemento apontado pelo atributo refer ('"+idRefer+"') deve ser um elemento <media>.",
						eMedia, MessageList.PORTUGUESE);				
				return false;
			}
		}		
        return true;
    }

    private boolean hasValidMediaNewInstanceAttribute(Element eMedia){
        //TODO Validar Atributo newIsntance
        return true;
    }
    
    private boolean hasValidExtension(Element eMedia){
    	/*
    	if (eMedia.hasAttribute("src"))
    	{
    		setTypes();
    		
    		String src = eMedia.getAttribute("src");
    		String extension = getExtension(src);
    		
    		if (!types.containsValue(extension)){
    			MessageList.addError(documentId, 
						"Invalid extension for <media> "+ idMedia + ".",
						eMedia);
				return false;
    		}
    		
    	}
    	*/
    	return true;
    }
    
    private boolean hasValidExtensionForMediaType(Element eMedia){
    	if (eMedia.hasAttribute("src") && eMedia.hasAttribute("type")){
    		
    		String type = eMedia.getAttribute("type");
    		String src = eMedia.getAttribute("src");
    		String extension = getExtension(src);
    		if (!types.containsKey(type) || !types.get(type).contains(extension)){
    			MessageList.addWarning(doc.getId(), 
						"Invalid extension for the type "+type+" defined in <media> " + idMedia + ".",
						eMedia, MessageList.ENGLISH);
    			MessageList.addWarning(doc.getId(), 
						"Extens�o inv�lida para o tipo MIME "+type+" definido na <media> ('" + idMedia + "').",
						eMedia, MessageList.PORTUGUESE);
    		}
    	}    	
    	return true;
    }
    
    private  String getExtension(String src){
    	String extension = "";
    	for (int i=src.length()-1;i>=0;i--) { 
			if (src.charAt(i) == '.') break;
    		extension = src.charAt(i) + extension;
		}
    	return extension;
    }
    
    private void setTypes(){
    	if (types == null){
    		types = new MultiHashMap();
    	
    		types.put("text/html", "html");
    		types.put("text/html", "htm");
    		types.put("text/css", "css");
    		types.put("text/xml", "xml");
    		types.put("image/bmp", "bmp");
    		types.put("image/png", "png");
        	types.put("image/gif", "gif");
        	types.put("image/jpeg", "jpg");
        	types.put("image/jpeg", "jpeg");
        	//types.put("image/mpeg", "");
        	types.put("audio/basic", "wav");
        	//types.put("audio/ac3", "ac3"); 
        	types.put("audio/mp3", "mp3");
        	types.put("audio/mp2", "mp2");
        	//types.put("audio/mpeg", "mpeg");
        	//types.put("audio/mpeg", "mpg");
        	types.put("audio/mpeg4", "mp4");
        	types.put("audio/mpeg4", "mpg4");
        	types.put("video/mpeg", "mpeg");
        	types.put("video/mpeg", "mpg");
        	types.put("application/x-ginga-NCLua", "lua");
        	types.put("application/x-ginga-NCLet", "xlt");
        	types.put("application/x-ginga-NCLet", "xlet");
        	types.put("application/x-ginga-NCLet", "class");
        	types.put("application/x-ginga-settings", "");
        	types.put("application/x-ginga-time", "");
    	}
    }

}

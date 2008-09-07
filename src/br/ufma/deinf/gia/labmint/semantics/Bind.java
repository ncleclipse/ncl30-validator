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


import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.ufma.deinf.gia.labmint.document.NclValidatorDocument;
import br.ufma.deinf.gia.labmint.message.MessageList;


public class Bind extends ElementValidation{
	
    public Bind(NclValidatorDocument doc){
        super(doc);
    }

    public boolean validate(Element eBind){
        boolean resultado = true;

        //Verifica se o atributo 'interface' do <bind> aponta para um elemento <area> ou <property>.
        if(!hasValidBindInterfaceAttribute(eBind)) resultado = false;

        //Verifica se o atributo 'descriptor' do <bind> aponta para um <descriptor>.
        if(!hasValidBindDescriptorAttribute(eBind)) resultado = false;

        //
        if(!hasValidBindRoleAttribute(eBind)) resultado = false;

        //Verifica se o atributo 'component' do <bind> aponta para um elemento <media>.
        if(!hasValidBindComponentAttribute(eBind)) resultado = false;

        return resultado;
    }

    private boolean hasValidBindInterfaceAttribute(Element eBind){
        if (!eBind.hasAttribute("interface")) {
        	return true;
        }

        String idInterface = eBind.getAttribute("interface");
    	if( !eBind.hasAttribute("component") ) {
			MessageList.addError(doc.getId(), 
					"The bind element has an interface attribute but does not have a component attribute",
			   		eBind, MessageList.ENGLISH);
			MessageList.addError(doc.getId(), 
					"O elemento <bind> possui um atributo 'interface', mas n�o tem um atributo 'component'.",
			   		eBind, MessageList.PORTUGUESE);
			return  false;
    	}
    	
    	String idComponent = eBind.getAttribute("component");
    	Element element = doc.getElement(idComponent);
    	if(element == null) return false;
    	while(element.hasAttribute("refer")){
        	NodeList nodeList = element.getChildNodes();
        	for(int i=0; i<nodeList.getLength(); i++) {
        		Node node = nodeList.item(i);
        		if( node.getNodeType() == Node.ELEMENT_NODE ) {
        			Element childElement = (Element)node; 
        	    	String tagName = childElement.getTagName(); 
        	    	if(tagName.compareTo("area")==0 && childElement.hasAttribute("id")) {
        	    		if(childElement.getAttribute("id").compareTo(idInterface)==0) {
        	    			return true;
        	    		}
        	    	}
        	    	if(tagName.compareTo("property")==0 && childElement.hasAttribute("name")) {
        	    		if(childElement.getAttribute("name").compareTo(idInterface)==0) {
        	    			return true;
        	    		}
        	    	}
        	    	if(tagName.compareTo("port")==0 && childElement.hasAttribute("id")) {
        	    		if(childElement.getAttribute("id").compareTo(idInterface)==0) {
        	    			return true;
        	    		}
        	    	}
        	    	if(tagName.compareTo("switchPort")==0 && childElement.hasAttribute("id")) {
        	    		if(childElement.getAttribute("id").compareTo(idInterface)==0) {
        	    			return true;
        	    		}    	    		
        	    	}
        		}
        	}
        	idComponent = element.getAttribute("refer");
    		element = doc.getElement(idComponent);
    		if(element == null) return false;
    	}
    	if(element==null) {
    		return false;
    	}
    	NodeList nodeList = element.getChildNodes();
    	for(int i=0; i<nodeList.getLength(); i++) {
    		Node node = nodeList.item(i);
    		if( node.getNodeType() == Node.ELEMENT_NODE ) {
    			Element childElement = (Element)node; 
    	    	String tagName = childElement.getTagName(); 
    	    	if(tagName.compareTo("area")==0 && childElement.hasAttribute("id")) {
    	    		if(childElement.getAttribute("id").compareTo(idInterface)==0) {
    	    			return true;
    	    		}
    	    	}
    	    	if(tagName.compareTo("property")==0 && childElement.hasAttribute("name")) {
    	    		if(childElement.getAttribute("name").compareTo(idInterface)==0) {
    	    			return true;
    	    		}
    	    	}
    	    	if(tagName.compareTo("port")==0 && childElement.hasAttribute("id")) {
    	    		if(childElement.getAttribute("id").compareTo(idInterface)==0) {
    	    			return true;
    	    		}
    	    	}
    	    	if(tagName.compareTo("switchPort")==0 && childElement.hasAttribute("id")) {
    	    		if(childElement.getAttribute("id").compareTo(idInterface)==0) {
    	    			return true;
    	    		}    	    		
    	    	}
    		}
    	}
    	MessageList.addError(doc.getId(), 
    			"The element pointed by attributte interface in " +
    			"the bind element must be an <area>, <property>, <port> or <switchPort> element.",
    			eBind, MessageList.ENGLISH);
    	MessageList.addError(doc.getId(), 
    			"O elemento apontado pelo atributo interface no elemento <bind> " +
    			"deve ser uma elemento <area>, <property>, <port> ou <switchPort>",
    			eBind, MessageList.PORTUGUESE);
    	return false;
    }

    private boolean hasValidBindDescriptorAttribute(Element eBind){
    	if( eBind.hasAttribute("descriptor") ) {			
			String idDescriptor = eBind.getAttribute("descriptor");
			Element element = doc.getElement(idDescriptor); 
			if (element==null ) {				
				MessageList.addError(doc.getId(), 
						"There is not a <descriptor> element with id '" + idDescriptor + "'.",
				   		eBind, MessageList.ENGLISH);
				MessageList.addError(doc.getId(), 
						"N�o existe um elemento <descriptor> com identificador '" + idDescriptor + "'.",
				   		eBind, MessageList.PORTUGUESE);				
				return false;
			}
			else if(element.getTagName().compareTo("descriptor")!=0){
				MessageList.addError(doc.getId(), 
						"The element pointed by descriptor attribute ('" + idDescriptor + 
						"' is not a <descriptor> element.",
				   		eBind, MessageList.ENGLISH);
				MessageList.addError(doc.getId(), 
						"O elemento apontado pelo atributo descritor ('" + idDescriptor + 
						"' n�o � um elemento <descriptor>.",
				   		eBind, MessageList.PORTUGUESE);				
				return false;
			}
		}
        return true;
    }

    private boolean hasValidBindRoleAttribute(Element eBind){
        //TODO: All!
        return true;
    }

    private boolean hasValidBindComponentAttribute(Element eBind){
    	
    	//Obrigado a ter um atributo 'component' - Verificacao feita no Sintatico.
    	if(!eBind.hasAttribute("component")) return false;
    	String idComponent = eBind.getAttribute("component");
    	Element element = doc.getElement(idComponent); 
    	if( element==null ) {
			MessageList.addError(doc.getId(), 
						"There is not an element with id '" + idComponent + "'.",
				   		eBind, MessageList.ENGLISH);
			MessageList.addError(doc.getId(), 
					"N�o existe um elemento com identificador '" + idComponent + "'.",
			   		eBind, MessageList.PORTUGUESE);			
			return false;
		}
    	else if( element.getTagName().compareTo("media") != 0 
    			&& element.getTagName().compareTo("context") != 0
    			&& element.getTagName().compareTo("switch") != 0) {
    			if(!componentIsMyContext(eBind)){
    				MessageList.addError(doc.getId(), 
    						"The element with id '" + idComponent + "' is not a valid <media>, <context>, <body> or <switch> element.",
    						eBind, MessageList.ENGLISH);
    				MessageList.addError(doc.getId(), 
    						"O elemento com identificador '" + idComponent + "' n�o � um elemento <media>, <context>, <body> ou <switch> v�lido.",
    						eBind, MessageList.PORTUGUESE);    				
    				return false;
    			}
    	}
    	return true;
    }
    /**
     * Verifica se o component do Bind referencia o seu context (ou Body)
     * @param eBind
     * @return
     */
	private boolean componentIsMyContext(Element eBind) {
		// TODO Auto-generated method stub
		Element eLink = (Element) eBind.getParentNode();
		Element eContext = (Element)eLink.getParentNode();
		
		if((eContext.getTagName().equals("body") || eContext.getTagName().equals("context"))
				&& eContext.hasAttribute("id") && 
				eContext.getAttribute("id").equals(eBind.getAttribute("component")))
					return true;
		
		return false;
	}
}

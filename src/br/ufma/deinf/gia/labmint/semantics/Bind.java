/******************************************************************************
Este arquivo eh uma extensao do ambiente declarativo do middleware 
Ginga (Ginga-NCL).

Direitos Autorais Reservados (c)2007 LabMint, Laboratorio de Midias Interativas 
Departamento de Informatica, UFMA - Universidade Federal do Maranhao

Este programa eh software livre; voce pode redistribui-lo e/ou modifica-lo sob 
os termos da Licenca Publica Geral GNU versao 2 conforme publicada pela Free 
Software Foundation.

Este programa eh distribu�do na expectativa de que seja util, porem, SEM 
NENHUMA GARANTIA; nem mesmo a garantia implicita de COMERCIABILIDADE OU 
ADEQUACAO A UMA FINALIDADE ESPECIFICA. Consulte a Licenca Publica Geral do 
GNU versao 2 para mais detalhes. 

Voce deve ter recebido uma copia da Licenca Publica Geral do GNU versao 2 junto 
com este programa; se nao, escreva para a Free Software Foundation, Inc., no 
endereco 59 Temple Street, Suite 330, Boston, MA 02111-1307 USA. 

Para maiores informacoes:
ncl @ telemidia.puc-rio.br
http://www.ncl.org.br
http://www.ginga.org.br
http://www.softwarepublico.gov.br

Sobre o Validador NCL:
labmint @ gia.deinf.ufma.br
http://www.gia.deinf.ufma.br/~labmint/


******************************************************************************
This file is an extension of the declarative environment of 
middleware Ginga (Ginga-NCL)

Copyright: 2007 LabMint - Laboratory of Interactive Medias, Science Computing
           Department, Federal University of Maranhao, All Rights Reserved.

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
ncl @ telemidia.puc-rio.br
http://www.ncl.org.br
http://www.ginga.org.br
http://www.softwarepublico.gov.br

About NCL Validator:
labmint @ gia.deinf.ufma.br
http://www.gia.deinf.ufma.br/~labmint/

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
			   		eBind);
			return  false;
    	}
    	
    	String idComponent = eBind.getAttribute("component");
    	Element element = doc.getElement(idComponent); 
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
    			eBind);
    	return false;
    }

    private boolean hasValidBindDescriptorAttribute(Element eBind){
    	if( eBind.hasAttribute("descriptor") ) {			
			String idDescriptor = eBind.getAttribute("descriptor");
			Element element = doc.getElement(idDescriptor); 
			if (element==null ) {				
				MessageList.addError(doc.getId(), 
						"There is not a <descriptor> element with id '" + idDescriptor + "'.",
				   		eBind);
				return false;
			}
			else if(element.getTagName().compareTo("descriptor")!=0){
				MessageList.addError(doc.getId(), 
						"The element pointed by descriptor attribute ('" + idDescriptor + 
						"' is not a <descriptor> element.",
				   		eBind);
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
				   		eBind);
			return false;
		}
    	else if( element.getTagName().compareTo("media") != 0 
    			&& element.getTagName().compareTo("context") != 0
    			&& element.getTagName().compareTo("switch") != 0) {
    			if(!componentIsMyContext(eBind)){
    				MessageList.addError(doc.getId(), 
    						"The element with id '" + idComponent + "' is not a valid <media>, <context>, <body> or <switch> element.",
    						eBind);
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

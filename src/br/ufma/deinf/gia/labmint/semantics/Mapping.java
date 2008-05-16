/******************************************************************************
Este arquivo eh uma extensao do ambiente declarativo do middleware 
Ginga (Ginga-NCL).

Direitos Autorais Reservados (c)2007 LabMint, Laboratorio de Midias Interativas 
Departamento de Informatica, UFMA - Universidade Federal do Maranhao

Este programa eh software livre; voce pode redistribui-lo e/ou modifica-lo sob 
os termos da Licenca Publica Geral GNU versao 2 conforme publicada pela Free 
Software Foundation.

Este programa eh distribuï¿½do na expectativa de que seja util, porem, SEM 
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


public class Mapping extends ElementValidation{

    public Mapping(NclValidatorDocument doc) {
		super(doc);
	}

	public boolean validate(Element eMapping){
        boolean resultado = true;

        //Verifica se o atributo 'interface' de <mapping> aponta para um elemento <port>.
        if(!hasValidMappingInterfaceAttribute(eMapping)) resultado = false;

        //Verifica se o atributo 'component' de <mapping> aponta para um elemento <context>.
        if(!hasValidMappingComponentAttribute(eMapping)) resultado = false;

        return resultado;
    }

    private boolean hasValidMappingInterfaceAttribute(Element eMapping){
    	if (eMapping.hasAttribute("interface")){
    		
    		if(!eMapping.hasAttribute("component")) {
    			MessageList.addError(doc.getId(), 
						"The element <mapping> has an interface attribute but does not have a " +
						"component attribute.",
						eMapping, MessageList.ENGLISH);
    			MessageList.addError(doc.getId(), 
						"O elemento <mapping> possui um atributo 'interface', mas não possui um atributo 'component'.",
						eMapping, MessageList.PORTUGUESE);    			
				return  false;    			
    		}
        	
        	String idInterface = eMapping.getAttribute("interface");
        	String idComponent = eMapping.getAttribute("component");
        	Element element = doc.getElement(idComponent);
        	if( element == null ) {
        		return false;
        	}
        	
        	NodeList nodeList = element.getChildNodes();
        	for(int i=0; i<nodeList.getLength(); i++) {
        		Node node = nodeList.item(i);
        		if(node.getNodeType() == Node.ELEMENT_NODE) {
        			Element child = (Element)node;
        			if(child.hasAttribute("id") && child.getAttribute("id").compareTo(idInterface)==0 ) {
        				if( child.getTagName().compareTo("anchor")!=0 && child.getTagName().compareTo("property")!=0 ) {
        					MessageList.addError(doc.getId(), 
        							"The element pointed by attributte interface in " +
        							"the <mapping> element must be an interface child of the component with id '" + idComponent + "'.",
        							eMapping, MessageList.ENGLISH);
        					MessageList.addError(doc.getId(), 
        							"O elemento apontado pelo atributo interface no elemento " +
        							"<mapping> deve ser o identificador de um elemento filho do nó ('" + idComponent + "').",
        							eMapping, MessageList.PORTUGUESE);
        					return  false;
        				}
        				return true;
        			}
        		}
        	}
			MessageList.addError(doc.getId(), 
					"The element pointed by attributte interface in " +
					"the <mapping> element must be an interface child of the component with id '" + idComponent + "'.",
					eMapping, MessageList.ENGLISH);
			MessageList.addError(doc.getId(), 
					"O elemento apontado pelo atributo interface no elemento " +
					"<mapping> deve ser o identificador de um elemento filho do nó ('" + idComponent + "').",
					eMapping, MessageList.PORTUGUESE);
			return  false;
    	}
    	return true;
    }

    private boolean hasValidMappingComponentAttribute(Element eMapping){
        
    	//Obrigado ter um atributo 'component' - Verificacao feita no Sintatico.
    	if(!eMapping.hasAttribute("component")) return false;
    	String idComponent = eMapping.getAttribute("component");
    	Element element = doc.getElement(idComponent);
    	if( element==null ) {
			MessageList.addError(doc.getId(), 
					"There is not an element with id '" + idComponent + "'.",
					eMapping, MessageList.ENGLISH);
			MessageList.addError(doc.getId(), 
					"O elemento apontado pelo atributo component ('" + idComponent + "') não existe.",
					eMapping, MessageList.PORTUGUESE);
			return false;
		}
    	else if( element.getTagName().compareTo("context")!=0 
    			&& element.getTagName().compareTo("media")!=0
    			&& element.getTagName().compareTo("switch")!=0) {
			MessageList.addError(doc.getId(), 
					"The element pointed by attributte component in " +
					"the mapping element must be a <context>, <media> or <switch>.",
					eMapping, MessageList.ENGLISH);
			MessageList.addError(doc.getId(), 
					"O elemento apontado pelo atributo component ('" + idComponent + "')" +
					"deve ser um elemento <context>, <media> ou <switch>.",
					eMapping, MessageList.PORTUGUESE);
			return false;
    		
    	}
    	return true;
    }

}

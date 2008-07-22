/******************************************************************************
Este arquivo eh uma extensao do ambiente declarativo do middleware 
Ginga (Ginga-NCL).

Direitos Autorais Reservados (c)2007 LabMint, Laboratorio de Midias Interativas 
Departamento de Informatica, UFMA - Universidade Federal do Maranhao

Este programa eh software livre; voce pode redistribui-lo e/ou modifica-lo sob 
os termos da Licenca Publica Geral GNU versao 2 conforme publicada pela Free 
Software Foundation.

Este programa eh distribuÔøΩdo na expectativa de que seja util, porem, SEM 
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


public class Port extends ElementValidation{

	public Port(NclValidatorDocument doc) {
		super(doc);
	}

	private String idPort = null;
	
    public boolean validate(Element ePort){
        boolean resultado = true;

        idPort = ePort.getAttribute("id");
        
        //Verifica se o atributo 'interface' de <port> aponta para um elemento <area>.
        if(!hasValidPortInterfaceAttribute(ePort)) resultado = false;

        //
        if(!hasValidPortIDAttribute(ePort)) resultado = false;

        //Verifica se o atributo 'component' de <port> aponta para um elemento <media>.
        if(!hasValidPortComponentAttribute(ePort)) resultado = false;
        
        return resultado;
    }

    private boolean hasValidPortInterfaceAttribute(Element ePort){
    	 if(!hasValidPortComponentAttribute(ePort)) return false;
    	 if (ePort.hasAttribute("interface")){
         	
			 if(!ePort.hasAttribute("component")) {
	  			MessageList.addError(doc.getId(), 
							"The element has an interface attribute but does not have a component attribute.",
							ePort, MessageList.ENGLISH);
				MessageList.addError(doc.getId(), 
						"O elemento <port> possui um atributo 'interface', mas n„o tem um atributo 'component'.",
				   		ePort, MessageList.PORTUGUESE);
					return  false;    			 
			 }
			
			if(!ePort.hasAttribute("component")) return false;//msg gerada pelo DTD
		    String idComponent = ePort.getAttribute("component");
	     	String idInterface = ePort.getAttribute("interface");
	     	Element element = doc.getElement(idComponent);

	     	while(element.hasAttribute("refer")){
	    		idComponent = element.getAttribute("refer");
	    		element = doc.getElement(idComponent);
	    	}
	    	
	     	if(element==null) {
	     		return false;
	     	}

	     	NodeList nodeList = element.getChildNodes();
	     	for(int i=0; i<nodeList.getLength(); i++) {
	     		Node node = nodeList.item(i);
	     		if(node.getNodeType() == Node.ELEMENT_NODE) {
	     			Element child = (Element)node;
	     			String tag = child.getTagName();
	     			String id = null;
	     			if(child.hasAttribute("id")) id = child.getAttribute("id");
	     			else if(child.hasAttribute("name")) id = child.getAttribute("name");
	     			if(id!=null && id.compareTo(idInterface)==0 ) {
	     				if(tag.compareTo("area")!=0 && tag.compareTo("property")!=0 
	     					&& tag.compareTo("port")!=0 && tag.compareTo("switchPort")!=0) {
	     		     		MessageList.addError(doc.getId(),
	    							"The element pointed by attributte interface in " +
	    							"the element '"+idPort+"' must be a <area>, <property>, <port> or <switchPort> element.",
	    							ePort, MessageList.ENGLISH);
	     		     		MessageList.addError(doc.getId(),
	    							"O elemento apontado pelo atributo interface " +
	    							"do elemento <port> ('"+idPort+"') deve ser um elemento <area>, <property>, <port> ou <switchPort>.",
	    							ePort, MessageList.PORTUGUESE);
	     		     		return  false;
	     				}
	     				return true;
	     			}
	     		}
	     	}
     		MessageList.addError(doc.getId(),
						"The element pointed by attributte interface in " +
						"the element '"+idPort+"' must be a child of the component element.",
						ePort, MessageList.ENGLISH);
     		MessageList.addError(doc.getId(),
					"O elemento apontado pelo atributo interface " +
					"do elemento <port> ('"+idPort+"') deve ser um elemento filho do componente" +
					"('"+ idComponent +"')",
					ePort, MessageList.PORTUGUESE);     		
			return  false;
    	 }
    	 return true;
    }

    private boolean hasValidPortIDAttribute(Element ePort){
        //TODO: All!
        return true;
    }

    private boolean hasValidPortComponentAttribute(Element ePort){
    	if(!ePort.hasAttribute("component")) return false; //msg gerada pelo DTD
    	String idComponent = ePort.getAttribute("component");
    	Element element = doc.getElement(idComponent);
    	if( element==null) {
    		MessageList.addError(doc.getId(), 
					"There is not an element with id '" + idComponent + "'.",
					ePort, MessageList.ENGLISH);
    		MessageList.addError(doc.getId(), 
					"N„o existe um elemento com identificador '" + idComponent + "'.",
					ePort, MessageList.PORTUGUESE);    		
    		return false;
    	}
    	else if( element.getTagName().compareTo("media") != 0 
    			&& element.getTagName().compareTo("context") != 0
    			&& element.getTagName().compareTo("switch") != 0 ) {
    		MessageList.addError(doc.getId(), 
					"The element pointed by attribute component is not a <media>, <context> or <switch> element.",
					ePort, MessageList.ENGLISH);
    		MessageList.addError(doc.getId(), 
					"O elemento apontado pelo atributo component ('" + idComponent + 
					"') n„o È um elemento <media>, <context> ou <switch>.",
					ePort,
					MessageList.PORTUGUESE);
    		return false;
    	}
    	else {
    		// Verifica se o atributo referenciado por component est√° no mesmo contexto
    		Node parent = ePort.getParentNode();
   			NodeList nodeList = parent.getChildNodes();
   			boolean ok = false;
   			Element child = null;
   			for(int i = 0; i < nodeList.getLength(); i++){
   				Node node = nodeList.item(i);
   	     		if(node.getNodeType() == Node.ELEMENT_NODE) {
   	     			child = (Element)node;
   	     			if(child.hasAttribute("id")) {  //msg gerada pelo DTD
   	     				if(child.getAttribute("id").equals(idComponent)){
    	     				ok = true;
   	     					break;
   	     				}
   	     			}
    	     	}
    		}
    		if(!ok){
    			MessageList.addError(doc.getId(),
    					"The element pointed by attribute component must to be in the same context of element <port>", 
    					element, MessageList.ENGLISH);
    			MessageList.addError(doc.getId(),
    					"O elemento apontado pelo atributo component deve estar no mesmo contexto do elemento <port> ('"+idPort+"')", 
    					element, MessageList.PORTUGUESE);
    			return false;
    		}
    		/*
    		//TODO Atributo interface √© obrigat√≥rio se elemento apontado √© um context (Acho que isso n√£o √© verdade)
    		if(element.getTagName().equals("context") && !ePort.hasAttribute("interface")){
    			MessageList.addError(doc.getId(),
    					"The Element pointed by attribute component is a Context, so the attribute interface is mandatory.",
    					element);
    		} 
    		*/   		
    	}    		
        return true;
    }

}


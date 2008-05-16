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


import java.nio.charset.CodingErrorAction;
import java.util.HashMap;
import java.util.Iterator;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.ufma.deinf.gia.labmint.document.NclValidatorDocument;
import br.ufma.deinf.gia.labmint.message.MessageList;


public class Link extends ElementValidation{
	HashMap conditions = null;
	HashMap actions = null;
	
	public Link(NclValidatorDocument doc) {
		super(doc);
	}

	private String idLink = null;
	
    public boolean validate(Element eLink){
        boolean resultado = true;
        if(!eLink.hasAttribute("id")) return false;
        idLink = eLink.getAttribute("id");
        
        //Verifica se o atributo 'xconnector' da <link> aponta para um <causalConnerctor> .
        //Verifica se o link usado realmente exite!
        if(!hasValidLinkXConnectorAttribute(eLink)) resultado = false;
        else{
        	//Verifica se a estrutura do Documento Link "bate" com a do XConnector 
            if(!hasValidXConnectorStructure(eLink)) resultado = false;
        }
        
        //
        if(!hasValidLinkIDAttribute(eLink)) resultado = false;
        
        return resultado;
    }

    private boolean hasValidXConnectorStructure(Element eLink) {
		// Verifica se a estrutura do link "Bate" com a estrutura do XConnector definido
    	if(!eLink.hasAttribute("xconnector")) return false; //msg gerada pelo DTD
    	String idXConnector = eLink.getAttribute("xconnector");
    	Element eCausalConnector = doc.getElement(idXConnector);
    	conditions = new HashMap<String, Element>();
    	actions = new HashMap<String, Element>();
    	parseCausalConnector(eCausalConnector);

    	//Valida o MÃ¡ximo e MÃ­nimo dos Links 
    	HashMap qtdeRoles = new HashMap<String, Integer>();
    	NodeList list = eLink.getChildNodes();
    	//Computa as quantidades
    	for(int i = 0; i < list.getLength(); i++){
    		Node node = list.item(i);
    		if(node.getNodeType() == Node.ELEMENT_NODE){
    			Element element = (Element) node;
    			if(!element.getTagName().equals("bind")) continue;
    			if(!element.hasAttribute("role")) return false; //msg gerada pelo DTD
    			String strRole = element.getAttribute("role");
    			if(qtdeRoles.containsKey(strRole)) {
    				Integer v = (Integer) qtdeRoles.get(strRole);
    				qtdeRoles.put(strRole, new Integer(v.intValue()+1));
    			}
    			else qtdeRoles.put(strRole, 1);
    		}
    	}
    	//Valida as quantidades
    	Iterator qtdeRolesIt = qtdeRoles.keySet().iterator();
		boolean ok = true;    	
    	while(qtdeRolesIt.hasNext()){
    		String strRole = (String) qtdeRolesIt.next();
    		Integer qtde = (Integer) qtdeRoles.get(strRole);
    		Element eSimpleCondition = null;
    		if(conditions.containsKey(strRole)) eSimpleCondition = (Element) conditions.get(strRole);
    		else if(actions.containsKey(strRole)) eSimpleCondition = (Element) actions.get(strRole);
    		else{
    			MessageList.addError(doc.getId(), "Role <"+strRole+"> is not appearing at XConnector element. ", eLink, MessageList.ENGLISH);
    			MessageList.addError(doc.getId(), "Papel <"+strRole+"> não definido no elemento xconnector ('"+eLink.getAttribute("xconnector")+"').", eLink, MessageList.PORTUGUESE);
    			return false;
    		}
			
    		if(eSimpleCondition.hasAttribute("min") && !eSimpleCondition.getAttribute("min").equals("unbounded")){
				Integer min = new Integer(eSimpleCondition.getAttribute("min"));
				if(qtde.intValue() < min.intValue()){
					MessageList.addError(doc.getId(), 
							"There are problems with the quantity of elements with role "+strRole+". Please see min and max attributes in XConnector.",
							eLink, MessageList.ENGLISH);
					MessageList.addError(doc.getId(), 
							"Existem problemas com a quantidade de elmentos que possuem role='"+strRole+"'. Verifique a quantidade mínima e máxima no xconnector.",
							eLink, MessageList.PORTUGUESE);
					ok = false; 
				}
			}
			if(eSimpleCondition.hasAttribute("max") && !eSimpleCondition.getAttribute("max").equals("unbounded")){
				Integer max = new Integer(eSimpleCondition.getAttribute("max"));
				if(qtde.intValue() > max.intValue()){
					MessageList.addError(doc.getId(), 
							"There are problems with the quantity of elements with role "+strRole+". Please see min and max attributes in XConnector.",
							eLink, MessageList.ENGLISH);
					MessageList.addError(doc.getId(), 
							"Existem problemas com a quantidade de elementos que possuem role = '"+strRole+"'. Verifique a quantidade mínima e máxima no xconnector.",
							eLink, MessageList.PORTUGUESE);					
					ok = false;
				}
			}			
		}
		//Verifica se uma role deve aparecer e nÃ£o aparece
    	Iterator itCondition = conditions.keySet().iterator();
    	while(itCondition.hasNext()){
    		Element el = (Element) conditions.get(itCondition.next());
    		if(el.hasAttribute("min")){
    			Integer min = new Integer(el.getAttribute("min"));
    			String strRole = el.getAttribute("role");
    			if(min.intValue() > 0){
    				if(!qtdeRoles.containsKey(strRole)){
    					MessageList.addError(doc.getId(), 
    							"Bind element with role '"+strRole+"' must appear at least "+min.intValue()+" time(s).",
    							eLink, MessageList.ENGLISH);
    					MessageList.addError(doc.getId(), 
    							"Elemento <bind> com atributo role ='"+strRole+"' deve aparecer pelo menos "+min.intValue()+" veze(s).",
    							eLink, MessageList.PORTUGUESE);
    					ok = false;
    				}
    			}
    		}
    	}
    	return ok;
	}
    
    private void parseCausalConnector(Element eCausalConnector){
    	NodeList list = eCausalConnector.getChildNodes();
    	for(int i = 0; i < list.getLength(); i++){
    		Node node = list.item(i);
    		if(node.getNodeType() == Node.ELEMENT_NODE){
    			Element element = (Element) node;
    			if(element.getTagName().equals("simpleCondition") && element.hasAttribute("role"))
    				conditions.put(element.getAttribute("role"), element);
    			else if(element.getTagName().equals("simpleAction") && element.hasAttribute("role"))
    				actions.put(element.getAttribute("role"), element);
    			parseCausalConnector(element);
    		}
    	}
    }

	private boolean hasValidLinkXConnectorAttribute(Element eLink){
		if(!eLink.hasAttribute("xconnector")) return false; //msg gerada pelo DTD
		String idXConnector = eLink.getAttribute("xconnector");
		Element element = doc.getElement(idXConnector); 
		if( element==null ) {
			MessageList.addError(doc.getId(), 
				"There is not a <causalConnector> element with id '" + idXConnector + "'.",
			   	eLink, MessageList.ENGLISH);
			MessageList.addError(doc.getId(), 
					"O atributo xconnector ('" + idXConnector + "') referencia um elemento que não existe.",
				   	eLink, MessageList.PORTUGUESE);
			return false;
		}
		else if( element.getTagName().compareTo("causalConnector")!=0 ) {
			MessageList.addError(doc.getId(), 
					"The element with id '" + idXConnector + "' is not a <causalConnector> element.",
				   	eLink, MessageList.ENGLISH);
			MessageList.addError(doc.getId(), 
					"O valor do atributo xconnector ('" + idXConnector + "') não é um elemento <causalConnector>.",
				   	eLink, MessageList.PORTUGUESE);			
			return false;			
		}
        return true;
    }

    private boolean hasValidLinkIDAttribute(Element eLink){
        //TODO: All!
        return true;
    }

}

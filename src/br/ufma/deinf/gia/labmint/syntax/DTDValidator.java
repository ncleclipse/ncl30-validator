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

package br.ufma.deinf.gia.labmint.syntax;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;


import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.ufma.deinf.gia.labmint.message.MessageList;
import br.ufma.deinf.laws.ncl.DataType;
import br.ufma.deinf.laws.ncl.NCLStructure;

/**
 * Classe responsável pela validaçao Sintática do documento NCL.
 * @author roberto
 * 
 */
public class DTDValidator {

	private DTDValidator(){}

	public static boolean validate(String fileName, Element root) {
		boolean ret = true;
		String tagName = root.getTagName();
		
		NCLStructure nclStructure = NCLStructure.getInstance();
		
		if(!nclStructure.isElement(tagName)) {  
			MessageList.addError(fileName, "Unknown element '" + tagName + "'. This program does not check its child elements.", root, MessageList.ENGLISH); 
			MessageList.addError(fileName, "Elemento desconhecido '" + tagName + "'. Este programa n�o checa estes tipo de elementos filho.", root, MessageList.PORTUGUESE);			
			return false;
		}

		//get the expected attributes
		Map<String, Boolean> atts = nclStructure.getAttributes(tagName);

		//fill the attSet with the root's attributes
		NamedNodeMap  attList = root.getAttributes();
		Set<String> attSet = new HashSet(); 
		
		for(int i=0; i<attList.getLength(); i++) {
			Node node = (Node)attList.item(i); 
			
			if( node.getNodeType() == Node.ATTRIBUTE_NODE) {		
				if(!nclStructure.isAttribute(tagName, node.getNodeName())) {
					String strName = node.getNodeName();
					MessageList.addError(fileName, "Invalid attribute '" + strName + "' at <" + tagName + "> element.", root, MessageList.ENGLISH);
					MessageList.addError(fileName, "Atributo inv�lido '" + strName + "' no elemento '" + tagName + "'.", root, MessageList.PORTUGUESE);
					ret = false;
				}
				attSet.add(node.getNodeName());
			}
		}
	
		//check the mandatory attributes
		Iterator it = atts.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Boolean> entry = (Entry<String, Boolean>) it.next();
			if(entry.getValue().booleanValue() && !attSet.contains(entry.getKey())){
				MessageList.addError(fileName, "The attribute '" + entry.getKey() + "' is mandatory but is not present at <" + tagName + "> element.", root, MessageList.ENGLISH);
				MessageList.addError(fileName, "O atributo '" + entry.getKey() + "' � obrigat�rio mas n�o est� presente no elemento <" + tagName + ">.", root, MessageList.PORTUGUESE);
				ret = false;
			}
		}

		//get the expected attributes data type
		Map <String, Integer> dataType = nclStructure.getDataTypes(tagName);
		
		//validate data types
		for(int i=0; i<attList.getLength(); i++) {
			Node node = (Node)attList.item(i);
			if( node.getNodeType() == Node.ATTRIBUTE_NODE) {
				//value of attribute (user)
				String value = node.getTextContent();
				//data type of attribute (model)
				Integer mdt = nclStructure.getDataType(tagName, node.getNodeName());
				
				if(!DataType.isDataType(mdt.intValue(), value)) {
					String strName = node.getNodeName();
					MessageList.addError(fileName, "Invalid data type of attribute '" + strName + "' at <" + tagName + "> element. Id = "+root.getAttribute("id")+".", root, MessageList.ENGLISH);
					MessageList.addError(fileName, "Tipo de dado inv�lido para o atributo '" + strName + "' do elemento <" + tagName + ">. Identificador = "+root.getAttribute("id")+".", root, MessageList.PORTUGUESE);
					ret = false;
				}
			}
		}
		
		//check the child elements
		NodeList childList = (NodeList)root.getChildNodes();
		Map<String, Integer> childSet = new HashMap();
		
		for(int i=0; i<childList.getLength(); i++) {
			Node node = childList.item(i);
			if( node.getNodeType() == Node.ELEMENT_NODE ) {
				Element child = (Element)node;
				String childTagName = child.getTagName();
				if( nclStructure.isChild(tagName, childTagName) ) {
					if(childSet.containsKey(childTagName)){
						childSet.put(childTagName, childSet.get(childTagName)+1);
					}
					else childSet.put(childTagName, 1);
				}
				else {
					MessageList.addError(fileName, "The element '" + childTagName + "' is an invalid child of '" + tagName + "' element.", child, MessageList.ENGLISH);
					MessageList.addError(fileName, "O elemento <" + childTagName + "> n�o � um filho v�lido do elemento <'" + tagName + ">.", child, MessageList.PORTUGUESE);
					ret = false;
				}
			}
		}
		
		//check cardinality
		boolean flagAtLeastOne = false, flagAtLeastA = false, flagAtLeastB = false;
		boolean checkAtLeastOne = false, checkAtLeastA = false, checkAtLeastB = false;
		boolean checkAssessmentStatement = false;
		Map<String, Character> childCard = nclStructure.getChildrenCardinality(tagName);
		it = childCard.entrySet().iterator();
		while(it.hasNext()){
			//nomenclature:
			// '?' means optional (0 or 1 occurence)
			// '*' means optional repetition (0 or more occurences)
			// '+' means repetition (1 or more occureences)
			// '1' means 1 occurence
			// '#' means that all the child with '#' must have at least one occurence
			// 'a' means one of the two child elements
			// 'b' means one of the two child elements
			// '@' means the assessmentStatement special case, where <attributeAssessment> (<attributeAssessment>|<valueaAssessment>)

			Map.Entry<String, Character> entry = (Entry<String, Character>) it.next();
			char ch = entry.getValue().charValue();
			switch(ch) {
				case '?':
					if( childSet.containsKey(entry.getKey()) && childSet.get(entry.getKey())>1 ) {
						MessageList.addError(fileName, "The <" + tagName + "> element has more than one <" + entry.getKey() + "> child element that is optional (0 or 1 occurence).", root, MessageList.ENGLISH);
						MessageList.addError(fileName, "O elemento <" + tagName + "> possui mas de um filho <" + entry.getKey() + "> cuja cardinalidade � opcional (0 ou 1 ocorr�ncias).", root, MessageList.PORTUGUESE);
						ret = false; 
					}
				break;
				case '+':
					if( !childSet.containsKey(entry.getKey()) ) {
						MessageList.addError(fileName, "The <" + tagName + "> element must have at least one <" + entry.getKey() + "> child element.", root, MessageList.ENGLISH);
						MessageList.addError(fileName, "O elemento <" + tagName + "> deve possuir pelo menos um filho <" + entry.getKey() + ">.", root, MessageList.PORTUGUESE);
						ret = false;
					}
				break;
				case '1':
					if( !childSet.containsKey(entry.getKey()) || childSet.get(entry.getKey())!=1 ) {
						MessageList.addError(fileName, "The <" + tagName + "> element must have exactly one <" + entry.getKey() + "> child element.", root, MessageList.ENGLISH);
						MessageList.addError(fileName, "O elemento <" + tagName + "> deve possuir exatamente um filho <" + entry.getKey() + ">.", root, MessageList.PORTUGUESE);
						ret = false;
					}					
				break;
				case '#':
					if(childSet.containsKey(entry.getKey())) flagAtLeastOne=true;
					checkAtLeastOne = true;
				break;
				case 'a':
					if(childSet.containsKey(entry.getKey()) ) {
						if(childSet.get(entry.getKey())==1 && !flagAtLeastA) flagAtLeastA=true;
						else flagAtLeastA=false;
					}
					checkAtLeastA = true;
				break;
				case 'b':
					if(childSet.containsKey(entry.getKey())) {
						if(childSet.get(entry.getKey()) ==1 && !flagAtLeastB) flagAtLeastB=true;
						else flagAtLeastB=false;
					}
					checkAtLeastB = true;
				break;
				case '@':
					checkAssessmentStatement = true;
				break;
			}			
		}		
		
		//check the special cardinality cases
		if(checkAtLeastOne && !flagAtLeastOne) {
			MessageList.addError(fileName, "The <" + tagName + "> element must have at least one child element (cardinality #).", root, MessageList.ENGLISH);
			MessageList.addError(fileName, "O elemento <" + tagName + "> deve possuir pelo menos um elemento filho (cardinalidade #).", root, MessageList.PORTUGUESE);
			ret = false;
		}

		if(checkAtLeastA && !flagAtLeastA) {
			MessageList.addError(fileName, "The <" + tagName + "> element must have one child element (cardinality a).", root, MessageList.ENGLISH);
			MessageList.addError(fileName, "O elemento <" + tagName + "> deve possuir um elemento filho (cardinalidade a).", root, MessageList.PORTUGUESE);
			ret = false;
		}

		if(checkAtLeastB && !flagAtLeastB) {
			MessageList.addError(fileName, "The <" + tagName + "> element must have one child element (cardinality b).", root, MessageList.ENGLISH);
			MessageList.addError(fileName, "O elemento <" + tagName + "> deve possuir um elemento filho (cardinalidade b).", root, MessageList.PORTUGUESE);
			ret = false;
		}
		
		if(checkAssessmentStatement) {
			int attribute, value;
			if(!childSet.containsKey("attributeAssessment")) {
				attribute = 0;
			}
			else{
				attribute = childSet.get("attributeAssessment");
			}
			if(!childSet.containsKey("valueAssessment")) {
				value = 0;
			}
			else {
				value = childSet.get("valueAssessment");
			}
			if(!((attribute==2&&value==0) || (attribute==1&&value==1) ) ) { 
				MessageList.addError(fileName, "The <assessmentStatement> element must have two children <attributeAssessment> elements or"
						+ " an <attributeAssessment> and a <valueAssessment> children elements.", root, MessageList.ENGLISH);
				
				MessageList.addError(fileName, "O elemento <assessmentStatement> deve possuir dois elementos filho <attributeAssessment> ou"
						+ " um elemento filho <attributeAssessment> e outro <valueAssessment>.", root, MessageList.PORTUGUESE);
				
				ret = false;
			}
		}

		//recursively test the child nodes
		for(int i=0; i<childList.getLength(); i++) {
			Node child = childList.item(i);
			if(child.getNodeType() == Node.ELEMENT_NODE ) {
				if(!validate(fileName, (Element)child)) ret=false;
			}
		}
		return ret;
	}
}

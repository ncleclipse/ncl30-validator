/******************************************************************************
Este arquivo eh uma extensao do ambiente declarativo do middleware 
Ginga (Ginga-NCL).

Direitos Autorais Reservados (c)2007 LabMint, Laboratorio de Midias Interativas 
Departamento de Informatica, UFMA - Universidade Federal do Maranhao

Este programa eh software livre; voce pode redistribui-lo e/ou modifica-lo sob 
os termos da Licenca Publica Geral GNU versao 2 conforme publicada pela Free 
Software Foundation.

Este programa eh distribu�do na expectativa de que seja util, porem, SEM 
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

package br.ufma.deinf.gia.labmint.ncl;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe que representa a estrutura sintática da linguagem NCL.
 * @author roberto
 *
 */
public class NCLStructure {
	private static NCLStructure instance = null; 
	private static Map<String, Map<String, Boolean>> attributes = new HashMap<String, Map<String,Boolean>>();
	private static Map<String, Map<String, Integer>> dataTypes = new HashMap<String, Map<String,Integer>>();	
	private static Map<String, Map<String, Character> > nesting = new HashMap<String, Map<String,Character>>();
	/**
	 * Construtor privado Singleton
	 *
	 */
	private NCLStructure(){
		//nomenclature:
		// '?' means optional (0 or 1 occurence)
		// '*' means optional repetition (0 or more occurences)
		// '+' means repetition (1 or more occureences)
		// '1' means 1 occurence
		// '#' means that all the child with '#' must have at least one occurence
		// 'a' means one of the two child elements
		// 'b' means one of the two child elements
	
	//Extended Structure Module
		//ncl
		att("ncl", "id", false, DataType.ID);
		att("ncl", "title", false, DataType.STRING);
		att("ncl", "xmlns", true);
		att("ncl", "xmlns:xsi", false, DataType.URI);
		att("ncl", "xsi:schemaLocation", false);
		ct("ncl", "head", '?');
		ct("ncl", "body", '?');
	
		//head
		ct("head", "importedDocumentBase", '?');
		ct("head", "ruleBase", '?');
		ct("head", "transitionBase", '?');
		ct("head", "regionBase", '*');
		ct("head", "descriptorBase", '?');
		ct("head", "connectorBase", '?');
		ct("head", "meta", '*');
		ct("head", "metaData", '*');
	
		//body
		att("body", "id", false, DataType.ID);
		ct("body", "port", '*');
		ct("body", "attribute", '*');
		ct("body", "media", '*');
		ct("body", "context", '*');
		ct("body", "switch", '*');
		ct("body", "link", '*');
	
	//Extended Layout Module
		//regionBase
		att("regionBase", "id", false, DataType.ID);
		att("regionBase", "device", false, DataType.STRING);
		//alteracao temporaria:
		//TODO: voltar como eh o correto
		//ct("regionBase", "importBase", '#');
		//ct("regionBase", "region", '#');
		ct("regionBase", "importBase", '*');
		ct("regionBase", "region", '*');
	
		//region
		att("region", "id", true, DataType.ID);
		att("region", "title", false, DataType.STRING);
		att("region", "left", false, DataType.SIZE);
		att("region", "right", false, DataType.SIZE);
		att("region", "top", false, DataType.SIZE);
		att("region", "bottom", false, DataType.SIZE);
		att("region", "height", false, DataType.SIZE);
		att("region", "width", false, DataType.SIZE);
		att("region", "zIndex", false, DataType.INTEGER); // pode ser negativo (não ta funcionando)
		ct("region", "region", '*');
	
	//Extended Media Module
		//media
		att("media", "id", true, DataType.ID);
		att("media", "src", false, DataType.URI);
		att("media", "refer", false, DataType.ID);
		att("media", "newInstance", false, DataType.ID);
		att("media", "type", false, DataType.MEDIA_DESCRIPTION); // Não ta funcionando
		att("media", "descriptor", false, DataType.ID);
		ct("media", "area", '*');
		ct("media", "property", '*');
	
	//Extended Context Module
		//context
		att("context", "id", true, DataType.ID);
		att("context", "refer", false, DataType.ID);
		ct("context", "port", '*');
		ct("context", "property", '*');
		ct("context", "media", '*');
		ct("context", "context", '*');
		ct("context", "link", '*');
		ct("context", "switch", '*');
	
	//Extended MediaContentAnchor Module
		//area
		att("area", "id", true, DataType.ID);
		att("area", "coords", false, DataType.COORDINATE); 
		att("area", "begin", false, DataType.TIME);
		att("area", "end", false, DataType.TIME);
		att("area", "text", false, DataType.STRING);
		att("area", "position", false, DataType.INTEGER); 
		att("area", "first", false, DataType.INTEGER);
		att("area", "last", false, DataType.INTEGER);
		att("area", "anchorLabel", false, DataType.STRING);
	
	//Extended CompositeNodeInterface Module
		//port
		att("port", "id", true, DataType.ID);
		att("port", "component", true, DataType.ID); // Verificar se é isso mesmo
		att("port", "interface", false, DataType.ID); // Verificar se é isso mesmo
	
	//Extended AttributeAnchor Module
		//property
		att("property", "name", true, DataType.ID);
		att("property", "value", false, DataType.STRING);
	
	//Extended SwitchInterface Module
		//switchPort
		att("switchPort", "id", true, DataType.ID);
		ct("switchPort", "mapping", '+');
	
		//mapping
		att("mapping", "component", true, DataType.ID);
		att("mapping", "interface", false, DataType.ID);
	
	//Extended Descriptor Module
		//descriptor
		att("descriptor", "id", true, DataType.ID);
		att("descriptor", "player", false, DataType.STRING);
		att("descriptor", "explicitDur", false, DataType.TIME);
		att("descriptor", "region", false, DataType.ID);
		att("descriptor", "freeze", false);
		att("descriptor", "moveLeft", false);
		att("descriptor", "moveRight", false);
		att("descriptor", "moveDown", false);
		att("descriptor", "moveUp", false);
		att("descriptor", "focusIndex", false);
		att("descriptor", "focusBorderColor", false);
		att("descriptor", "focusBorderWidth", false);
		att("descriptor", "focusBorderTransparency", false);
		att("descriptor", "focusSrc", false, DataType.URI); // é isso mesmo ??
		att("descriptor", "focusSelSrc", false, DataType.URI); // é isso mesmo ??
		att("descriptor", "selBorderColor", false);
		att("descriptor", "transIn", false);
		att("descriptor", "transOut", false);
		ct("descriptor", "descriptorParam", '*');
	
		//descriptorParam
		att("descriptorParam", "name", true, DataType.ID);
		att("descriptorParam", "value", true, DataType.STRING);
	
		//descriptorBase
		att("descriptorBase", "id", false, DataType.ID);
		ct("descriptorBase", "importBase", '#');
		ct("descriptorBase", "descriptor", '#');
		ct("descriptorBase", "descriptorSwitch", '#');
	
	//Extended Linking Module
		//bind
		att("bind", "role", true, DataType.ID);
		att("bind", "component", true, DataType.ID);
		att("bind", "interface", false, DataType.ID);
		att("bind", "descriptor", false, DataType.ID);
		ct("bind", "bindParam", '*');
	
		//bindParam
		att("bindParam", "name", true, DataType.ID);
		att("bindParam", "value", true, DataType.STRING);
	
		//linkParam
		att("linkParam", "name", true, DataType.ID);
		att("linkParam", "value", true, DataType.STRING);
	
		//link
		att("link", "id", false, DataType.ID);
		att("link", "xconnector", true, DataType.XCONNECTOR);
		ct("link", "linkParam", '*');
		ct("link", "bind", '+');
	
	//Extended CausalConnectorFunctionality Module
		//causalConnector
		att("causalConnector", "id", true, DataType.ID);
		ct("causalConnector", "connectorParam", '*');
		ct("causalConnector", "simpleCondition", 'a');
		ct("causalConnector", "compoundCondition", 'a');
		ct("causalConnector", "simpleAction", 'b');
		ct("causalConnector", "compoundAction", 'b');
	
		//connectorParam
		att("connectorParam", "name", true, DataType.ID);
		att("connectorParam", "type", false);
	
		//simpleCondition
		att("simpleCondition", "role", true, DataType.ID);
		att("simpleCondition", "delay", false);
		att("simpleCondition", "eventType", false);
		att("simpleCondition", "key", false);
		att("simpleCondition", "transition", false);
		att("simpleCondition", "min", false, DataType.QUANTITY);
		att("simpleCondition", "max", false, DataType.QUANTITY);
		att("simpleCondition", "qualifier", false, DataType.BOOLEAN_OPERATOR);
	
		//compoundCondition
		att("compoundCondition", "operator", true, DataType.BOOLEAN_OPERATOR);
		att("compoundCondition", "delay", false, DataType.TIME);
		ct("compoundCondition", "simpleCondition", '#');
		ct("compoundCondition", "compoundCondition", '#');
		ct("compoundCondition", "assessmentStatement", '*');
		ct("compoundCondition", "compoundStatement", '*');
		
		//simpleAction
		att("simpleAction", "role", true, DataType.ID);
		att("simpleAction", "delay", false);
		att("simpleAction", "eventType", false);
		att("simpleAction", "actionType", false);
		att("simpleAction", "value", false);
		att("simpleAction", "min", false, DataType.QUANTITY);
		att("simpleAction", "max", false, DataType.QUANTITY);
		att("simpleAction", "qualifier", false, DataType.SYNCHRONISM_OPERATOR);
		att("simpleAction", "repeat", false);
		att("simpleAction", "repeatDelay", false);
		att("simpleAction", "duration", false);
		att("simpleAction", "by", false);
		att("simpleAction", "", false);
		att("simpleAction", "", false);
	
		//compoundAction
		att("compoundAction", "operator", true, DataType.SYNCHRONISM_OPERATOR);
		att("compoundAction", "delay", false);
		ct("compoundAction", "simpleAction", '#');
		ct("compoundAction", "compoundAction", '#');
	
		//assessmentStatement
		att("assessmentStatement", "comparator", true);
		//ct("assessmentStatement", "attributeAssessment", '1');
		ct("assessmentStatement", "attributeAssessment", '@');
		ct("assessmentStatement", "valueAssessment", '@');
	
		//attributeAssessment
		att("attributeAssessment", "role", true);
		att("attributeAssessment", "eventType", true);
		att("attributeAssessment", "key", false);
		att("attributeAssessment", "attributeType", true);
		att("attributeAssessment", "offset", false);
	
		//valueAssessment
		att("valueAssessment", "value", true);
		
		//compoundStatement
		att("compoundStatement", "operator", true, DataType.BOOLEAN_OPERATOR);
		att("compoundStatement", "isNegated", false);
		ct("compoundStatement", "assessmentStatement", '#');
		ct("compoundStatement", "compoundStatement", '#');
	
	//Extended ConnectorBase Module
		//connectorBase
		att("connectorBase", "id", false, DataType.ID);
		ct("connectorBase", "importBase", '*');
		ct("connectorBase", "causalConnector", '*');
	
	//Extended TestRule Module
		//ruleBase
		att("ruleBase", "id", false, DataType.ID);
		ct("ruleBase", "importBase", '#');
		ct("ruleBase", "rule", '#');
		ct("ruleBase", "compositeRule", '#');
	
		//rule
		att("rule", "id", true, DataType.ID);
		att("rule", "var", true);
		att("rule", "comparator", true);
		att("rule", "value", true);
	
		//compositeRule
		att("compositeRule", "id", true, DataType.ID);
		att("compositeRule", "operator", true, DataType.BOOLEAN_OPERATOR);
		ct("compositeRule", "rule", '#');
		ct("compositeRule", "compositeRule", '#');
	
	//Extended TestRule Module
		//bindRule
		att("bindRule", "constituent", true);
		att("bindRule", "rule", true);
	
	//Extended ContentControl Module
		//switch
		att("switch", "id", true, DataType.ID);
		att("switch", "refer", false);
		ct("switch", "defaultComponent", '?');
		ct("switch", "switchPort", '*');
		ct("switch", "bindRule", '*');
		ct("switch", "media", '*');
		ct("switch", "context", '*');
		ct("switch", "switch", '*'); 
	
		//defaultComponent
		att("defaultComponent", "component", true);
	
	//Extended DescriptorControl Module
		//descriptorSwitch
		att("descriptorSwitch", "id", true, DataType.ID);
		ct("descriptorSwitch", "defaultDescriptor", '?');
		ct("descriptorSwitch", "descriptor", '*');
		ct("descriptorSwitch", "bindRule", '*');
	
		//defaultDescriptor
		att("defaultDescriptor", "descriptor", true);
	
	//Extended Import Module
		//importBase
		att("importBase", "alias", true);
		att("importBase", "documentURI", true, DataType.URI);
		att("importBase", "region", false);
	
		//importedDocumentBase
		att("importedDocumentBase", "id", false, DataType.ID);
		ct("importedDocumentBase", "importNCL", '+');
	
		//importNCL
		att("importNCL", "alias", true);
		att("importNCL", "documentURI", true, DataType.URI);
	
	//Extended TransitionBase Module
		//transitionBase
		att("transitionBase", "id", false, DataType.ID);
		ct("transitionBase", "importBase", '#');
		ct("transitionBase", "transition", '#');
	
	//Extended BasicTransition module
		//transition
		att("transition", "id", true, DataType.ID);
		att("transition", "type", true);
		att("transition", "subtype", false);
		att("transition", "dur", false);
		att("transition", "startProgress", false);
		att("transition", "endProgress", false);
		att("transition", "direction", false);
		att("transition", "fadeColor", false);
		att("transition", "horRepeat", false);
		att("transition", "vertRepeat", false);
		att("transition", "borderWidth", false);
		att("transition", "borderColor", false);
	
	//Extended Metainformation module
		//meta
		att("meta", "name", true);
		att("meta", "content", true);
	
		//metadata
			//TODO: "RDF tree" as child
	}

	/**
	 * Retorna a instância única da classe
	 * @return A instância de {@link NCLStructure}
	 */
	public static NCLStructure getInstance(){
		if(instance == null) return new NCLStructure();
		else return instance;
	}
	
	/**
	 * 
	 * @return attributes
	 */
	public static Map<String, Map<String, Boolean>> getAttributes() {
		return attributes;
	}
	
	/**
	 * 
	 * @param attributes
	 */
	public void setAttributes(Map<String, Map<String, Boolean>> attributes) {
		this.attributes = attributes;
	}
	
	/**
	 * 
	 * @return nesting
	 */
	public Map<String, Map<String, Character>> getNesting() {
		return nesting;
	}
	
	/**
	 * 
	 * @param nesting
	 */
	public void setNesting(Map<String, Map<String, Character>> nesting) {
		this.nesting = nesting;
	}
	
	private void att(String elementName, String attributeName, boolean required)
	{
		if( !attributes.containsKey(elementName) ) {
			attributes.put(elementName, new HashMap());
		}
		Map <String, Boolean> atts = instance.attributes.get(elementName);
		if( atts == null) {
			atts = new HashMap();
			attributes.put(elementName, atts);
		}
		atts.put(attributeName, required);
	}
	//Além de gravar se é requerido ou não verifica o tipo do dado
	private void att(String elementName, String attributeName, boolean required, int type)
	{
		if( !attributes.containsKey(elementName) ) {
			attributes.put(elementName, new HashMap());
			dataTypes.put(elementName, new HashMap());
		}
		Map <String, Boolean> atts = instance.attributes.get(elementName);
		Map <String, Integer> dt = instance.dataTypes.get(elementName);
		if( atts == null) {
			atts = new HashMap();
			attributes.put(elementName, atts);
		}
		if(dt == null){
			dt = new HashMap();
			dataTypes.put(elementName, dt);
		}
		atts.put(attributeName, required);
		dt.put(attributeName, type);
	}
	
	private void ct(String elementName, String childElementName, char cardinality){
		if( !nesting.containsKey(elementName) ) {
			nesting.put(elementName, new HashMap());
		}
		Map <String, Character> childElement = instance.nesting.get(elementName);
		if( childElement == null) {
			childElement = new HashMap();
			nesting.put(elementName, childElement);
		}
		childElement.put(childElementName, cardinality);		
	}
	
	/**
	 * Verifica se existe um elemento com o nome elementName na estrutura da linguagem.
	 * @param elementName
	 * @return
	 */
	public static boolean isElement(String elementName) {
		return attributes.containsKey(elementName) || nesting.containsKey(elementName);
	}
	
	/**
	 * @param elementName
	 * @param attributeName
	 * @return
	 */
	public static boolean isAttribute(String elementName, String attributeName) {
		if( !getAttributes().containsKey(elementName) ) return false;
		else return getAttributes().get(elementName).containsKey(attributeName);
	}
	public static Map<String, Map<String, Integer>> getDataTypes() {
		return dataTypes;
	}

	public static void setDataTypes(Map<String, Map<String, Integer>> dataTypes) {
		NCLStructure.dataTypes = dataTypes;
	}
	/**
	 * Retorna o dataType do atributo, conforme a classe DataType
	 * @param elementName
	 * @param attributeName
	 * @return data type of attribute
	 */
	public static Integer getDataType(String elementName, String attributeName){
		if(!getDataTypes().containsKey(elementName)
			|| !getDataTypes().get(elementName).containsKey(attributeName)) return new Integer(DataType.UNKNOWN);
		else return getDataTypes().get(elementName).get(attributeName);
	}
	
	/**
	 * 
	 * @param parentElementName
	 * @param childElementName
	 * @return
	 */
	public static boolean isChild(String parentElementName, String childElementName) {
		if( !nesting.containsKey(parentElementName) ) return false;
		else return nesting.get(parentElementName).containsKey(childElementName);
	}
	
	/**
	 * 
	 * @param elementName
	 * @return
	 */
	public static Map<String, Character> getChildrenCardinality(String elementName) 
	{
		Map<String, Character> empty = new HashMap();
		if( !nesting.containsKey(elementName) ) return empty;
		else return nesting.get(elementName);
	}
	
	/**
	 * 
	 * @param elementName
	 * @return
	 */
	public static Map<String, Boolean> getAttributes(String elementName)
	{
		Map<String, Boolean> empty = new HashMap();
		if( !attributes.containsKey(elementName) ) return empty;
		else return attributes.get(elementName);
	}
	
	public static Map<String, Integer> getDataTypes(String elementName)
	{
		Map<String, Integer> empty = new HashMap();
		if( !attributes.containsKey(elementName) ) return empty;
		else return dataTypes.get(elementName);
	}
	
	/**
	 * 
	 * @param elementName
	 * @return
	 */
	public boolean isRequiredId(String elementName) {
		if(!instance.getAttributes().containsKey(elementName)) return false;
		if(!instance.getAttributes().get(elementName).containsKey("id")) return false;
		return instance.getAttributes().get(elementName).containsKey("id")==true;
	}
}

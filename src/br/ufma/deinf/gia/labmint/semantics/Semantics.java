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

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.ufma.deinf.gia.labmint.document.NclValidatorDocument;


public class Semantics {
	private NclValidatorDocument document;
	private Map<String,ElementValidation> elements = new HashMap<String, ElementValidation>();
	
	public Semantics(NclValidatorDocument document) {
			this.document = document;
			//Structure Functionality
			elements.put("ncl", new Ncl(document));
			elements.put("head", new Head(document));
			elements.put("body", new Body(document));
			
			//Layout Functionality
			elements.put("regionBase", new RegionBase(document));
			elements.put("region", new Region(document));
			
			//Components Functionality
			elements.put("media", new Media(document));
			elements.put("context", new Context(document));
			
			//Interfaces Functionality
			elements.put("area", new Area(document));
			elements.put("port", new Port(document));
			elements.put("property", new Property(document));
			elements.put("switchPort", new SwitchPort(document));
			elements.put("mapping", new Mapping(document));	
			
			//Presentation Specification Functionality
			elements.put("descriptor", new Descriptor(document));	
			elements.put("descriptorParam", new DescriptorParam(document));
			elements.put("descriptorBase", new DescriptorBase(document));
			
			//Linking Functionality
			elements.put("bind", new Bind(document));
			elements.put("bindParam", new BindParam(document));
			elements.put("linkParam", new LinkParam(document));
			elements.put("link", new Link(document));
			
			//Connectors Functionality
			elements.put("causalConnector", new CausalConnector(document));
			elements.put("connectorParam", new ConnectorParam(document));
			elements.put("simpleCondition", new SimpleCondition(document));
			elements.put("compoundCondition", new CompoundCondition(document));
			elements.put("simpleAction", new SimpleAction(document));
			elements.put("compoundAction", new CompoundAction(document));
			elements.put("assessmentStatement", new AssessmentStatement(document));
			elements.put("attributeAssessment", new AttributeAssessment(document));
			elements.put("valueAssessment", new ValueAssessment(document));
			elements.put("compoundStatement", new CompoundStatement(document));
			elements.put("connectorBase", new ConnectorBase(document));
			
			//Presentation Control Functionality
			elements.put("ruleBase", new RuleBase(document));
			elements.put("rule", new Rule(document));
			elements.put("compositeRule", new CompositeRule(document));
			elements.put("bindRule", new BindRule(document));
			elements.put("switch", new Switch(document));
			elements.put("defaultComponent", new DefaultComponent(document));
			elements.put("descriptorSwitch", new DescriptorSwitch(document));
			elements.put("defaultDescriptor", new DefaultDescriptor(document));
			
			//Timing Functionality
			// No tem nada??? :P
			
			//Reuse Functionality
			elements.put("importBase", new ImportBase(document));
			elements.put("importedDocumentBase", new ImportedDocumentBase(document));
			elements.put("importNCL", new ImportNCL(document));
			
			//SMIL Transition Effects Functionality
			elements.put("transitionBase", new TransitionBase(document));
			elements.put("transition", new Transition(document));
			
			//SMIL Meta-Information Functionality
			elements.put("meta", new Meta(document));
			elements.put("metadata", new Metadata(document));
	}
	
	public boolean parse(Element element){
		boolean ret = true;
		
		String tagName = element.getTagName();
		if( this.elements.containsKey(tagName) ) {
			if( !(this.elements.get(tagName)).validate(element) ) {
				ret = false;
			}
		}
		
		NodeList elementNodeList = element.getChildNodes();
		for (int  i = 0; i < elementNodeList.getLength(); i++) {
			Node node = elementNodeList.item(i);
			if (node.getNodeType()== Node.ELEMENT_NODE) {
				if(! this.parse( (Element)node )) ret = false;
			}
		}
		
		return ret;
	}
	
	public String getDocumentId(){
		return document.getId();
	}	
}


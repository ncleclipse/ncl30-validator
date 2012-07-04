/*******************************************************************************
 * This file is part of the NCL authoring environment - NCL Eclipse.
 *
 * Copyright (C) 2007-2012, LAWS/UFMA.
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

package br.ufma.deinf.gia.labmint.semantics;

import java.util.Vector;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.ufma.deinf.gia.labmint.document.NclValidatorDocument;
import br.ufma.deinf.gia.labmint.message.MessageList;

public class Mapping extends ElementValidation {

	public Mapping(NclValidatorDocument doc) {
		super(doc);
	}

	public boolean validate(Element eMapping) {
		boolean resultado = true;

		//Verifica se o atributo 'interface' de <mapping> aponta para um elemento <port>.
		if (!hasValidMappingInterfaceAttribute(eMapping))
			resultado = false;

		//Verifica se o atributo 'component' de <mapping> aponta para um elemento <context>.
		if (!hasValidMappingComponentAttribute(eMapping))
			resultado = false;

		return resultado;
	}

	private boolean hasValidMappingInterfaceAttribute(Element eMapping) {
		if (eMapping.hasAttribute("interface")) {

			if (!eMapping.hasAttribute("component")) {
				MessageList.addError(doc.getId(), 4001, eMapping);
				return false;
			}

			String idInterface = eMapping.getAttribute("interface");
			String idComponent = eMapping.getAttribute("component");
			Element element = doc.getElement(idComponent);
			if (element == null) {
				return false;
			}

			NodeList nodeList = element.getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element child = (Element) node;
					if (child.hasAttribute("id")
							&& child.getAttribute("id").compareTo(idInterface) == 0) {
						
						if (child.getTagName().compareTo("area") != 0
								&& child.getTagName().compareTo("port") != 0
								&& child.getTagName().compareTo("switchPort") != 0) {

							Vector<String> args = new Vector<String>();
							args.add(idComponent);
							MessageList.addError(doc.getId(), 4002, eMapping,
									args);
							return false;
						}
						return true;
					
					} 
					else if( child.hasAttribute("name") 
							&& child.getAttribute("name").compareTo(idInterface) == 0){
						
						if (child.getTagName().compareTo("property") != 0){
							Vector<String> args = new Vector<String>();
							args.add(idComponent);
							MessageList.addError(doc.getId(), 4002, eMapping,
									args);
							return false;
						}
						return true;
					}
				}
			}
			Vector<String> args = new Vector<String>();
			args.add(idComponent);
			MessageList.addError(doc.getId(), 4002, eMapping, args);
			return false;
		}
		return true;
	}

	private boolean hasValidMappingComponentAttribute(Element eMapping) {

		//Obrigado ter um atributo 'component' - Verificacao feita no Sintatico.
		if (!eMapping.hasAttribute("component"))
			return false;
		String idComponent = eMapping.getAttribute("component");
		Element element = doc.getElement(idComponent);
		if (element == null) {
			Vector<String> args = new Vector<String>();
			args.add(idComponent);
			MessageList.addError(doc.getId(), 4003, eMapping, args);
			return false;
		} else if (element.getTagName().compareTo("context") != 0
				&& element.getTagName().compareTo("media") != 0
				&& element.getTagName().compareTo("switch") != 0) {
			Vector<String> args = new Vector<String>();
			args.add(idComponent);
			MessageList.addError(doc.getId(), 4003, eMapping, args);
			return false;

		}
		return true;
	}

}

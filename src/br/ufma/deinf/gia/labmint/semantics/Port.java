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

public class Port extends ElementValidation {

	public Port(NclValidatorDocument doc) {
		super(doc);
	}

	private String idPort = null;

	public boolean validate(Element ePort) {
		boolean resultado = true;

		idPort = ePort.getAttribute("id");

		// Verifica se o atributo 'interface' de <port> aponta para um elemento
		// <area>.
		if (!hasValidPortInterfaceAttribute(ePort))
			resultado = false;

		//
		if (!hasValidPortIDAttribute(ePort))
			resultado = false;

		// Verifica se o atributo 'component' de <port> aponta para um elemento
		// <media>.
		if (!hasValidPortComponentAttribute(ePort))
			resultado = false;

		return resultado;
	}

	private boolean hasValidPortInterfaceAttribute(Element ePort) {
		if (!hasValidPortComponentAttribute(ePort))
			return false;
		if (ePort.hasAttribute("interface")) {
			if (!ePort.hasAttribute("component")) {
				MessageList.addError(doc.getId(), 4203, ePort);
				return false;
			}

			if (!ePort.hasAttribute("component"))
				return false;// msg gerada pelo DTD
			String idComponent = ePort.getAttribute("component");
			String idInterface = ePort.getAttribute("interface");
			Element element = doc.getElement(idComponent);

			Vector<String> alreadyTestedId = new Vector<String>();
			do {
				if (element == null) {
					break;
				}
				alreadyTestedId.add(idComponent);

				NodeList nodeList = element.getChildNodes();
				for (int i = 0; i < nodeList.getLength(); i++) {
					Node node = nodeList.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element child = (Element) node;
						String tag = child.getTagName();
						String id = null;
						if (child.hasAttribute("id"))
							id = child.getAttribute("id");
						else if (child.hasAttribute("name"))
							id = child.getAttribute("name");
						if (id != null && id.compareTo(idInterface) == 0) {
							if (tag.compareTo("area") != 0
									&& tag.compareTo("property") != 0
									&& tag.compareTo("port") != 0
									&& tag.compareTo("switchPort") != 0) {
								Vector<String> args = new Vector<String>();
								args.add(idPort);
								MessageList.addError(doc.getId(), 4204, ePort,
										args);
								return false;
							}
							return true;
						}
					}
				}

				idComponent = element.getAttribute("refer");
				if (idComponent == null
						|| idComponent == "" 
						|| alreadyTestedId.contains(idComponent))
					break;
				element = doc.getElement(idComponent);
			} while (true);

			Vector<String> args = new Vector<String>();
			args.add(idPort);
			args.add(idComponent);
			MessageList.addError(doc.getId(), 4205, ePort, args);
			return false;
		}
		return true;
	}

	private boolean hasValidPortIDAttribute(Element ePort) {
		// TODO: All!
		return true;
	}

	private boolean hasValidPortComponentAttribute(Element ePort) {
		if (!ePort.hasAttribute("component"))
			return false; // msg gerada pelo DTD
		String idComponent = ePort.getAttribute("component");
		Element element = doc.getElement(idComponent);
		if (element == null) {
			Vector<String> args = new Vector<String>();
			args.add(idComponent);
			MessageList.addError(doc.getId(), 4201, ePort, args);
			return false;
		} else if (element.getTagName().compareTo("media") != 0
				&& element.getTagName().compareTo("context") != 0
				&& element.getTagName().compareTo("switch") != 0) {
			Vector<String> args = new Vector<String>();
			args.add(idComponent);
			MessageList.addError(doc.getId(), 4201, ePort, args);
			return false;
		} else {
			// Verifica se o atributo referenciado por component está no mesmo
			// contexto
			Node parent = ePort.getParentNode();
			NodeList nodeList = parent.getChildNodes();
			boolean ok = false;
			Element child = null;
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					child = (Element) node;
					if (child.hasAttribute("id")) { // msg gerada pelo DTD
						if (child.getAttribute("id").equals(idComponent)) {
							ok = true;
							break;
						}
					}
				}
			}
			if (!ok) {
				Vector<String> args = new Vector<String>();
				args.add(idComponent);
				MessageList.addError(doc.getId(), 4202, ePort, args);
				return false;
			}
			/*
			 * //TODO Atributo interface é obrigatório se elemento apontado é um
			 * context (Acho que isso não é verdade)
			 * if(element.getTagName().equals("context") &&
			 * !ePort.hasAttribute("interface")){
			 * MessageList.addError(doc.getId(),
			 * "The Element pointed by attribute component is a Context, so the attribute interface is mandatory."
			 * , element); }
			 */
		}
		return true;
	}

}

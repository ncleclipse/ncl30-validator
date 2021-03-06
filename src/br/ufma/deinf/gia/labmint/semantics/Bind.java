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

/**
 * 
 * @author <a href="mailto:robertogerson@telemidia.puc-rio.br">Roberto Gerson
 *         Azevedo</a>
 * 
 */
public class Bind extends ElementValidation {

	public Bind(NclValidatorDocument doc) {
		super(doc);
	}

	public boolean validate(Element eBind) {
		boolean resultado = true;

		// Verifica se o atributo 'interface' do <bind> aponta para um elemento
		// <area> ou <property>.
		if (!hasValidBindInterfaceAttribute(eBind))
			resultado = false;

		// Verifica se o atributo 'descriptor' do <bind> aponta para um
		// <descriptor>.
		if (!hasValidBindDescriptorAttribute(eBind))
			resultado = false;

		// Verifica se o atributo role do bind referencia um action,
		// condition ou assessmentStatement do link referenciado por
		// este conector
		if (!hasValidBindRoleAttribute(eBind))
			resultado = false;

		// Verifica se o atributo 'component' do <bind> aponta para um elemento
		// <media>.
		if (!hasValidBindComponentAttribute(eBind))
			resultado = false;

		return resultado;
	}

	private boolean hasValidBindInterfaceAttribute(Element eBind) {
		if (!eBind.hasAttribute("interface")) {
			return true;
		}

		String idInterface = eBind.getAttribute("interface");
		if (!eBind.hasAttribute("component")) {
			MessageList.addError(doc.getId(), 3201, eBind);
			return false;
		}

		String idComponent = eBind.getAttribute("component");
		Element element = doc.getElement(idComponent);
		if (element == null)
			return false;
		Vector <String> referPath = new Vector <String>();
		referPath.add(idComponent);
		while (element.hasAttribute("refer")) {
			NodeList nodeList = element.getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element childElement = (Element) node;
					String tagName = childElement.getTagName();
					if (tagName.compareTo("area") == 0
							&& childElement.hasAttribute("id")) {
						if (childElement.getAttribute("id").compareTo(
								idInterface) == 0) {
							return true;
						}
					}
					if (tagName.compareTo("property") == 0
							&& childElement.hasAttribute("name")) {
						if (childElement.getAttribute("name").compareTo(
								idInterface) == 0) {
							return true;
						}
					}
					if (tagName.compareTo("port") == 0
							&& childElement.hasAttribute("id")) {
						if (childElement.getAttribute("id").compareTo(
								idInterface) == 0) {
							return true;
						}
					}
					if (tagName.compareTo("switchPort") == 0
							&& childElement.hasAttribute("id")) {
						if (childElement.getAttribute("id").compareTo(
								idInterface) == 0) {
							return true;
						}
					}
				}
			}
			idComponent = element.getAttribute("refer");
			element = doc.getElement(idComponent);
			if (element == null || referPath.contains(idComponent))
				return false;
			else
				referPath.add(idComponent);
		}
		if (element == null) {
			return false;
		}
		NodeList nodeList = element.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element childElement = (Element) node;
				String tagName = childElement.getTagName();
				if (tagName.compareTo("area") == 0
						&& childElement.hasAttribute("id")) {
					if (childElement.getAttribute("id").compareTo(idInterface) == 0) {
						return true;
					}
				}
				if (tagName.compareTo("property") == 0
						&& childElement.hasAttribute("name")) {
					if (childElement.getAttribute("name")
							.compareTo(idInterface) == 0) {
						return true;
					}
				}
				if (tagName.compareTo("port") == 0
						&& childElement.hasAttribute("id")) {
					if (childElement.getAttribute("id").compareTo(idInterface) == 0) {
						return true;
					}
				}
				if (tagName.compareTo("switchPort") == 0
						&& childElement.hasAttribute("id")) {
					if (childElement.getAttribute("id").compareTo(idInterface) == 0) {
						return true;
					}
				}
			}
		}
		MessageList.addError(doc.getId(), 3202, eBind);
		return false;
	}

	private boolean hasValidBindDescriptorAttribute(Element eBind) {
		if (eBind.hasAttribute("descriptor")) {
			String idDescriptor = eBind.getAttribute("descriptor");
			Element element = doc.getElement(idDescriptor);
			if (element == null) {
				Vector<String> args = new Vector<String>();
				args.add(idDescriptor);
				MessageList.addError(doc.getId(), 3203, eBind, args);
				return false;
			} else if ( (element.getTagName().compareTo("descriptor") != 0)
					&& (element.getTagName().compareTo("descriptorSwitch") != 0) ) {
				Vector<String> args = new Vector<String>();
				args.add(idDescriptor);
				MessageList.addError(doc.getId(), 3203, eBind, args);
				return false;
			}
		}
		return true;
	}

	private boolean hasValidBindRoleAttribute(Element eBind) {
		// TODO: All!
		return true;
	}

	private boolean hasValidBindComponentAttribute(Element eBind) {
		// Obrigado a ter um atributo 'component' - Verificacao feita no
		// Sintatico DTD.
		if (!eBind.hasAttribute("component"))
			return false;
		String idComponent = eBind.getAttribute("component");
		Element element = doc.getElement(idComponent);

		if (element == null) {
			Vector<String> args = new Vector<String>();
			args.add(idComponent);
			MessageList.addError(doc.getId(), 3204, eBind, args);
			return false;
		} else if (element.getTagName().equals("body")
				|| element.getTagName().equals("media")
				|| element.getTagName().equals("context")
				|| element.getTagName().equals("switch")) {

			if (componentIsInMyContext(eBind))
				return true;
		}
		Vector<String> args = new Vector<String>();
		args.add(idComponent);
		MessageList.addError(doc.getId(), 3204, eBind, args);
		return false;
	}

	/**
	 * Verifica se o component do Bind referencia o seu context (ou Body)
	 * 
	 * @param eBind
	 * @return
	 */
	private boolean componentIsMyContext(Element eBind) {
		Element eLink = (Element) eBind.getParentNode();
		if (eLink == null)
			return false;
		Element eContext = (Element) eLink.getParentNode();
		if (eContext == null)
			return false;

		if ((eContext.getTagName().equals("body") || eContext.getTagName()
				.equals("context"))
				&& eContext.hasAttribute("id")
				&& eContext.getAttribute("id").equals(
						eBind.getAttribute("component")))
			return true;

		return false;
	}

	/**
	 * Check if the component refered by the component attribute is in the same
	 * context that the <bind>.
	 * 
	 * @param eBind
	 * @return true if the component is in the same context that the bind
	 */
	private boolean componentIsInMyContext(Element eBind) {
		// if bind refered the own context return true.
		if (componentIsMyContext(eBind))
			return true;

		// DTD checks if component exists and print message.
		if (!eBind.hasAttribute("component"))
			return true;

		Element component = doc.getElement(eBind.getAttribute("component"));

		// if the media refered is application/x-ginga-settings always is valid
		if (component.getTagName().equals("media")
				&& component.hasAttribute("type")
				&& component.getAttribute("type").equals(
						"application/x-ginga-settings")) {
			return true;
		}

		Element eLink = (Element) eBind.getParentNode();
		// this is error, but the DTD validates
		if (eLink == null)
			return false;
		Element eContext = (Element) eLink.getParentNode();
		if (eContext == null)
			return false;

		// get the element with id refered by component in the context
		Element element = doc.getElementChild(eContext, eBind
				.getAttribute("component"));

		// if it doesn't exists return false
		if (element == null)
			return false;

		// check if the component refered is media, switch or component
		if (element.getTagName().equals("media")
				|| element.getTagName().equals("switch")
				|| element.getTagName().equals("context"))
			return true;

		return false;
	}
}

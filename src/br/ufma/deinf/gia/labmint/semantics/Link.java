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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.ufma.deinf.gia.labmint.document.NclValidatorDocument;
import br.ufma.deinf.gia.labmint.message.MessageList;

public class Link extends ElementValidation {
	HashMap conditions = null;
	HashMap actions = null;
	HashMap connectorParam = null;
	HashMap linkAndBindParam = null;

	public Link(NclValidatorDocument doc) {
		super(doc);
	}

	private String idLink = null;

	public boolean validate(Element eLink) {
		boolean resultado = true;

		// Verifica se o atributo 'xconnector' da <link> aponta para um
		// <causalConnerctor> .
		// Verifica se o link usado realmente exite!
		if (!hasValidLinkXConnectorAttribute(eLink))
			resultado = false;
		else {
			// Verifica se a estrutura do Documento Link "bate" com a do
			// XConnector
			if (!hasValidXConnectorStructure(eLink))
				resultado = false;
		}

		//
		if (!hasValidLinkIDAttribute(eLink))
			resultado = false;

		return resultado;
	}

	private boolean hasValidXConnectorStructure(Element eLink) {
		// Verifica se a estrutura do link "Bate" com a estrutura do XConnector
		// definido
		if (!eLink.hasAttribute("xconnector"))
			return false; // msg gerada pelo DTD
		String idXConnector = eLink.getAttribute("xconnector");
		Element eCausalConnector = doc.getElement(idXConnector);
		conditions = new HashMap<String, Element>();
		actions = new HashMap<String, Element>();
		connectorParam = new HashMap<String, Element>();
		linkAndBindParam = new HashMap<String, Element>();

		parseCausalConnector(eCausalConnector);

		// Valida o Máximo e Mínimo dos Links
		HashMap qtdeRoles = new HashMap<String, Integer>();
		NodeList list = eLink.getChildNodes();

		// Computa as quantidades
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				if (!element.getTagName().equals("bind"))
					continue;
				if (!element.hasAttribute("role"))
					return false; // msg gerada pelo DTD
				String strRole = element.getAttribute("role");
				if (qtdeRoles.containsKey(strRole)) {
					Integer v = (Integer) qtdeRoles.get(strRole);
					qtdeRoles.put(strRole, new Integer(v.intValue() + 1));
				} else
					qtdeRoles.put(strRole, 1);
			}
		}
		// Valida as quantidades
		Iterator qtdeRolesIt = qtdeRoles.keySet().iterator();
		boolean ok = true;
		while (qtdeRolesIt.hasNext()) {
			String strRole = (String) qtdeRolesIt.next();
			Integer qtde = (Integer) qtdeRoles.get(strRole);
			Element eSimpleCondition = null;
			if (conditions.containsKey(strRole))
				eSimpleCondition = (Element) conditions.get(strRole);
			else if (actions.containsKey(strRole))
				eSimpleCondition = (Element) actions.get(strRole);
			else {
				// Ajeitar isso! Foi comentado por causa do PegaDali!
				// Vector <String> args = new Vector <String>();
				// args.add(strRole);
				// args.add(eLink.getAttribute("xconnector"));
				// MessageList.addError(doc.getId(), 3901, eLink, args);
				// return false;
				continue;
			}

			// validate the min cardinality
			Integer min = new Integer(1);
			boolean unbounded = false;
			if (eSimpleCondition.hasAttribute("min")) {
				if (!eSimpleCondition.getAttribute("min").equals("unbounded"))
					min = new Integer(eSimpleCondition.getAttribute("min"));
				else
					unbounded = true;
			}

			if (!unbounded) {
				if (qtde.intValue() < min.intValue()) {
					Vector<String> args = new Vector<String>();
					args.add(qtde.toString());
					args.add(strRole);
					args.add(min.toString());

					MessageList.addError(doc.getId(), 3902, eLink, args);

					ok = false;
				}
			}

			Integer max = new Integer(1);
			unbounded = false;

			if (eSimpleCondition.hasAttribute("max")) {
				if (!eSimpleCondition.getAttribute("max").equals("unbounded"))
					max = new Integer(eSimpleCondition.getAttribute("max"));
				else
					unbounded = true;
			}

			if (!unbounded) {
				if (qtde.intValue() > max.intValue()) {
					Vector<String> args = new Vector<String>();
					args.add(qtde.toString());
					args.add(strRole);
					args.add(max.toString());

					MessageList.addError(doc.getId(), 3903, eLink, args);

					ok = false;
				}
			}
		}

		// Verifica se uma condition deve aparecer e não aparece
		Iterator itCondition = conditions.keySet().iterator();
		while (itCondition.hasNext()) {
			Element el = (Element) conditions.get(itCondition.next());
			Integer min = new Integer(1);
			boolean unbounded = false;
			if (el.hasAttribute("min")) {
				if (!el.getAttribute("min").equals("unbounded"))
					min = new Integer(el.getAttribute("min"));
				else
					unbounded = true;
			}
			String strRole = el.getAttribute("role");

			if (unbounded) {
				if (!qtdeRoles.containsKey(strRole)) {
					Vector<String> args = new Vector<String>();
					args.add(strRole);
					args.add("unbounded");
					MessageList.addError(doc.getId(), 3904, eLink, args);
					ok = false;
				}
				continue;
			}

			if (min.intValue() > 0) {
				if (!qtdeRoles.containsKey(strRole)) {
					Vector<String> args = new Vector<String>();
					args.add(strRole);
					args.add(min.toString());
					MessageList.addError(doc.getId(), 3904, eLink, args);
					ok = false;
				}
			}
		}
		// Verifica se uma action deve aparecer e não aparece
		Iterator itAction = actions.keySet().iterator();
		while (itAction.hasNext()) {
			Element el = (Element) actions.get(itAction.next());
			Integer min = new Integer(1);
			boolean unbounded = false;
			if (el.hasAttribute("min")) {
				if (!el.getAttribute("min").equals("unbounded"))
					min = new Integer(el.getAttribute("min"));
				else
					unbounded = true;
			}
			String strRole = el.getAttribute("role");

			if (unbounded) {
				if (!qtdeRoles.containsKey(strRole)) {
					Vector<String> args = new Vector<String>();
					args.add(strRole);
					args.add("unbounded");
					MessageList.addError(doc.getId(), 3904, eLink, args);
					ok = false;
				}
				continue;
			}

			if (min.intValue() > 0) {
				if (!qtdeRoles.containsKey(strRole)) {
					Vector<String> args = new Vector<String>();
					args.add(strRole);
					args.add(min.toString());
					MessageList.addError(doc.getId(), 3904, eLink, args);
					ok = false;
				}
			}
		}

		// TODO: validate if the parameter was declared when it is necessary
		/*
		 * parseLink(); //valida se os parametros definidos no conector sao
		 * todos especificados no link Iterator itConnectorParam =
		 * connectorParam.keySet().iterator();
		 * while(itConnectorParam.hasNext()){ String nameConnectorParam =
		 * (String) itConnectorParam.next(); Element atualConnectorParam =
		 * (Element) connectorParam.get(nameConnectorParam);
		 * if(atualConnectorParam == null){ Vector<String> args = new
		 * Vector<String>(); args.add(nameConnectorParam);
		 * MessageList.addWarning(doc.getId(), 4601, eLink, args); } }
		 */
		/*for (int i = 0; i < list.getLength(); i++) {

			if (list.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Node nod = (Element) list.item(i);
				Element element = (Element) nod;
				
				 * if(conditions.containsKey(element.getAttribute("role"))){
				 * continue; }else
				 * if(!actions.containsKey(element.getAttribute("role"))){
				 * System.out.println("n tem"); continue; }else
				 * if(actions.containsKey(element.getAttribute("role"))){
				 * continue;
				 * 
				 * }else
				 * if(conditions.containsKey(element.getAttribute("role"))){
				 * System.out.println("c nao tem"); }
				 
				if (element.hasAttribute("role")) {
					if ((conditions.containsKey(element.getAttribute("role")))
							|| (actions.containsKey(element
									.getAttribute("role")))) {
						continue;
					} else {
						Vector<String> args = new Vector<String>();
						args.add(element.getAttribute("role"));
						args.add(eCausalConnector.getAttribute("id"));

						MessageList.addError(doc.getId(), 3907, element, args);

						ok = false;
					}
				}
			}
		}*/
		return ok;
	}

	private void parseCausalConnector(Element eCausalConnector) {
		NodeList list = eCausalConnector.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				if (element.getTagName().equals("simpleCondition")
						&& element.hasAttribute("role"))
					conditions.put(element.getAttribute("role"), element);
				else if (element.getTagName().equals("simpleAction")
						&& element.hasAttribute("role"))
					actions.put(element.getAttribute("role"), element);
				else if (element.getTagName().equals("attributeAssessment")
						&& element.hasAttribute("role")) {
					conditions.put(element.getAttribute("role"), element);
				} else if (element.getTagName().equals("connectorParam")
						&& element.hasAttribute("name"))
					connectorParam.put(element.getAttribute("name"), element);
				parseCausalConnector(element);
			}
		}
	}

	private void parseLink(Element eLink) {
		NodeList list = eLink.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				if ((element.getTagName().equals("linkParam") && element
						.hasAttribute("name"))
						|| (element.getTagName().equals("bindParam") && element
								.hasAttribute("name")))
					linkAndBindParam.put(element.getAttribute("name"), element);
				parseLink(element);
			}
		}
	}

	private boolean hasValidLinkXConnectorAttribute(Element eLink) {
		if (!eLink.hasAttribute("xconnector"))
			return false; // msg gerada pelo DTD
		String idXConnector = eLink.getAttribute("xconnector");
		Element element = doc.getElement(idXConnector);
		if (element == null) {
			Vector<String> args = new Vector<String>();
			args.add(idXConnector);
			MessageList.addError(doc.getId(), 3905, eLink, args);
			return false;
		} else if (element.getTagName().compareTo("causalConnector") != 0) {
			Vector<String> args = new Vector<String>();
			args.add(idXConnector);
			MessageList.addError(doc.getId(), 3905, eLink, args);
			return false;
		}
		return true;
	}

	private boolean hasValidLinkIDAttribute(Element eLink) {
		// TODO: All!
		return true;
	}

}

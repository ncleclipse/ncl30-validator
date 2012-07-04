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
import java.util.Vector;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.ufma.deinf.gia.labmint.document.NclValidatorDocument;
import br.ufma.deinf.gia.labmint.message.Message;
import br.ufma.deinf.gia.labmint.message.MessageList;

public class CausalConnector extends ElementValidation {
	HashMap<String, Element> roles;
	Element eCausalConnector;

	public CausalConnector(NclValidatorDocument doc) {
		super(doc);
	}

	public boolean validate(Element eCausalConnector) {
		boolean resultado = true;
		// Inicializa roles
		roles = new HashMap<String, Element>();
		this.eCausalConnector = eCausalConnector;
		//
		if (!hasValidCausalConnectorIDAttribute(eCausalConnector))
			resultado = false;

		//
		if (!hasChildrenValidRoleAttribute(eCausalConnector))
			resultado = false;
		return resultado;
	}

	private boolean hasChildrenValidRoleAttribute(Element element) {
		// TODO O atributo role deve ser �nico no connector
		boolean ret = true;

		NodeList nodeList = element.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element childElement = (Element) node;
				if (childElement.hasAttribute("role")) {
					String strRole = childElement.getAttribute("role");
					// System.out.println(strRole);
					if (this.roles.containsKey(strRole)) {
						Vector<String> args = new Vector<String>();
						args.add(strRole);
						args.add(eCausalConnector.getAttribute("id"));

						MessageList.addError(doc.getId(), 3301, childElement,
								args);
						ret = false;
					} else
						this.roles.put(strRole, childElement);
				}
				// Recursive
				ret = hasChildrenValidRoleAttribute(childElement) && ret;
			}
		}
		return ret;
	}

	private boolean hasValidCausalConnectorIDAttribute(Element eCausalConnector) {
		// TODO: All!
		return true;
	}

}

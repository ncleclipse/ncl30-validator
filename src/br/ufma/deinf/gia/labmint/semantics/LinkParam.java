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

public class LinkParam extends ElementValidation {

	public LinkParam(NclValidatorDocument doc) {
		super(doc);
	}

	public boolean validate(Element eLinkParam) {
		boolean resultado = true;

		//
		if (!hasValidLinkParamNameAttribute(eLinkParam))
			resultado = false;

		//
		if (!hasValidLinkParamValueAttribute(eLinkParam))
			resultado = false;

		return resultado;
	}

	private boolean hasValidLinkParamNameAttribute(Element eLinkParam) {
		Element link = (Element) eLinkParam.getParentNode();
		String nameBindParam = eLinkParam.getAttribute("name");

		if (nameBindParam == null)
			return false; // is validated in Structure

		if (link != null) {
			if (link.hasAttribute("xconnector")) {
				String xconnector = link.getAttribute("xconnector");

				// TODO: make a function to this
				// equal to bindParam
				Element causalConnector = doc.getElement(xconnector);
				if (causalConnector != null) {
					// find for connectorParam with this name
					NodeList child = causalConnector.getChildNodes();
					for (int i = 0; i < child.getLength(); i++) {
						Node node = child.item(i);
						if (node.getNodeType() == Node.ELEMENT_NODE) {
							Element atual = (Element) node;
							if (atual.getTagName().equals("connectorParam")) {
								if (atual.getAttribute("name") != null
										&& atual.getAttribute("name").equals(
												nameBindParam))
									return true;
							}
						}
					}
					// TODO: end make a function to this

					Vector<String> args = new Vector<String>();
					args.add(nameBindParam);
					args.add(xconnector);
					MessageList.addWarning(doc.getId(), 4601, eLinkParam, args);
				}
			}
		}
		return false;
	}

	private boolean hasValidLinkParamValueAttribute(Element eLinkParam) {
		// TODO: All!
		return true;
	}

}

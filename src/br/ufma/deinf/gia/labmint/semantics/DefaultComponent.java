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

import br.ufma.deinf.gia.labmint.document.NclValidatorDocument;
import br.ufma.deinf.gia.labmint.message.MessageList;

public class DefaultComponent extends ElementValidation {

	public DefaultComponent(NclValidatorDocument doc) {
		super(doc);
	}

	private String idDefaultComponent = null;

	public boolean validate(Element eDefaultComponent) {
		boolean resultado = true;

		idDefaultComponent = eDefaultComponent.getAttribute("id");

		// Verifica se o atributo 'component' de <defaultComponent> aponta para
		// um <component>.
		if (!hasValidDefaultComponentComponentAttribute(eDefaultComponent))
			resultado = false;

		return resultado;
	}

	private boolean hasValidDefaultComponentComponentAttribute(
			Element eDefaultComponent) {
		if (!eDefaultComponent.hasAttribute("component"))
			return false;

		String idComponent = eDefaultComponent.getAttribute("component");
		if (doc.getElement(idComponent) == null) {
			Vector<String> args = new Vector<String>();
			args.add(idComponent);

			MessageList.addError(doc.getId(), 3501, eDefaultComponent, args);
			return false;
		}
		if (!componentIsInMySwitch(eDefaultComponent)) {
			Vector<String> args = new Vector<String>();
			args.add(idComponent);

			MessageList.addError(doc.getId(), 3502, eDefaultComponent, args);
			return false;
		}
		return true;
	}

	private boolean componentIsInMySwitch(Element eDefaultComponent) {
		// DTD checks if component exists and print message.
		if (!eDefaultComponent.hasAttribute("component"))
			return true;

		Element component = doc.getElement(eDefaultComponent
				.getAttribute("component"));

		Element eSwitch = (Element) eDefaultComponent.getParentNode();
		// this is error, but the DTD validates
		if (eSwitch == null)
			return false;

		// get the element with id refered by component in the context
		Element element = doc.getElementChild(eSwitch, eDefaultComponent
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

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

public class DefaultDescriptor extends ElementValidation {

	public DefaultDescriptor(NclValidatorDocument doc) {
		super(doc);
	}

	private String idDefaultDescriptor = null;

	public boolean validate(Element eDefaultDescriptor) {
		boolean resultado = true;

		idDefaultDescriptor = eDefaultDescriptor.getAttribute("id");

		//Verifica se o atributo 'descriptor' do <defaultDescriptor> aponta para um <descriptor>.
		if (!hasValidDefaultDescriptorDescriptorAttribute(eDefaultDescriptor))
			resultado = false;

		return resultado;
	}

	private boolean hasValidDefaultDescriptorDescriptorAttribute(
			Element eDefaultDescriptor) {
		if (!eDefaultDescriptor.hasAttribute("descriptor"))
			return false;
		String idDescriptor = eDefaultDescriptor.getAttribute("descriptor");
		Element element = doc.getElement(idDescriptor);
		Vector<String> args = new Vector<String>();
		args.add(idDescriptor);
		if (element == null) {
			MessageList.addError(doc.getId(), 3601, eDefaultDescriptor, args);
			return false;
		} else if (element.getTagName().compareTo("descriptor") != 0) {
			MessageList.addError(doc.getId(), 3601, eDefaultDescriptor, args);
			return false;
		}
		return true;
	}

}

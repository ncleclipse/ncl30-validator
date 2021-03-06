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

public class Descriptor extends ElementValidation {

	public Descriptor(NclValidatorDocument doc) {
		super(doc);
	}

	public boolean validate(Element eDescriptor) {
		boolean resultado = true;

		//
		if (!hasValidDescriptorFocusSelSrcAttribute(eDescriptor))
			resultado = false;

		//
		if (!hasValidDescriptorSelBorderColorAttribute(eDescriptor))
			resultado = false;

		//
		if (!hasValidDescriptorPlayerAttribute(eDescriptor))
			resultado = false;

		//
		if (!hasValidDescriptorMoveLeftAttribute(eDescriptor))
			resultado = false;

		//
		if (!hasValidDescriptorFocusIndexAttribute(eDescriptor))
			resultado = false;

		//
		if (!hasValidDescriptorFocusSrcAttribute(eDescriptor))
			resultado = false;

		//
		if (!hasValidDescriptorExplicitDurAttribute(eDescriptor))
			resultado = false;

		//
		if (!hasValidDescriptorMoveUpAttribute(eDescriptor))
			resultado = false;

		//
		if (!hasValidDescriptorMoveDownAttribute(eDescriptor))
			resultado = false;

		//
		if (!hasValidDescriptorTransOutAttribute(eDescriptor))
			resultado = false;

		//Verifica se o atributo 'region' do elemento <descriptor> aponta para um <region>.
		if (!hasValidDescriptorRegionAttribute(eDescriptor))
			resultado = false;

		//
		if (!hasValidDescriptorFocusBorderColorAttribute(eDescriptor))
			resultado = false;

		//
		if (!hasValidDescriptorIDAttribute(eDescriptor))
			resultado = false;

		//
		if (!hasValidDescriptorMoveRightAttribute(eDescriptor))
			resultado = false;

		//
		if (!hasValidDescriptorFocusBorderWidthAttribute(eDescriptor))
			resultado = false;

		//
		if (!hasValidDescriptorTransInAttribute(eDescriptor))
			resultado = false;

		//
		if (!hasValidDescriptorFocusBorderTransparencyAttribute(eDescriptor))
			resultado = false;

		//
		if (!hasValidDescriptorFreezeAttribute(eDescriptor))
			resultado = false;

		return resultado;
	}

	private boolean hasValidDescriptorFocusSelSrcAttribute(Element eDescriptor) {
		if(eDescriptor.hasAttribute("focusSelSrc"))
		{
			String focusSelSrc = eDescriptor.getAttribute("focusSelSrc");
			return doc.validateSrc(focusSelSrc, eDescriptor);
		}
		return true;
	}

	private boolean hasValidDescriptorSelBorderColorAttribute(
			Element eDescriptor) {
		//TODO: All!
		return true;
	}

	private boolean hasValidDescriptorPlayerAttribute(Element eDescriptor) {
		//TODO: All!
		return true;
	}

	private boolean hasValidDescriptorMoveLeftAttribute(Element eDescriptor) {
		//TODO: All!
		return true;
	}

	private boolean hasValidDescriptorFocusIndexAttribute(Element eDescriptor) {
		//TODO: All!
		return true;
	}

	private boolean hasValidDescriptorFocusSrcAttribute(Element eDescriptor) {
		if(eDescriptor.hasAttribute("focusSrc"))
		{
			String focusSelSrc = eDescriptor.getAttribute("focusSrc");
			return doc.validateSrc(focusSelSrc, eDescriptor);
		}
		return true;
	}

	private boolean hasValidDescriptorExplicitDurAttribute(Element eDescriptor) {
		//TODO: All!
		return true;
	}

	private boolean hasValidDescriptorMoveUpAttribute(Element eDescriptor) {
		//TODO: All!
		return true;
	}

	private boolean hasValidDescriptorMoveDownAttribute(Element eDescriptor) {
		//TODO: All!
		return true;
	}

	private boolean hasValidDescriptorTransOutAttribute(Element eDescriptor) {
		//TODO: All!
		return true;
	}

	private boolean hasValidDescriptorRegionAttribute(Element eDescriptor) {
		if (eDescriptor.hasAttribute("region")) {
			String idRegion = eDescriptor.getAttribute("region");
			Element element = doc.getElement(idRegion);
			Vector<String> args = new Vector<String>();
			args.add(idRegion);
			if (element == null) {
				MessageList.addError(doc.getId(), 3701, eDescriptor, args);
				return false;
			} else if (element.getTagName().compareTo("region") != 0) {
				MessageList.addError(doc.getId(), 3701, eDescriptor, args);
				return false;
			}
		}
		return true;
	}

	private boolean hasValidDescriptorFocusBorderColorAttribute(
			Element eDescriptor) {
		//TODO: All!
		return true;
	}

	private boolean hasValidDescriptorIDAttribute(Element eDescriptor) {
		//TODO: All!
		return true;
	}

	private boolean hasValidDescriptorMoveRightAttribute(Element eDescriptor) {
		//TODO: All!
		return true;
	}

	private boolean hasValidDescriptorFocusBorderWidthAttribute(
			Element eDescriptor) {
		//TODO: All!
		return true;
	}

	private boolean hasValidDescriptorTransInAttribute(Element eDescriptor) {
		//TODO: All!
		return true;
	}

	private boolean hasValidDescriptorFocusBorderTransparencyAttribute(
			Element eDescriptor) {
		//TODO: All!
		return true;
	}

	private boolean hasValidDescriptorFreezeAttribute(Element eDescriptor) {
		//TODO: All!
		return true;
	}

}

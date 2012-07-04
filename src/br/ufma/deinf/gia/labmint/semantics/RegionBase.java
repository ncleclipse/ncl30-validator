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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Element;

import br.ufma.deinf.gia.labmint.document.NclValidatorDocument;
import br.ufma.deinf.gia.labmint.message.MessageList;

public class RegionBase extends ElementValidation {

	public RegionBase(NclValidatorDocument doc) {
		super(doc);
	}

	public boolean validate(Element eRegionBase) {
		boolean resultado = true;

		//
		if (!hasValidRegionBaseDeviceAttribute(eRegionBase))
			resultado = false;

		//
		if (!hasValidRegionBaseIDAttribute(eRegionBase))
			resultado = false;

		return resultado;
	}

	private boolean hasValidRegionBaseDeviceAttribute(Element eRegionBase) {
		//Atributo device deve ser da forma systemScreen(i) ou systemAudio(i)
		if (eRegionBase.hasAttribute("device")) {
			String attString = eRegionBase.getAttribute("device");
			Pattern p = Pattern.compile("systemScreen\\(([0-9])+\\)");
			Matcher m = p.matcher(attString);
			if (!m.matches()) {
				p = Pattern.compile("systemAudio\\(([0-9])+\\)");
				;
				m = p.matcher(attString);
				if (!m.matches()) {
					MessageList.addWarning(doc.getId(), 4301, eRegionBase);
				}
			}
		}
		return true;
	}

	private boolean hasValidRegionBaseIDAttribute(Element eRegionBase) {
		return true;
	}

}

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

import org.w3c.dom.Element;

import br.ufma.deinf.gia.labmint.document.NclValidatorDocument;

public class Region extends ElementValidation {

	public Region(NclValidatorDocument doc) {
		super(doc);
	}

	public boolean validate(Element eRegion) {
		boolean resultado = true;

		//
		if (!hasValidRegionBottomAttribute(eRegion))
			resultado = false;

		//
		if (!hasValidRegionRightAttribute(eRegion))
			resultado = false;

		//
		if (!hasValidRegionZIndexAttribute(eRegion))
			resultado = false;

		//
		if (!hasValidRegionTopAttribute(eRegion))
			resultado = false;

		//
		if (!hasValidRegionIDAttribute(eRegion))
			resultado = false;

		//
		if (!hasValidRegionHeightAttribute(eRegion))
			resultado = false;

		//
		if (!hasValidRegionWidthAttribute(eRegion))
			resultado = false;

		//
		if (!hasValidRegionLeftAttribute(eRegion))
			resultado = false;

		//
		if (!hasValidRegionTitleAttribute(eRegion))
			resultado = false;

		return resultado;
	}

	private boolean hasValidRegionBottomAttribute(Element eRegion) {
		//TODO: All!
		return true;
	}

	private boolean hasValidRegionRightAttribute(Element eRegion) {
		//TODO: All!
		return true;
	}

	private boolean hasValidRegionZIndexAttribute(Element eRegion) {
		//TODO: All!
		return true;
	}

	private boolean hasValidRegionTopAttribute(Element eRegion) {
		//TODO: All!
		return true;
	}

	private boolean hasValidRegionIDAttribute(Element eRegion) {
		//TODO: All!
		return true;
	}

	private boolean hasValidRegionHeightAttribute(Element eRegion) {
		//TODO: All!
		return true;
	}

	private boolean hasValidRegionWidthAttribute(Element eRegion) {
		//TODO: All!
		return true;
	}

	private boolean hasValidRegionLeftAttribute(Element eRegion) {
		//TODO: All!
		return true;
	}

	private boolean hasValidRegionTitleAttribute(Element eRegion) {
		//TODO: All!
		return true;
	}

}

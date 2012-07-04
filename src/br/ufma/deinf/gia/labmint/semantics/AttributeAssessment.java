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

public class AttributeAssessment extends ElementValidation {

	public AttributeAssessment(NclValidatorDocument doc) {
		super(doc);
	}

	public boolean validate(Element eAttributeAssessment) {
		boolean resultado = true;

		//
		if (!hasValidAttributeAssessmentKeyAttribute(eAttributeAssessment))
			resultado = false;

		//
		if (!hasValidAttributeAssessmentOffsetAttribute(eAttributeAssessment))
			resultado = false;

		//
		if (!hasValidAttributeAssessmentAttributeTypeAttribute(eAttributeAssessment))
			resultado = false;

		//
		if (!hasValidAttributeAssessmentRoleAttribute(eAttributeAssessment))
			resultado = false;

		//
		if (!hasValidAttributeAssessmentEventTypeAttribute(eAttributeAssessment))
			resultado = false;

		return resultado;
	}

	private boolean hasValidAttributeAssessmentKeyAttribute(
			Element eAttributeAssessment) {
		//TODO: All!
		return true;
	}

	private boolean hasValidAttributeAssessmentOffsetAttribute(
			Element eAttributeAssessment) {
		//TODO: All!
		return true;
	}

	private boolean hasValidAttributeAssessmentAttributeTypeAttribute(
			Element eAttributeAssessment) {
		//TODO: All!
		return true;
	}

	private boolean hasValidAttributeAssessmentRoleAttribute(
			Element eAttributeAssessment) {
		//TODO: All!
		return true;
	}

	private boolean hasValidAttributeAssessmentEventTypeAttribute(
			Element eAttributeAssessment) {
		//TODO: All!
		return true;
	}

}

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
import br.ufma.deinf.laws.ncl.AttributeValues;
import br.ufma.deinf.laws.ncl.DataType;

public class SimpleCondition extends ElementValidation {
	
	public SimpleCondition(NclValidatorDocument doc) {
		super(doc);
	}

	public boolean validate(Element eSimpleCondition) {
		boolean resultado = true;

		//
		if (!hasValidSimpleConditionKeyAttribute(eSimpleCondition))
			resultado = false;

		//
		if (!hasValidSimpleConditionDelayAttribute(eSimpleCondition))
			resultado = false;

		//
		if (!hasValidSimpleConditionQualifierAttribute(eSimpleCondition))
			resultado = false;

		//
		if (!hasValidSimpleConditionMaxAttribute(eSimpleCondition))
			resultado = false;

		//
		if (!hasValidSimpleConditionRoleAttribute(eSimpleCondition))
			resultado = false;

		//
		if (!hasValidSimpleConditionTransitionAttribute(eSimpleCondition))
			resultado = false;

		//
		if (!hasValidSimpleConditionEventTypeAttribute(eSimpleCondition))
			resultado = false;

		//
		if (!hasValidSimpleConditionMinAttribute(eSimpleCondition))
			resultado = false;

		return resultado;
	}

	private boolean hasValidSimpleConditionKeyAttribute(Element eSimpleCondition) {
		//TODO: All!
		return true;
	}

	private boolean hasValidSimpleConditionDelayAttribute(
			Element eSimpleCondition) {
		//TODO: All!
		return true;
	}

	private boolean hasValidSimpleConditionQualifierAttribute(
			Element eSimpleCondition) {
		/*
		if(eSimpleCondition.hasAttribute("max")){
			String attMax = eSimpleCondition.getAttribute("max");
			int maxValue = -1;
			try{
				maxValue = (new Integer(attMax)).intValue();
			}catch(Exception e){}
			if(attMax.equals("unbounded") || (maxValue > 1) ){
				if(!eSimpleCondition.hasAttribute("qualifier")){
					MessageList.addError(doc.getId(), 
							4401,
							eSimpleCondition);
				}
			}
		}*/
		return true;
	}

	private boolean hasValidSimpleConditionMaxAttribute(Element eSimpleCondition) {
		//TODO: All!
		return true;
	}

	private boolean hasValidSimpleConditionRoleAttribute(
			Element eSimpleCondition) {
		
		// TODO: Check consistency among onBegin, onEnd, etc. and the 
		// transition and eventType attributes? 
		if(eSimpleCondition.hasAttribute("role")) {
			String role = eSimpleCondition.getAttribute("role");
			if(!AttributeValues.getValues(DataType.SIMPLECONDITION_ROLE).contains(role) &&
					( !eSimpleCondition.hasAttribute("transition") || 
							!eSimpleCondition.hasAttribute("eventType"))) {
				
				Vector <String> args = new Vector <String>();
				args.add(AttributeValues.getValues(DataType.SIMPLECONDITION_ROLE).toString());
				
				MessageList.addError(doc.getId(), 
						4402,
						eSimpleCondition,
						args);
				return false;
			}
		}
		return true;
	}

	private boolean hasValidSimpleConditionTransitionAttribute(
			Element eSimpleCondition) {
		//TODO: All!
		return true;
	}

	private boolean hasValidSimpleConditionEventTypeAttribute(
			Element eSimpleCondition) {
		//TODO: All!
		return true;
	}

	private boolean hasValidSimpleConditionMinAttribute(Element eSimpleCondition) {
		//TODO: All!
		return true;
	}

}

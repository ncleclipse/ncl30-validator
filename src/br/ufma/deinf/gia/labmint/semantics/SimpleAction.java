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

public class SimpleAction extends ElementValidation {

	public SimpleAction(NclValidatorDocument doc) {
		super(doc);
	}

	public boolean validate(Element eSimpleAction) {
		boolean resultado = true;

		//
		if (!hasValidSimpleActionByAttribute(eSimpleAction))
			resultado = false;

		//
		if (!hasValidSimpleActionValueAttribute(eSimpleAction))
			resultado = false;

		//
		if (!hasValidSimpleActionRepeatDelayAttribute(eSimpleAction))
			resultado = false;

		//
		if (!hasValidSimpleActionActionTypeAttribute(eSimpleAction))
			resultado = false;

		//
		if (!hasValidSimpleActionDelayAttribute(eSimpleAction))
			resultado = false;

		//
		if (!hasValidSimpleActionDurationAttribute(eSimpleAction))
			resultado = false;

		//
		if (!hasValidSimpleActionQualifierAttribute(eSimpleAction))
			resultado = false;

		//
		if (!hasValidSimpleActionMaxAttribute(eSimpleAction))
			resultado = false;

		//
		if (!hasValidSimpleActionRoleAttribute(eSimpleAction))
			resultado = false;

		//
		if (!hasValidSimpleActionEventTypeAttribute(eSimpleAction))
			resultado = false;

		//
		if (!hasValidSimpleActionRepeatAttribute(eSimpleAction))
			resultado = false;

		//
		if (!hasValidSimpleActionMinAttribute(eSimpleAction))
			resultado = false;

		return resultado;
	}

	private boolean hasValidSimpleActionByAttribute(Element eSimpleAction) {
		//TODO: All!
		return true;
	}

	private boolean hasValidSimpleActionValueAttribute(Element eSimpleAction) {
		//TODO: All!
		return true;
	}

	private boolean hasValidSimpleActionRepeatDelayAttribute(
			Element eSimpleAction) {
		//TODO: All!
		return true;
	}

	private boolean hasValidSimpleActionActionTypeAttribute(
			Element eSimpleAction) {
		//TODO: All!
		return true;
	}

	private boolean hasValidSimpleActionDelayAttribute(Element eSimpleAction) {
		//TODO: All!
		return true;
	}

	private boolean hasValidSimpleActionDurationAttribute(Element eSimpleAction) {
		//TODO: All!
		return true;
	}

	private boolean hasValidSimpleActionQualifierAttribute(Element eSimpleAction) {
		/*
		if(eSimpleAction.hasAttribute("max")){
			String attMax = eSimpleAction.getAttribute("max");
			int maxValue = -1;
			try{
				maxValue = (new Integer(attMax)).intValue();
			}catch(Exception e){}
			if(attMax.equals("unbounded") || (maxValue > 1) ){
				if(!eSimpleAction.hasAttribute("qualifier")){
					MessageList.addError(doc.getId(), 
							4401,
							eSimpleAction);
				}
			}
		}*/
		return true;
	}

	private boolean hasValidSimpleActionMaxAttribute(Element eSimpleAction) {
		//TODO: All!
		return true;
	}

	private boolean hasValidSimpleActionRoleAttribute(Element eSimpleAction) {
		
		// TODO: Check consistency among start, stop, etc. and the 
		// transition and eventType attributes?
		
		if(eSimpleAction.hasAttribute("role")) {
			String role = eSimpleAction.getAttribute("role");
			if(!AttributeValues.getValues(DataType.SIMPLEACTION_ROLE).contains(role) &&
					( !eSimpleAction.hasAttribute("transition") || 
							!eSimpleAction.hasAttribute("eventType"))) {
				
				Vector <String> args = new Vector <String>();
				args.add(AttributeValues.getValues(DataType.SIMPLEACTION_ROLE).toString());
				
				MessageList.addError(doc.getId(), 
						4402,
						eSimpleAction,
						args);
				return false;
			}
		}
		return true;
	}

	private boolean hasValidSimpleActionEventTypeAttribute(Element eSimpleAction) {
		//TODO: All!
		return true;
	}

	private boolean hasValidSimpleActionRepeatAttribute(Element eSimpleAction) {
		//TODO: All!
		return true;
	}

	private boolean hasValidSimpleActionMinAttribute(Element eSimpleAction) {
		//TODO: All!
		return true;
	}

}

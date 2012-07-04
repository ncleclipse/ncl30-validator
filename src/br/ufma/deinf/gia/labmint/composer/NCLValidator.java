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
package br.ufma.deinf.gia.labmint.composer;

import java.util.Iterator;
import java.util.Vector;

import br.ufma.deinf.gia.labmint.message.*;
import br.ufma.deinf.gia.labmint.document.NclValidatorDocument;
import br.ufma.deinf.gia.labmint.message.MessageList;
import br.ufma.deinf.gia.labmint.semantics.Semantics;
import br.ufma.deinf.gia.labmint.syntax.DTDValidator;
import br.ufma.deinf.laws.ncl.NCLStructure;

public class NCLValidator {
	private static NCLValidator instance = null;

	/**
	 * Define o construtor como privado - Singleton
	 *
	 */
	private NCLValidator() {

	};

	public static boolean validate(Vector<NclValidatorDocument> docs) {
		if (instance == null)
			instance = new NCLValidator();

		Iterator it = docs.iterator();
		NclValidatorDocument doc = null;
		String fileName = "";
		boolean dtdOk = true;

		while (it.hasNext()) {
			doc = (NclValidatorDocument) it.next();
			if (!DTDValidator.validate(fileName, doc.getDocumentElement())) {
				dtdOk = false;
			}
			else {
				// Test if the root element is one of the rootElements in the NCLStructure
				String rootTagname = doc.getDocumentElement().getTagName(); 
				if(!NCLStructure.getInstance().getRootElements().contains(rootTagname))
				{
					Vector<String> description = new Vector<String>();
					description.add(rootTagname);
					MessageList.addError(doc.getPath(), 2013, null,
							description);
				}
			}
		}

		/*if (!dtdOk){
			return false;
		}*/

		it = docs.iterator();
		boolean docsOk = true;

		while (it.hasNext()) {
			doc = (NclValidatorDocument) it.next();
			Semantics semantic = new Semantics(doc);
			if (!semantic.parse(doc.getDocumentElement())) {
				docsOk = false;
			}
		}

		return docsOk;

	}

	/**
	 * 
	 * @return
	 */
	public static Vector<Message> getErrors() {
		return MessageList.getErrors();
	}

	/**
	 * 
	 * @return
	 */
	public static Vector<Message> getWarnings() {
		return MessageList.getWarnings();
	}
}

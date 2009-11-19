/******************************************************************************
This file is part of the authoring environment in Nested Context Language -
NCL Eclipse.

Copyright: 2007-2008 UFMA/LAWS (Laboratory of Advanced Web Systems), All Rights Reserved.

This program is free software; you can redistribute it and/or modify it under 
the terms of the GNU General Public License version 2 as published by
the Free Software Foundation.

This program is distributed in the hope that it will be useful, but WITHOUT ANY 
WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
PARTICULAR PURPOSE.  See the GNU General Public License version 2 for more 
details.

You should have received a copy of the GNU General Public License version 2
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA

For further information contact:
ncleclipse@laws.deinf.ufma.br
http://www.laws.deinf.ufma.br/ncleclipse
http://www.laws.deinf.ufma.br

 *******************************************************************************/

package br.ufma.deinf.gia.labmint.semantics;

import java.util.Vector;

import org.w3c.dom.Element;

import br.ufma.deinf.gia.labmint.document.NclValidatorDocument;
import br.ufma.deinf.gia.labmint.message.MessageList;

public class Context extends ElementValidation {

	public Context(NclValidatorDocument doc) {
		super(doc);
	}

	private String idContext = null;

	public boolean validate(Element eContext) {
		boolean resultado = true;

		idContext = eContext.getAttribute("id");

		//
		if (!hasValidContextIDAttribute(eContext))
			resultado = false;

		// Verifica se o atributo 'refer' de <context> aponta para um outro
		// elemento <context>.
		if (!hasValidContextReferAttribute(eContext))
			resultado = false;

		return resultado;
	}

	private boolean hasValidContextIDAttribute(Element eContext) {
		// TODO: All!
		return true;
	}

	private boolean hasValidContextReferAttribute(Element eContext) {
		if (eContext.hasAttribute("refer")) {
			// TODO: Element cannot be a parent (grandparent,...) or a children
			// (grandchildren, ...) from the element
			String idRefer = eContext.getAttribute("refer");
			Element element = doc.getElement(idRefer);

			if (element == null) {
				Vector<String> args = new Vector<String>();
				args.add(idRefer);

				MessageList.addError(doc.getId(), 3401, eContext, args);
				return false;
			} else if (element.getTagName().compareTo("context") != 0
					&& element.getTagName() != "body") {
				Vector<String> args = new Vector<String>();
				args.add(idRefer);

				MessageList.addError(doc.getId(), 3401, eContext, args);
				return false;
			} else if (element.hasAttribute("refer")) {
				// cannot refer an element that has a refer too
				Vector<String> args = new Vector<String>();
				args.add(idRefer);
				MessageList.addError(doc.getId(), 4109, eContext, args);
				return false;
			}
		}
		return true;
	}

}

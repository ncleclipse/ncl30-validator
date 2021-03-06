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

package br.ufma.deinf.gia.labmint.schemas;

import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class SchemaErrorChecker extends DefaultHandler {
	public SchemaErrorChecker() {
	}

	public void error(SAXParseException e) {
		//MessageList.addError("", "line: "+e.getLineNumber()+" > Problem: "+e.getMessage(), null);
		System.out.println("line: " + e.getLineNumber() + " > Error Problem: "
				+ e.getMessage());
	}

	public void warning(SAXParseException e) {
		//MessageList.addError("", "line: "+e.getLineNumber()+" > Problem: "+e.getMessage(), null);
		System.out.println("line: " + e.getLineNumber()
				+ " > Warning Problem: " + e.getMessage());
	}

	public void fatalError(SAXParseException e) {
		//MessageList.addError("", "line: "+e.getLineNumber()+" > Problem: "+e.getMessage(), null);
		System.out.println("line: " + e.getLineNumber()
				+ " > Fatal Error Problem: " + e.getMessage());
		System.exit(1);
	}
}

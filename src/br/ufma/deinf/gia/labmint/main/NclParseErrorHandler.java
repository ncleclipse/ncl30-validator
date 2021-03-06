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

package br.ufma.deinf.gia.labmint.main;

import java.util.Vector;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import br.ufma.deinf.gia.labmint.message.MessageList;

public class NclParseErrorHandler implements ErrorHandler {
	String file;

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	@Override
	public void error(SAXParseException arg0) throws SAXException {
		// TODO Setar a linha e a coluna
		String message = arg0.getLineNumber() + ":" + arg0.getColumnNumber()
				+ " " + arg0.getMessage();
		Vector<String> args = new Vector<String>();
		args.add(message);
		MessageList.addError(file, 1002, null, args);
	}

	@Override
	public void fatalError(SAXParseException arg0) throws SAXException {
		// TODO Auto-generated method stub
		String message = arg0.getLineNumber() + ":" + arg0.getColumnNumber()
				+ " " + arg0.getMessage();
		Vector<String> args = new Vector<String>();
		args.add(message);
		MessageList.addError(file, 1002, null, args);
	}

	@Override
	public void warning(SAXParseException arg0) throws SAXException {
		// TODO Auto-generated method stub
		String message = arg0.getLineNumber() + ":" + arg0.getColumnNumber()
				+ " " + arg0.getMessage();
		Vector<String> args = new Vector<String>();
		args.add(message);
		MessageList.addError(file, 1101, null, args);
	}

}

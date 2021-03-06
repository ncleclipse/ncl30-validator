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

package br.ufma.deinf.gia.labmint.message;

import java.util.Vector;

import org.w3c.dom.Element;

public class MessageList {
	public static final int PORTUGUESE = 0;
	public static final int ENGLISH = 1;
	public static final int SPANISH = 2;
	private static Vector<Message> errors = new Vector<Message>();
	private static Vector<Message> warnings = new Vector<Message>();
	private static int atualLanguage = ENGLISH;

	private MessageList() {

	}

	public static void addError(String file, int idTextMsg, Element element) {
		// int row = 0, col = 0;
		String id = "";
		if (element != null && element.hasAttribute("id"))
			id = element.getAttribute("id");
		String text = MessageHandler.getMessage(idTextMsg);
		Message msg = new Message(Message.ERROR_MESSAGE, text, file, null,
				element, id, idTextMsg);
		// new ReportMessage()
		// Message msg = new Message(file, text, element, row, col);
		errors.add(msg);
	}

	public static void addError(String file, int idTextMsg, Element element,
			Vector<String> args) {
		String id = "";
		if (element != null && element.hasAttribute("id"))
			id = element.getAttribute("id");
		String text = MessageHandler.getMessage(idTextMsg);

		for (int i = 0; i < args.size(); i++) {
			text = text.replaceFirst("%s", args.get(i));
		}

		Message msg = new Message(Message.ERROR_MESSAGE, text, file, null,
				element, id, idTextMsg);

		errors.add(msg);
	}

	public static void addError(String file, String text, Element element) {
		// int row = 0, col = 0;
		String id = "";
		if (element != null && element.hasAttribute("id"))
			id = element.getAttribute("id");
		Message msg = new Message(Message.ERROR_MESSAGE, text, file, null,
				element, id, -1);
		// new ReportMessage()
		// Message msg = new Message(file, text, element, row, col);
		errors.add(msg);
	}

	public static void addError(String file, int idTextMsg, Element element,
			int language) {
		if (atualLanguage == language) {
			addError(file, idTextMsg, element);
		}
	}

	public static void addError(String file, String text, Element element,
			int language) {
		if (atualLanguage == language) {
			addError(file, text, element);
		}
	}

	public static void addWarning(String file, int idTextMsg, Element element) {
		// int row = 0, col = 0;
		String id = "";
		if (element != null && element.hasAttribute("id"))
			id = element.getAttribute("id");
		String text = MessageHandler.getMessage(idTextMsg);
		Message msg = new Message(Message.WARNING_MESSAGE, text, file, null,
				element, id, idTextMsg);
		// Message msg = new Message(file, text, element, row, col);
		warnings.add(msg);
	}

	public static void addWarning(String file, String text, Element element) {
		// int row = 0, col = 0;
		String id = "";
		if (element != null && element.hasAttribute("id"))
			id = element.getAttribute("id");
		Message msg = new Message(Message.WARNING_MESSAGE, text, file, null,
				element, id, -1);
		// Message msg = new Message(file, text, element, row, col);
		warnings.add(msg);
	}

	public static void addWarning(String file, int idTextMsg, Element element,
			int language) {
		if (atualLanguage == language) {
			addWarning(file, idTextMsg, element);
		}
	}

	public static void addWarning(String file, String text, Element element,
			int language) {
		if (atualLanguage == language) {
			addWarning(file, text, element);
		}
	}

	public static void addWarning(String file, int idTextMsg, Element element,
			Vector<String> args) {
		String id = "";
		if (element != null && element.hasAttribute("id"))
			id = element.getAttribute("id");
		String text = MessageHandler.getMessage(idTextMsg);

		for (int i = 0; i < args.size(); i++)
			text = text.replaceFirst("%s", args.get(i));

		Message msg = new Message(Message.ERROR_MESSAGE, text, file, null,
				element, id, idTextMsg);

		warnings.add(msg);
	}

	public static void setLanguage(int language) {
		atualLanguage = language;
	}

	public static int getErrorSize() {
		return (int) errors.size();
	}

	public static int getWarningSize() {
		return (int) warnings.size();
	}

	public static Vector<Message> getErrors() {
		return errors;
	}

	public static Vector<Message> getWarnings() {
		return warnings;
	}

	public static void clear() {
		errors.clear();
		warnings.clear();
	}
}

/******************************************************************************
Este arquivo é parte da implementação do ambiente de autoria em Nested Context
Language - NCL Eclipse.

Direitos Autorais Reservados (c) 2007-2008 UFMA/LAWS (Laboratório de Sistemas Avançados da Web) 

Este programa é software livre; você pode redistribuí-lo e/ou modificá-lo sob 
os termos da Licença Pública Geral GNU versão 2 conforme publicada pela Free 
Software Foundation.

Este programa é distribuído na expectativa de que seja útil, porém, SEM 
NENHUMA GARANTIA; nem mesmo a garantia implícita de COMERCIABILIDADE OU 
ADEQUAÇÃO A UMA FINALIDADE ESPECÍFICA. Consulte a Licença Pública Geral do 
GNU versão 2 para mais detalhes. 

Você deve ter recebido uma cópia da Licença Pública Geral do GNU versão 2 junto 
com este programa; se não, escreva para a Free Software Foundation, Inc., no 
endereço 59 Temple Street, Suite 330, Boston, MA 02111-1307 USA. 

Para maiores informações:
ncleclipse@laws.deinf.ufma.br
http://www.laws.deinf.ufma.br/ncleclipse
http://www.laws.deinf.ufma.br

 ******************************************************************************
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

package br.ufma.deinf.gia.labmint.message;

import java.util.Vector;

import org.w3c.dom.Element;

public class MessageList {
	public static final int PORTUGUESE = 0;
	public static final int ENGLISH = 1;

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
				element, id);
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

		for (int i = 0; i < args.size(); i++){
			text = text.replaceFirst("%s", args.get(i));
		}

		Message msg = new Message(Message.ERROR_MESSAGE, text, file, null,
				element, id);

		errors.add(msg);
	}

	public static void addError(String file, String text, Element element) {
		// int row = 0, col = 0;
		String id = "";
		if (element != null && element.hasAttribute("id"))
			id = element.getAttribute("id");
		Message msg = new Message(Message.ERROR_MESSAGE, text, file, null,
				element, id);
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
				element, id);
		// Message msg = new Message(file, text, element, row, col);
		warnings.add(msg);
	}

	public static void addWarning(String file, String text, Element element) {
		// int row = 0, col = 0;
		String id = "";
		if (element != null && element.hasAttribute("id"))
			id = element.getAttribute("id");
		Message msg = new Message(Message.WARNING_MESSAGE, text, file, null,
				element, id);
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
				element, id);

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

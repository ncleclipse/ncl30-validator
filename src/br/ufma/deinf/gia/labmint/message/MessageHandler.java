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

import java.util.Properties;

/**
 * 
 * @author <a href="mailto:robertogerson@telemidia.puc-rio.br">Roberto Gerson
 *         Azevedo</a>
 * 
 */
public class MessageHandler {
	private static MessageHandler instance = null;
	private static Properties propertyMessage = null;

	private MessageHandler() {
		propertyMessage = new NCLValidatorErrorMessages();
	}

	public static void getInstance() {
		if (instance == null)
			instance = new MessageHandler();
	}

	private static String getMessageTemplate(int msg) {
		getInstance();
		return propertyMessage.getProperty((new Integer(msg)).toString());
	}

	public static String getMessage(int msg) {
		String str = MessageHandler.getMessageTemplate(msg);
		return str;
	}

	public static void setPropertyMessage(Properties prop) {
		propertyMessage = prop;
	}
}

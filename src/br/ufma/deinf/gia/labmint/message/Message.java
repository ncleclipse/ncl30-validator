/*******************************************************************************
 * Este arquivo é parte da implementação do ambiente de autoria em Nested 
 * Context Language - NCL Eclipse.
 * Direitos Autorais Reservados (c) 2007-2010 UFMA/LAWS (Laboratório de Sistemas 
 * Avançados da Web)
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou modificá-lo sob
 * os termos da Licença Pública Geral GNU versão 2 conforme publicada pela Free 
 * Software Foundation.
 *
 * Este programa é distribuído na expectativa de que seja útil, porém, SEM 
 * NENHUMA GARANTIA; nem mesmo a garantia implícita de COMERCIABILIDADE OU
 * ADEQUAÇÃO A UMA FINALIDADE ESPECÍFICA. Consulte a Licença Pública Geral do
 * GNU versão 2 para mais detalhes. Você deve ter recebido uma cópia da Licença
 * Pública Geral do GNU versão 2 junto com este programa; se não, escreva para a
 * Free Software Foundation, Inc., no endereço 59 Temple Street, Suite 330,
 * Boston, MA 02111-1307 USA.
 *
 * Para maiores informações:
 * - ncleclipse@laws.deinf.ufma.br
 * - http://www.laws.deinf.ufma.br/ncleclipse
 * - http://www.laws.deinf.ufma.br
 *
 *******************************************************************************
 * This file is part of the authoring environment in Nested Context Language -
 * NCL Eclipse.
 * Copyright: 2007-2010 UFMA/LAWS (Laboratory of Advanced Web Systems), All
 * Rights Reserved.
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

import java.io.Serializable;

import org.w3c.dom.Element;

public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int ERROR_MESSAGE = 0;
	public static final int WARNING_MESSAGE = 1;
	public static final int INFO_MESSAGE = 2;

	private int type;
	private String description;
	private String resource;
	private String position;
	private Element element;
	private String id;
	private int idMsg;
	int line;
	int column;

	public Message(int type, String description, String resource,
			String position, Element element, String id, int idMsg) {
		this.type = type;
		this.description = description;
		this.resource = resource;
		this.position = position;
		this.element = element;
		this.id = id;
		this.setMsgID(idMsg);
	}

	public Message(int type, String description, String resource, int line,
			int col, Element element, String id, int idMsg) {
		this.type = type;
		this.description = description;
		this.resource = resource;
		this.element = element;
		this.id = id;
		this.line = line;
		this.column = col;
		this.setMsgID(idMsg);
	}

	/**
	 * Gets the description of the message.
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the message description.
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get the message location. In other words, the line in the resource file. 
	 * @return
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * Set the message location (the line in the resource file).
	 * @param location
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * Get the message type.
	 * @return
	 */
	public int getType() {
		return type;
	}

	/**
	 * Set the message type.
	 * @param type
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * Return the file name where the message was originated.
	 * @return
	 */
	public String getResource() {
		return resource;
	}

	/**
	 * Sets where the message was originated (for example, the file name).
	 * @param resource
	 */
	public void setResource(String resource) {
		this.resource = resource;
	}

	/**
	 * Gets the element with problem.
	 * @return
	 */
	public Element getElement() {
		return element;
	}

	/**
	 * Sets the element with problem.
	 * @param element
	 */
	public void setElement(Element element) {
		this.element = element;
	}

	/**
	 * Gets the id of the element with problem.
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id of the element with problem.
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	public void setMsgID(int idMsg) {
		this.idMsg = idMsg;
	}

	public int getMsgID() {
		return idMsg;
	}
}

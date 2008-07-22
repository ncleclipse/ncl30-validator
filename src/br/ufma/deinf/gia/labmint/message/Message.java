/******************************************************************************
Este arquivo � parte da implementa��o ferramenta de autoria Composer para 
documentos NCL.

Direitos Autorais Reservados (c) 1989-2007 PUC-Rio/Laborat�rio TeleM�dia

Este programa � software livre; voc� pode redistribu�-lo e/ou modific�-lo sob 
os termos da Licen�a P�blica Geral GNU vers�o 2 conforme publicada pela Free 
Software Foundation.

Este programa � distribu�do na expectativa de que seja �til, por�m, SEM 
NENHUMA GARANTIA; nem mesmo a garantia impl�cita de COMERCIABILIDADE OU 
ADEQUA��O A UMA FINALIDADE ESPEC�FICA. Consulte a Licen�a P�blica Geral do 
GNU vers�o 2 para mais detalhes. 

Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral do GNU vers�o 2 junto 
com este programa; se n�o, escreva para a Free Software Foundation, Inc., no 
endere�o 59 Temple Street, Suite 330, Boston, MA 02111-1307 USA. 

Para maiores informa��es:
ncl @ telemidia.puc-rio.br
http://www.ncl.org.br
http://www.ginga.org.br
http://www.telemidia.puc-rio.br
******************************************************************************
This file is part of the Composer authoring tool implementation for NCL documents.

Copyright: 1989-2007 PUC-RIO/LABORATORIO TELEMIDIA, All Rights Reserved.

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
ncl @ telemidia.puc-rio.br
http://www.ncl.org.br
http://www.ginga.org.br
http://www.telemidia.puc-rio.br
*******************************************************************************/

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
	int line;
	int column;
	
	public Message(int type, String description, String resource, 
      String position, Element element, String id){
		this.type = type;
		this.description = description;
		this.resource = resource;
		this.position = position;
		this.element = element;
		this.id = id;
	}
	
	public Message(int type, String description, String resource, 
	  int line, int col, Element element, String id){
		this.type = type;
		this.description = description;
		this.resource = resource;
		this.element = element;
		this.id = id;
		this.line = line;
		this.column = col;
	}

	/**
	 * Gets the description of the message.
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the message description.
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the message location. In other words, the line in the resource file. 
	 * @return
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * Sets the message location (the line in the resource file).
	 * @param location
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	
	/**
	 * Gets the message type.
	 * @return
	 */
	public int getType() {
		return type;
	}

	/**
	 * Sets the message type.
	 * @param type
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * Returns the file name where the message was originated.
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
}

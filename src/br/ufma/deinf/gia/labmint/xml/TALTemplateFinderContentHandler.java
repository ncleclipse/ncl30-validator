/*******************************************************************************
 * This file is part of the authoring environment in Nested Context Language -
 * NCL Eclipse.
 * 
 * Copyright: 2007-2009 UFMA/LAWS (Laboratory of Advanced Web Systems), All Rights Reserved.
 * 
 * This program is free software; you can redistribute it and/or modify it under 
 * the terms of the GNU General Public License version 2 as published by
 * the Free Software Foundation.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE.  See the GNU General Public License version 2 for more 
 * details.
 * 
 * You should have received a copy of the GNU General Public License version 2
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 * For further information contact:
 * 		ncleclipse@laws.deinf.ufma.br
 * 		http://www.laws.deinf.ufma.br/ncleclipse
 * 		http://www.laws.deinf.ufma.br
 ********************************************************************************/

package br.ufma.deinf.gia.labmint.xml;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;


/**
 * @author Rodrigo Costa <rodrim.c@laws.deinf.ufma.br>
 * 
 */
public class TALTemplateFinderContentHandler extends DefaultHandler {
	private String _templateId;
	private boolean _isValid; 
	
	public TALTemplateFinderContentHandler (String id){
		_templateId = id;
		_isValid = false;
	}
	
	public void startElement(String uri, String name, String qName, Attributes atts) {
		if (qName.equals("tal:template")){
			String id = atts.getValue("id");
			if (id != null && id.equals(_templateId))
				_isValid = true;
		}
	}
	
	public boolean isValid() {
		return _isValid;
	}
	
}




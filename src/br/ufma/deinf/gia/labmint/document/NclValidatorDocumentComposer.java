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

package br.ufma.deinf.gia.labmint.document;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class NclValidatorDocumentComposer extends NclValidatorDocument {

	public NclValidatorDocumentComposer(Document doc)
			throws ParserConfigurationException, URISyntaxException,
			SAXException, IOException {
		super(null, doc);
	}

	protected void parse(Element root) {
		if (root.hasAttribute("id")) {
			String id = root.getAttribute("id");
			elements.put(id, root);
		}

		/*Codigo retirado para funcionar com o composer
		 if(root.getTagName().equals("importBase")){
			if(root.hasAttribute("alias")){
				if(root.hasAttribute("documentURI")){
					DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		    		DocumentBuilder db = dbf.newDocumentBuilder();
					Document doc = null;
					URI uri = new URI(root.getAttribute("documentURI"));
		//					Se documentUri é absoluto					
					if(uri.isAbsolute()) {
						doc = db.parse(new File(uri));
					}
					else {
						doc = db.parse(DocumentUtil.getAbsoluteFileName(this.getPath(), root.getAttribute("documentURI")));
					}
		    		if(!this.addDocument(root.getAttribute("alias"), new NclValidatorDocument(doc)))
		    			MessageList.addError(this.path, "Two Element with same alias <"+root.getAttribute("alias")+"> ", root);
				}
				else
					MessageList.addError(this.getPath(), "Element <importBase> must to have a <documentUri> Attribute.", root);
			}
			else
				MessageList.addError(this.getPath(), "Element <importBase> must to have a <alias> Attribute.", root);
		}
		 */
		NodeList nodeList = root.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				parse((Element) node);
			}
		}
	}
}

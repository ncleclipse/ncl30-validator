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

package br.ufma.deinf.gia.labmint.xml;

import java.io.IOException;
import java.io.StringReader;

import org.apache.xerces.parsers.DOMParser;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XNIException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLParserExtend extends DOMParser {
	private XMLLocator locator;

	public XMLParserExtend() {
		// fNodeExpansion = FULL; // faster than:
		// this.setFeature("http://apache.org/xml/features/defer-node-expansion"
		// , false);
		try {
			this.setFeature(
					"http://apache.org/xml/features/dom/defer-node-expansion",
					false);
			this.setFeature("http://xml.org/sax/features/namespaces", false);
		} catch (org.xml.sax.SAXException e) {
			System.err.println("except" + e);
		}
	} // constructor

	public void startDocument(XMLLocator locator, String encoding,
			NamespaceContext namespaceContext, Augmentations augs)
			throws XNIException {
		super.startDocument(locator, encoding, namespaceContext, augs);
		this.locator = locator;
		Node node = null;
		try {
			node = (Node) this
					.getProperty("http://apache.org/xml/properties/dom/current-element-node");
		} catch (org.xml.sax.SAXException ex) {
			System.err.println("except" + ex);
		}

		if (node != null)
			node.setUserData("startLine", String.valueOf(locator
					.getLineNumber()), null); // Save location String into node
	} // startDocument

	public void startElement(QName elementQName, XMLAttributes attrList,
			Augmentations augs) throws XNIException {

		super.startElement(elementQName, attrList, augs);

		Node node = null;
		try {
			node = (Node) this
					.getProperty("http://apache.org/xml/properties/dom/current-element-node");
			// System.out.println( "The node = " + node ); TODO JEFF
		} catch (org.xml.sax.SAXException ex) {
			System.err.println("except" + ex);
			;
		}
		if (node != null) {
			node.setUserData("startLine", String.valueOf(locator
					.getLineNumber()), null); // Save location String into node
			node.setUserData("startColumn", String.valueOf(locator
					.getColumnNumber()), null);
			node.setUserData("startCharacterOffset", String.valueOf(locator
					.getCharacterOffset()), null);
			node.setUserData("baseSystemId", locator.getBaseSystemId(), null);
		}
	} // startElement

	public void parseString(String xmlText) throws SAXException, IOException {
		InputSource inputSource = new InputSource(new StringReader(xmlText));
		parse(inputSource);
	}

	public static void main(String[] args) {
		XMLParserExtend myParser = new XMLParserExtend();
		try {
			myParser.parse("testes/teste.ncl");
			Document doc = myParser.getDocument();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

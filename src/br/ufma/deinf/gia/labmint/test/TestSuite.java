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
package br.ufma.deinf.gia.labmint.test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import br.ufma.deinf.gia.labmint.composer.NCLValidator;
import br.ufma.deinf.gia.labmint.document.NclValidatorDocument;
import br.ufma.deinf.gia.labmint.main.NclParseErrorHandler;
import br.ufma.deinf.gia.labmint.message.Message;
import br.ufma.deinf.gia.labmint.message.MessageList;
import br.ufma.deinf.gia.labmint.xml.XMLParserExtend;

public class TestSuite {
	String out = "tests/out";
	static String input = "tests/";
	static Vector<File> files = new Vector<File>();

	public static void buildFileTree(File f) {
		if (!f.exists())
			return;
		if (f.isDirectory()) {
			File[] childs = f.listFiles();
			for (int i = 0; i < childs.length; i++) {
				buildFileTree(childs[i]);
			}
		} else {
			files.add(f);
		}
	}

	public static void runTests() {
		System.out.println("### Running NCL Validator test suite..");
		System.out.println("### Total files = " + files.size());
		for (int t = 0; t < files.size(); t++) {
			File docFile = files.get(t);
			System.out.println("\n\n");
			System.out.println("######################################");
			System.out.println("# Test " + (t + 1) + " from " + files.size());
			System.out.println("# File = " + docFile.getAbsolutePath());
			System.out.println("######################################");

			Document doc = null;
			MessageList.clear();
			MessageList.setLanguage(MessageList.ENGLISH);
			try {
				XMLParserExtend parser = new XMLParserExtend();

				NclParseErrorHandler p = new NclParseErrorHandler();
				p.setFile(docFile.getAbsolutePath());
				parser.setErrorHandler(p);
				parser.parse(docFile.getPath());
				doc = parser.getDocument();

				Vector<NclValidatorDocument> documents = new Vector<NclValidatorDocument>();
				// NclDocumentManager.resetDocumentManager();
				NclValidatorDocument nclValidatorDocument = new NclValidatorDocument(
						null, doc);
				documents.add(nclValidatorDocument);
				NCLValidator.validate(documents);

			} catch (IOException e) {
				Vector<String> description = new Vector<String>();
				description.add(e.getMessage());
				MessageList.addError(docFile.getAbsolutePath(), 1001, null,
						description);
			} catch (SAXException e) {
				Vector<String> description = new Vector<String>();
				description.add(e.getMessage());
				MessageList.addError(docFile.getAbsolutePath(), 1001, null,
						description);
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Vector<Message> warnings = NCLValidator.getWarnings();
			Vector<Message> erros = NCLValidator.getErrors();
			// Imprime os warning
			System.out.println("### Warnings ###");
			for (int i = 0; i < warnings.size(); i++) {
				if (warnings.get(i).getElement() != null)
					System.out.print("Element:'"
							+ warnings.get(i).getElement().getTagName()
							+ "' id:'" + warnings.get(i).getId() + "'");
				System.out.println(" -> " + warnings.get(i).getDescription());
			}
			System.out.println("\n\n");
			// Imprime os erros
			System.out.println("### Erros ###");
			for (int i = 0; i < erros.size(); i++) {
				if (erros.get(i).getElement() != null) {
					System.out.println("Element:'"
							+ erros.get(i).getElement().getTagName() + "' id:'"
							+ erros.get(i).getId() + "'");
					// SimpleLocator loc =
					// handler.element2Locator(erros.get(i).getElement());
					System.out.println(erros.get(i).getElement().getUserData(
							"baseSystemId"));
					System.out.println("line : "
							+ erros.get(i).getElement()
									.getUserData("startLine")
							+ " Column: "
							+ erros.get(i).getElement().getUserData(
									"startColumn"));
				}
				System.out.println(" -> " + erros.get(i).getDescription());
			}
		}
	}

	public static void main(String[] args) {
		File f = new File(input);

		buildFileTree(f);

		runTests();
	}
}

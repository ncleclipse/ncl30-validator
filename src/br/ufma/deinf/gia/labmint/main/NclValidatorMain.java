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

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import br.ufma.deinf.gia.labmint.composer.NCLValidator;
import br.ufma.deinf.gia.labmint.document.NclValidatorDocument;
import br.ufma.deinf.gia.labmint.main.NclParseErrorHandler;
import br.ufma.deinf.gia.labmint.message.Message;
import br.ufma.deinf.gia.labmint.message.MessageList;
import br.ufma.deinf.gia.labmint.message.NCLValidatorErrorMessages;
import br.ufma.deinf.gia.labmint.xml.XMLParserExtend;

public class NclValidatorMain {
	static Vector<File> files = new Vector<File>();
	static int language;

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

	public static void printHelp() {
		System.out.println(NCLValidatorErrorMessages.getString("0"));
	}

	public static void run() {
		for (int t = 0; t < files.size(); t++) {
			File docFile = files.get(t);
			System.out.println(docFile.getAbsolutePath());

			Document doc = null;
			MessageList.clear();
			// MessageList.setLanguage(language);

			try {
				XMLParserExtend parser = new XMLParserExtend();

				NclParseErrorHandler p = new NclParseErrorHandler();
				p.setFile(docFile.getAbsolutePath());
				parser.setErrorHandler(p);
				parser.parse(docFile.getPath());
				doc = parser.getDocument();

				Vector<NclValidatorDocument> documents = new Vector<NclValidatorDocument>();

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

				e.printStackTrace();
			} catch (URISyntaxException e) {

				e.printStackTrace();
			}

			Vector<Message> warnings = NCLValidator.getWarnings();
			Vector<Message> erros = NCLValidator.getErrors();

			System.out.println(NCLValidatorErrorMessages.getString("1"));
			for (int i = 0; i < warnings.size(); i++) {
				if (warnings.get(i).getElement() != null)
					System.out.print(NCLValidatorErrorMessages.getString("5")
							+ warnings.get(i).getElement().getTagName()
							+ "' id:'" + warnings.get(i).getId() + "'");
				System.out.println(" -> " + warnings.get(i).getDescription());
			}
			System.out.println("\n\n");
			// Imprime os erros
			System.out.println("### "
					+ NCLValidatorErrorMessages.getString("2") + " ###");
			for (int i = 0; i < erros.size(); i++) {
				if (erros.get(i).getElement() != null) {
					System.out.println(NCLValidatorErrorMessages.getString("5")
							+ erros.get(i).getElement().getTagName() + "' id:'"
							+ erros.get(i).getId() + "'");
					// SimpleLocator loc =
					// handler.element2Locator(erros.get(i).getElement());
					// System.out.println(erros.get(i).getElement().getUserData(
					// "baseSystemId"));
					System.out.println(NCLValidatorErrorMessages.getString("3")
							+ erros.get(i).getElement()
									.getUserData("startLine")
							+ " "
							+ NCLValidatorErrorMessages.getString("4")
							+ erros.get(i).getElement().getUserData(
									"startColumn"));
				}
				System.out.println(" -> " + erros.get(i).getDescription());
			}

		}
	}

	public static void main(String[] args) {

		if (args.length < 1) {
			printHelp();
		} else if (args.length == 1) {
			if (args[0].equals("--help") || args[0].equals("-h")) {
				printHelp();
				return;
			}
			Locale.setDefault(Locale.getDefault());
			File f = new File(args[0]);

			if (f.isFile() || f.isDirectory()) {
				buildFileTree(f);
				run();
			} else {
				printHelp();
			}
		} else if (args.length == 3) {
			if (args[0].equals("-nl")) {
				String nl = args[1];
				String language = args[1].substring(0, 2);
				String country = args[1].substring(3, 5);
				Locale.setDefault(new Locale(language, country));
			}

			File f = new File(args[2]);
			if (f.isFile() || f.isDirectory()) {
				buildFileTree(f);
				run();
			} else {
				printHelp();
			}
		} else
			printHelp();

	}
}

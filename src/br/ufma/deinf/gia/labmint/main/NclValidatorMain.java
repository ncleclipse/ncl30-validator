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

	public static void printMain() {
		System.out.println("Usage: ncl30-validator.jar [-nl][language]{file} ");
		System.out.println("'-nl' - Select National Language ");
		System.out.println("'-language' - Select one of the available languages:pt_BR(portuguese),es_ES(spanish),en_US(english)" );
		
		System.out.println("'file' input file or directory");
		System.out
				.println("Example 1: Validating a single NCL document with english messages: validator.jar -nl en_US filename");
		System.out
				.println("Example 2: Validating a single NCL document with portuguese messages: validator.jar -nl pt_BR filename");
		System.out
				.println("Example 3: Validating a single NCL document with spanish messages: validator.jar -es_ES filename");

	}

	public static void run() {
		for (int t = 0; t < files.size(); t++) {
			File docFile = files.get(t);
			System.out.println(docFile.getAbsolutePath());

			Document doc = null;
			MessageList.clear();
			MessageList.setLanguage(language);

			try {
				XMLParserExtend parser = new XMLParserExtend();

				NclParseErrorHandler p = new NclParseErrorHandler();
				p.setFile(docFile.getAbsolutePath());
				parser.setErrorHandler(p);
				parser.parse(docFile.getPath());
				doc = parser.getDocument();

				Vector<NclValidatorDocument> documents = new Vector<NclValidatorDocument>();

				NclValidatorDocument nclValidatorDocument = new NclValidatorDocument(
						doc);
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
			System.out.println("### " + NCLValidatorErrorMessages.getString("2") + " ###");
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
			
			printMain();
		}
		if (args.length == 1) {
			
			Locale.setDefault(Locale.getDefault());
			File f = new File(args[0]);

			if (f.isFile() || f.isDirectory()) {
				buildFileTree(f);
				run();
			} else {
				printMain();
			}
		} else if (args.length > 2) {
			if(args[0].equals("-nl")){
				if(args[1].equals("pt_BR")){
					language = MessageList.PORTUGUESE;
					Locale.setDefault(new Locale("pt", "BR"));
				}
				if(args[1].equals("en_US")){
					language = MessageList.ENGLISH;
					Locale.setDefault(Locale.ENGLISH);
				}
				if(args[1].equals("es_ES")){
					language = MessageList.SPANISH;
					Locale.setDefault(new Locale("es", "ES"));
				}
			}
			
			
			

			File f = new File(args[1]);

			if (f.isFile() || f.isDirectory()) {
				buildFileTree(f);
				run();
			} else {
				printMain();
			}
		}

	}

}
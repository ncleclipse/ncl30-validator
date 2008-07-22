package br.ufma.deinf.gia.labmint.main;

import java.io.File;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.DOMLocator;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import br.ufma.deinf.gia.labmint.composer.NCLValidator;
import br.ufma.deinf.gia.labmint.document.NclValidatorDocument;
import br.ufma.deinf.gia.labmint.message.Message;
import br.ufma.deinf.gia.labmint.message.MessageList;
import br.ufma.deinf.gia.labmint.xml.XMLParserExtend;

public class NclValidatorMain {
	
	public static void main(String[] args) {
		//File docFile = new File("../../workspace/composer-java/files/connectors/composerConnectorBase.conn");
		//File docFile = new File("testes/causalconnector/simple_action_qualifier_erro.conn");
		File docFile = new File("testes/bind/bind_component_body_erro.ncl");
		//File docFile = new File("testes/head/dois_regionBase_ok.ncl");
		//File docFile = new File("testes/multilingueTest.ncl");
		
		Document doc = null;
		MessageList.clear();
		MessageList.setLanguage(MessageList.PORTUGUESE);
        try {
        		XMLParserExtend parser = new XMLParserExtend();
        		
        		NclParseErrorHandler p = new NclParseErrorHandler();
        		p.setFile(docFile.getAbsolutePath());
        		parser.setErrorHandler(p);
        		parser.parse(docFile.getPath());
        		doc = parser.getDocument();
        		
        		Vector <NclValidatorDocument> documents = new Vector<NclValidatorDocument>();
        		//NclDocumentManager.resetDocumentManager();
        		NclValidatorDocument nclValidatorDocument = new NclValidatorDocument(doc);
        		documents.add(nclValidatorDocument);
        		NCLValidator.validate(documents);
       		        		
        } 
        catch (Exception e) {
        	//TODO Alguma coisa
        	MessageList.addError(docFile.getAbsolutePath(), e.getMessage(), null, MessageList.ENGLISH);
        	MessageList.addError(docFile.getAbsolutePath(), "Problemas ao tentar fazer o parse do documento", null, MessageList.PORTUGUESE);
        }
        
		Vector <Message> warnings = NCLValidator.getWarnings();
		Vector <Message> erros = NCLValidator.getErrors();	
		//Imprime os warning        		
		System.out.println("### Warnings ###");
		for(int i = 0; i < warnings.size(); i++){
				if(warnings.get(i).getElement() != null)
					System.out.print("Element:'"+warnings.get(i).getElement().getTagName()+"' id:'"+warnings.get(i).getId()+"'");
				System.out.println(" -> "+warnings.get(i).getDescription());
		}
		System.out.println("\n\n");
		//Imprime os erros
		System.out.println("### Erros ###");        		
		for(int i = 0; i < erros.size(); i++){
				if(erros.get(i).getElement() != null){
					System.out.println("Element:'"+erros.get(i).getElement().getTagName()+"' id:'"+erros.get(i).getId()+"'");
					//SimpleLocator loc = handler.element2Locator(erros.get(i).getElement());
					System.out.println(erros.get(i).getElement().getUserData("baseSystemId"));
					System.out.println("line : " + erros.get(i).getElement().getUserData("startLine") + " Column: " + erros.get(i).getElement().getUserData("startColumn"));
				}
				System.out.println(" -> "+erros.get(i).getDescription());
		}        
          
	}
}

package br.ufma.deinf.gia.labmint.main;

import java.io.File;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import br.ufma.deinf.gia.labmint.composer.NCLValidator;
import br.ufma.deinf.gia.labmint.document.NclValidatorDocument;
import br.ufma.deinf.gia.labmint.message.MessageList;
import br.ufma.deinf.gia.labmint.message.Message;

import org.w3c.dom.Document;

public class NclValidatorMain {
	public static void main(String[] args) {
		//File docFile = new File("../../workspace/composer-java/files/connectors/composerConnectorBase.conn");
		//File docFile = new File("testes/causalconnector/simple_action_qualifier_erro.conn");
		File docFile = new File("testes/bind/bind_component_body_erro.ncl");
		//File docFile = new File("testes/head/dois_regionBase_ok.ncl");
		
		Document doc = null;
		MessageList.clear();
        try {
        		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        		DocumentBuilder db = dbf.newDocumentBuilder();
        		NclParseErrorHandler p = new NclParseErrorHandler();
        		p.setFile(docFile.getAbsolutePath());
        		db.setErrorHandler(p);
        		doc = db.parse(docFile);
        		
        		Vector <NclValidatorDocument> documents = new Vector<NclValidatorDocument>();
        		//NclDocumentManager.resetDocumentManager();
        		NclValidatorDocument nclValidatorDocument = new NclValidatorDocument(doc);
        		documents.add(nclValidatorDocument);
        		       		
        		NCLValidator.validate(documents);
       		        		
        } 
        catch (Exception e) {
        	//TODO Alguma coisa
        	e.printStackTrace();
        	MessageList.addError(docFile.getAbsolutePath(), e.getMessage(), null);
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
				if(erros.get(i).getElement() != null)			
					System.out.println("Element:'"+erros.get(i).getElement().getTagName()+"' id:'"+erros.get(i).getId()+"'");
				System.out.println(" -> "+erros.get(i).getDescription());
		}        
          
	}
}

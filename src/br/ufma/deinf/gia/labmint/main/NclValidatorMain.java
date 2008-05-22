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
		if(args.length == 0) {
			System.out.println("use: 'java -jar ncl30-validator filename language");
			return;
		}
		File docFile = new File(args[0]);
	
		Document doc = null;
		MessageList.clear();
		if(args.length == 2){
			if(args[1].equals("pt") || args[1].equals("portugues") || args[1].equals("portuguese"))
				MessageList.setLanguage(MessageList.PORTUGUESE);
			else MessageList.setLanguage(MessageList.ENGLISH);
		}
		else MessageList.setLanguage(MessageList.ENGLISH);
        try {
        		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        		DocumentBuilder db = dbf.newDocumentBuilder();
        		NclParseErrorHandler p = new NclParseErrorHandler();
        		p.setFile(docFile.getAbsolutePath());
        		db.setErrorHandler(p);
        		doc = db.parse(docFile);
        		
        		Vector <NclValidatorDocument> documents = new Vector<NclValidatorDocument>();
        		NclValidatorDocument nclValidatorDocument = new NclValidatorDocument(doc);
        		documents.add(nclValidatorDocument);
        		NCLValidator.validate(documents);
       		        		
        } 
        catch (Exception e) {
        	//TODO Alguma coisa
        	MessageList.addError(docFile.getAbsolutePath(), e.getMessage(), null, MessageList.ENGLISH);
        	MessageList.addError(docFile.getAbsolutePath(), "Problemas ao tentar fazer o parse do documento ('"+e.getMessage()+"')", null, MessageList.PORTUGUESE);
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
		System.out.println("### Errors ###");        		
		for(int i = 0; i < erros.size(); i++){
				if(erros.get(i).getElement() != null)			
					System.out.println("Element:'"+erros.get(i).getElement().getTagName()+"' id:'"+erros.get(i).getId()+"'");
				System.out.println(" -> "+erros.get(i).getDescription());
		}        
          
	}
}

import java.io.File;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import br.ufma.deinf.gia.labmint.composer.NCLValidator;
import br.ufma.deinf.gia.labmint.document.NclValidatorDocument;
import br.ufma.deinf.gia.labmint.main.NclParseErrorHandler;
import br.ufma.deinf.gia.labmint.message.Message;
import br.ufma.deinf.gia.labmint.message.MessageList;


public class ncl30validator {
		public static void main(String[] args) {
			File docFile = new File(args[0]);
			
			Document doc = null;
			MessageList.clear();
			if(args.length > 1){
				int language = 0;
				if(args[1].equals("pt") || args[1].equals("portugues") || args[1].equals("portuguese"))
					language = MessageList.PORTUGUESE;
				else if(args[1].equals("en") || args[1].equals("english"))
					language = MessageList.ENGLISH;
				MessageList.setLanguage(language);
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
					if(erros.get(i).getElement() != null)			
						System.out.println("Element:'"+erros.get(i).getElement().getTagName()+"' id:'"+erros.get(i).getId()+"'");
					System.out.println(" -> "+erros.get(i).getDescription());
			}        
	          
		}
}

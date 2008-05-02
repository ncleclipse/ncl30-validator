package br.ufma.deinf.gia.labmint.schemas;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXParseException;
import br.ufma.deinf.gia.labmint.message.*;

public class SchemaErrorChecker extends DefaultHandler
{
   public SchemaErrorChecker() {
   }
   
   public void error (SAXParseException e) {
	   //MessageList.addError("", "line: "+e.getLineNumber()+" > Problem: "+e.getMessage(), null);
	   System.out.println("line: "+e.getLineNumber()+" > Error Problem: "+e.getMessage());
   }
   
   public void warning (SAXParseException e) {
	   //MessageList.addError("", "line: "+e.getLineNumber()+" > Problem: "+e.getMessage(), null);
	   System.out.println("line: "+e.getLineNumber()+" > Warning Problem: "+e.getMessage());
   }
   
   public void fatalError (SAXParseException e) {
	   //MessageList.addError("", "line: "+e.getLineNumber()+" > Problem: "+e.getMessage(), null);
	   System.out.println("line: "+e.getLineNumber()+" > Fatal Error Problem: "+e.getMessage());	   
	   System.exit(1);
   }
}

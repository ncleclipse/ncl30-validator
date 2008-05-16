package br.ufma.deinf.gia.labmint.main;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import br.ufma.deinf.gia.labmint.message.MessageList;

public class NclParseErrorHandler implements ErrorHandler{
	String file;
	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	@Override
	public void error(SAXParseException arg0) throws SAXException {
		// TODO Setar a linha e a coluna
		String message = arg0.getLineNumber()+":"+arg0.getColumnNumber()+" "+ arg0.getMessage();
		MessageList.addError(file, message, null, MessageList.ENGLISH);
		MessageList.addError(file, "Erro no XML ("+message+")", null, MessageList.PORTUGUESE);
	}

	@Override
	public void fatalError(SAXParseException arg0) throws SAXException {
		// TODO Auto-generated method stub
		String message = arg0.getLineNumber()+":"+arg0.getColumnNumber()+" "+ arg0.getMessage();
		MessageList.addError(file, message, null, MessageList.ENGLISH);
		MessageList.addError(file, "Erro no XML ("+message+")", null, MessageList.PORTUGUESE);
	}

	@Override
	public void warning(SAXParseException arg0) throws SAXException {
		// TODO Auto-generated method stub
		String message = arg0.getLineNumber()+":"+arg0.getColumnNumber()+" "+ arg0.getMessage();
		MessageList.addWarning(file, message, null, MessageList.ENGLISH);
		MessageList.addWarning(file, "Alerta do XML ("+message+")", null, MessageList.PORTUGUESE);		
	}

}

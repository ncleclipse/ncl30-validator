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
		MessageList.addError(file, arg0.getLineNumber()+":"+arg0.getColumnNumber()+" "+ arg0.getMessage(), null);
	}

	@Override
	public void fatalError(SAXParseException arg0) throws SAXException {
		// TODO Auto-generated method stub
		MessageList.addError(file, arg0.getLineNumber()+":"+arg0.getColumnNumber()+" "+ arg0.getMessage(), null);
	}

	@Override
	public void warning(SAXParseException arg0) throws SAXException {
		// TODO Auto-generated method stub
		MessageList.addWarning(file, arg0.getLineNumber()+":"+arg0.getColumnNumber()+" "+ arg0.getMessage(), null);		
	}

}

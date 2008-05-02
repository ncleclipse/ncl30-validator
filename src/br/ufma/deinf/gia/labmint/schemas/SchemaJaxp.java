package br.ufma.deinf.gia.labmint.schemas;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;


/**
 * 
 * @author robertogerson
 *
 */
public class SchemaJaxp 
{
	/**
	 * 
	 * @param file
	 * @param sch
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
	static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
	static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";	
	
	public void Validate(String file, String sch) throws SAXException, IOException, ParserConfigurationException{
		SchemaFactory schemaFactory =
			SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); 		
		Source schemaSource = 
		     new StreamSource(new File(sch));
		Schema schema = schemaFactory.newSchema(schemaSource);
		
		ErrorHandler mySchemaErrorHandler = new SchemaErrorChecker();
		Validator validator = schema.newValidator();
		validator.setErrorHandler(mySchemaErrorHandler);
		validator.setProperty(JAXP_SCHEMA_SOURCE, sch);
		validator.validate(new StreamSource(file));
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SchemaJaxp sch = new SchemaJaxp();
		try {
			//sch.Validate("testes/memory.xml", "testes/memory.xsd");
			sch.Validate("testes/tmp.ncl", "schemas/profiles/NCL30BDTV.xsd");
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}



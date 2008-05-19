package br.ufma.deinf.gia.labmint.document;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import br.ufma.deinf.gia.labmint.message.MessageList;

public class NclValidatorDocumentComposer extends NclValidatorDocument{

	public NclValidatorDocumentComposer(Document doc) throws ParserConfigurationException, URISyntaxException, SAXException, IOException{		
		super(doc);
	}
	
	protected void parse(Element root){
		if(root.hasAttribute("id")) {
			String id = root.getAttribute("id");
			elements.put(id, root);
		}
		
		/*Código retirado para funcionar com o composer
		 if(root.getTagName().equals("importBase")){
			if(root.hasAttribute("alias")){
				if(root.hasAttribute("documentURI")){
					DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        		DocumentBuilder db = dbf.newDocumentBuilder();
					Document doc = null;
					URI uri = new URI(root.getAttribute("documentURI"));
//					Se documentUri Ã© absoluto					
					if(uri.isAbsolute()) {
						doc = db.parse(new File(uri));
					}
					else {
						doc = db.parse(DocumentUtil.getAbsoluteFileName(this.getPath(), root.getAttribute("documentURI")));
					}
	        		if(!this.addDocument(root.getAttribute("alias"), new NclValidatorDocument(doc)))
	        			MessageList.addError(this.path, "Two Element with same alias <"+root.getAttribute("alias")+"> ", root);
				}
				else
					MessageList.addError(this.getPath(), "Element <importBase> must to have a <documentUri> Attribute.", root);
			}
			else
				MessageList.addError(this.getPath(), "Element <importBase> must to have a <alias> Attribute.", root);
		}
		 */
		NodeList nodeList = root.getChildNodes();
		for(int i=0; i<nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if(node.getNodeType()==Node.ELEMENT_NODE) {
				parse( (Element)node );
			}
		}
	}
}

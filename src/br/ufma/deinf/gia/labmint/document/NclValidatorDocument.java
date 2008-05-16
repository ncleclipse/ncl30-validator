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

import com.sun.swing.internal.plaf.metal.resources.metal;

import br.ufma.deinf.gia.labmint.message.MessageList;

public class NclValidatorDocument{
	protected Map<String, Element> elements;
	protected Map<String, NclValidatorDocument> aliases;
	protected Element root;
	protected String id;
	protected String path;
	
	public NclValidatorDocument(Document doc) throws ParserConfigurationException, URISyntaxException, SAXException, IOException{		
		this.elements = new HashMap<String, Element>();
		this.aliases = new HashMap<String, NclValidatorDocument>();
		this.root = doc.getDocumentElement();
		this.path = doc.getBaseURI();
		//NclDocumentManager.addDocument(doc, this); //registra o documento que está lendo no DocumentManager
		this.id = null;
		//JOptionPane.showMessageDialog(null, this.path);
		System.out.println(">> Validating File = "+this.path);

        if(root.hasAttribute("id")) {
			this.id = root.getAttribute("id");
		}
		else {
			NodeList nodeList = root.getChildNodes();
			for(int i=0; i<nodeList.getLength(); i++){
				Node node = nodeList.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element)node;
					if(element.getTagName().compareTo("body")==0) {
						if(element.hasAttribute("id")) {
							this.id = element.getAttribute("id");
						}
					}
				}
			}
		}
		parse( this.root );
	}

	public String getId() {
		return id;
	}
	
	public Element getDocumentElement() {
		return this.root;
	}
	
	public Element getElement(String id){
		if(elements.containsKey(id)) {
			return elements.get(id);
		}

		if( id.indexOf("#") < 0 ) {
			return null; 
		}

		if( id.indexOf("#") != id.lastIndexOf("#") ) {
			//two or more # chars
			return null;
		}
		
		int index = id.indexOf("#");
		String alias = id.substring(0, index);
		String docId = id.substring(index+1);

		if(!aliases.containsKey(alias)) {
			return null;
		}

		NclValidatorDocument doc = aliases.get(alias);
		return doc.getElement(docId);
	}
	
	protected void parse(Element root) throws ParserConfigurationException, URISyntaxException, SAXException, IOException{
		if(root.hasAttribute("id")) {
			String id = root.getAttribute("id");
			if(!elements.containsKey(id)){
				elements.put(id, root);
			}
			else{
				MessageList.addError(this.id, 
						"Exists more than one element with id '"+id+"'.", 
						root, MessageList.ENGLISH);
				MessageList.addError(this.id, 
						"Existe mais de um elemento com identificador '"+id+"'.", 
						root, MessageList.PORTUGUESE);
			}
		}
		if(root.getTagName().equals("importBase")){
			if(root.hasAttribute("alias")){
				if(root.hasAttribute("documentURI")){
					DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        		DocumentBuilder db = dbf.newDocumentBuilder();
					Document doc = null;
					URI uri = new URI(root.getAttribute("documentURI"));
//					Se documentUri é absoluto					
					if(uri.isAbsolute()) {
						doc = db.parse(new File(uri));
					}
					else {
						doc = db.parse(DocumentUtil.getAbsoluteFileName(this.getPath(), root.getAttribute("documentURI")));
					}
	        		if(!this.addDocument(root.getAttribute("alias"), new NclValidatorDocument(doc))){
	        			MessageList.addError(this.path, "Two Element with same alias <"+root.getAttribute("alias")+"> ", root, MessageList.ENGLISH);
	        			MessageList.addError(this.path, "Existem dois elementos com mesmo atributo alias <"+root.getAttribute("alias")+"> ", root, MessageList.PORTUGUESE);
	        		}
				}
				else{
					MessageList.addError(this.getPath(), "Element <importBase> must to have a <documentUri> Attribute.", root, MessageList.ENGLISH);
					MessageList.addError(this.getPath(), "O elemento <importBase> deve possuir um atributo 'documentUri'.", root, MessageList.PORTUGUESE);
				}
			}
			else{
				MessageList.addError(this.getPath(), "Element <importBase> must to have a <alias> Attribute.", root, MessageList.ENGLISH);
				MessageList.addError(this.getPath(), "O elemento <importBase> deve possuir um atributo 'alias'.", root, MessageList.PORTUGUESE);
			}
		}

		NodeList nodeList = root.getChildNodes();
		for(int i=0; i<nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if(node.getNodeType()==Node.ELEMENT_NODE) {
				parse( (Element)node );
			}
		}
	}
	
	public boolean addDocument(String alias, NclValidatorDocument doc) {
		if( aliases.containsKey(alias) ) return false;
		aliases.put(alias, doc);	
		return true;
	}

	public Map<String, NclValidatorDocument> getAliases() {
		return aliases;
	}

	public void setAliases(Map<String, NclValidatorDocument> aliases) {
		this.aliases = aliases;
	}

	public Map<String, Element> getElements() {
		return elements;
	}

	public void setElements(Map<String, Element> elements) {
		this.elements = elements;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Element getRoot() {
		return root;
	}

	public void setRoot(Element root) {
		this.root = root;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getDir(){
		String path = this.getPath();
		for(int i = path.length()-1; i>=0; i--)
			if (path.charAt(i)=='/') return path.substring(0, i+1);
		return path;
	}
	/**
	 * Retorna o elemento com o id dentro do context idContext.
	 * @param idContext
	 * @param id
	 * @return
	 */
	public Element getElementInContext(String idContext, String id){
		if(elements.containsKey(idContext)) {
			Element context = elements.get(idContext);
			
			NodeList nodeList = context.getChildNodes();
			for(int i = 0; i < nodeList.getLength(); i++){
				Node node = nodeList.item(i);
	     		if(node.getNodeType() == Node.ELEMENT_NODE) {
	     			Element child = (Element)node;
	     			System.out.println(id+" -> "+child.getAttribute("id"));
	     			if(child.getAttribute("id").equals(id))
	     				return child;
	     		}
			}
		}
		return null;
	}
}

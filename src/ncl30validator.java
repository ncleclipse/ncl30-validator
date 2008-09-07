/******************************************************************************
Este arquivo é parte da implementação do ambiente de autoria em Nested Context
Language - NCL Eclipse.

Direitos Autorais Reservados (c) 2007-2008 UFMA/LAWS (Laboratório de Sistemas Avançados da Web) 

Este programa é software livre; você pode redistribuí-lo e/ou modificá-lo sob 
os termos da Licença Pública Geral GNU versão 2 conforme publicada pela Free 
Software Foundation.

Este programa é distribuído na expectativa de que seja útil, porém, SEM 
NENHUMA GARANTIA; nem mesmo a garantia implícita de COMERCIABILIDADE OU 
ADEQUAÇÃO A UMA FINALIDADE ESPECÍFICA. Consulte a Licença Pública Geral do 
GNU versão 2 para mais detalhes. 

Você deve ter recebido uma cópia da Licença Pública Geral do GNU versão 2 junto 
com este programa; se não, escreva para a Free Software Foundation, Inc., no 
endereço 59 Temple Street, Suite 330, Boston, MA 02111-1307 USA. 

Para maiores informações:
ncleclipse@laws.deinf.ufma.br
http://www.laws.deinf.ufma.br/ncleclipse
http://www.laws.deinf.ufma.br

******************************************************************************
This file is part of the authoring environment in Nested Context Language -
NCL Eclipse.

Copyright: 2007-2008 UFMA/LAWS (Laboratory of Advanced Web Systems), All Rights Reserved.

This program is free software; you can redistribute it and/or modify it under 
the terms of the GNU General Public License version 2 as published by
the Free Software Foundation.

This program is distributed in the hope that it will be useful, but WITHOUT ANY 
WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
PARTICULAR PURPOSE.  See the GNU General Public License version 2 for more 
details.

You should have received a copy of the GNU General Public License version 2
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA

For further information contact:
ncleclipse@laws.deinf.ufma.br
http://www.laws.deinf.ufma.br/ncleclipse
http://www.laws.deinf.ufma.br

*******************************************************************************/

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

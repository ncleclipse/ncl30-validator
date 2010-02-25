/******************************************************************************
Este arquivo Ã© parte da implementaÃ§Ã£o do ambiente de autoria em Nested Context
Language - NCL Eclipse.

Direitos Autorais Reservados (c) 2007-2008 UFMA/LAWS (LaboratÃ³rio de Sistemas AvanÃ§ados da Web) 

Este programa Ã© software livre; vocÃª pode redistribuÃ­-lo e/ou modificÃ¡-lo sob 
os termos da LicenÃ§a PÃºblica Geral GNU versÃ£o 2 conforme publicada pela Free 
Software Foundation.

Este programa Ã© distribuÃ­do na expectativa de que seja Ãºtil, porÃ©m, SEM 
NENHUMA GARANTIA; nem mesmo a garantia implÃ­cita de COMERCIABILIDADE OU 
ADEQUAÃ‡ÃƒO A UMA FINALIDADE ESPECÃ�FICA. Consulte a LicenÃ§a PÃºblica Geral do 
GNU versÃ£o 2 para mais detalhes. 

VocÃª deve ter recebido uma cÃ³pia da LicenÃ§a PÃºblica Geral do GNU versÃ£o 2 junto 
com este programa; se nÃ£o, escreva para a Free Software Foundation, Inc., no 
endereÃ§o 59 Temple Street, Suite 330, Boston, MA 02111-1307 USA. 

Para maiores informaÃ§Ãµes:
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

package br.ufma.deinf.gia.labmint.semantics;

import java.util.Vector;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import br.ufma.deinf.gia.labmint.document.NclValidatorDocument;
import br.ufma.deinf.gia.labmint.message.MessageList;

public class Switch extends ElementValidation {

	public Switch(NclValidatorDocument doc) {
		super(doc);
	}

	public boolean validate(Element eSwitch) {
		boolean resultado = true;

		//
		if (!hasValidSwitchIDAttribute(eSwitch))
			resultado = false;

		// Verifica se o atributo 'refer' da <switch> aponta para um outro
		// elemento <swtich>.
		if (!hasValidSwitchReferAttribute(eSwitch))
			resultado = false;
		
		hasValidSwitchContext(eSwitch);
		return resultado;
	}

	private boolean hasValidSwitchIDAttribute(Element eSwitch) {
		// TODO: All!
		return true;
	}

	private boolean hasValidSwitchReferAttribute(Element eSwitch) {
		if (eSwitch.hasAttribute("refer")) {
			if (!eSwitch.hasAttribute("id"))
				return false;// msg gerada pelo DTD
			String idSwitch = eSwitch.getAttribute("id");
			
			if (!eSwitch.hasAttribute("refer"))
				return false;// msg gerada pelo DTD
			String idRefer = eSwitch.getAttribute("refer");
			Element element = doc.getElement(idRefer);

			if (element == null) {
				MessageList.addError(doc.getId(), 4501, eSwitch);
				return false;
			} else if (element.getTagName().compareTo("switch") != 0) {
				MessageList.addError(doc.getId(), 4501, eSwitch);
				return false;
			} else if (element.hasAttribute("refer")) {
				//cannot refer a switch that has a refer too
				Vector<String> args = new Vector<String>();
				args.add(idRefer);
				MessageList.addError(doc.getId(), 4109, eSwitch, args);
				return false;
			}
		}
		return true;
	}
	
	private boolean hasValidSwitchContext(Element eSwitch){
		String idSwitch = eSwitch.getAttribute("id");
		
		NodeList contexts=eSwitch.getElementsByTagName("context");
		
		NodeList bindRules=eSwitch.getElementsByTagName("bindRule");
		
		NodeList defaultComponent=eSwitch.getElementsByTagName("defaultComponent");
		
		NodeList media = eSwitch.getElementsByTagName("media");
		
		NodeList switchPort= eSwitch.getElementsByTagName("switchPort");
		Vector<String> ids= new Vector<String>();
		
		Vector<String> refers= new Vector<String>();
		
		for(int i=0;i<media.getLength();i++){
			ids.add(((Element) media.item(i)).getAttribute("id"));
		}
		
		for (int i=0;i< contexts.getLength();i++){
			ids.add(((Element) contexts.item(i)).getAttribute("id"));
			//System.out.println(contextId.elementAt(i));
		}
		for(int i=0;i<bindRules.getLength();i++){
			refers.add(((Element) bindRules.item(i)).getAttribute("constituent"));
			
		}
		for(int i=0;i<defaultComponent.getLength();i++){
			refers.add(((Element) defaultComponent.item(i)).getAttribute("component"));
			//System.out.println(constituents.lastElement());
		}
		 for(int i=0;i<ids.size();i++){
			 if(!refers.contains(ids.elementAt(i))){
				 Vector<String> args = new Vector<String>();
					args.add(ids.elementAt(i));
					MessageList.addError(doc.getId(), 4502, doc.getElement(ids.elementAt(i)), args);
					return false;
			 }
		 }
		
		return true;
		
	}

}

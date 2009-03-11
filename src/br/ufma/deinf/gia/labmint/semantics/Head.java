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

package br.ufma.deinf.gia.labmint.semantics;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.ufma.deinf.gia.labmint.document.NclValidatorDocument;
import br.ufma.deinf.gia.labmint.message.MessageList;

public class Head extends ElementValidation{
    Map <String, Integer> childPos;
    public Head(NclValidatorDocument doc) {
		super(doc);
		childPos = new HashMap<String, Integer>();
		childPos.put("importedDocumentBase", 0);
		childPos.put("ruleBase", 1);
		childPos.put("transitionBase", 2);
		childPos.put("regionBase", 3);
		childPos.put("descriptorBase", 4);		
		childPos.put("connectorBase", 5);
		childPos.put("meta", 6);
		childPos.put("metadata", 7);
	}

	public boolean validate(Element eHead){
        boolean resultado = true;

        //
        if(!hasValidHeadIDAttribute(eHead)) resultado = false;

        //Valida a ordem em que os filhos de Head devem aparecer
        if(!validateChildPos(eHead)) return false;
       
        return resultado;
    }
	
	public boolean validateChildPos(Element eHead){
		NodeList nodes = eHead.getChildNodes();
		int lastP = -1;
		Element lastEl = null;
		boolean first = true;
		boolean ret = true;
		for(int i = 0; i < nodes.getLength(); i++){
			Node node = nodes.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE){
				Element child = (Element)node;
				if(!childPos.containsKey(child.getTagName())) continue;
				int p = childPos.get(child.getTagName());
				if(first) { first = false; lastP = p; lastEl = child;} 
				else {
					if( p < lastP) {
						Vector <String> args = new Vector <String>();
						args.add(child.getTagName());
						args.add(lastEl.getTagName());
						MessageList.addWarning(doc.getId(),
								3801,
								child, args);
						ret = false;
					}
						
					lastP = p;
					lastEl = child;
				}
			}
		}
		return ret;
	}

    private boolean hasValidHeadIDAttribute(Element eHead){
        //TODO: All!
        return true;
    }

}

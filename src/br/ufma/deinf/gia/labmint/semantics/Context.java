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


import org.w3c.dom.Element;

import br.ufma.deinf.gia.labmint.document.NclValidatorDocument;
import br.ufma.deinf.gia.labmint.message.MessageList;


public class Context extends ElementValidation{

	public Context(NclValidatorDocument doc) {
		super(doc);
	}

	private String idContext = null;
	
    public boolean validate(Element eContext){
        boolean resultado = true;

        idContext = eContext.getAttribute("id");
        
        //
        if(!hasValidContextIDAttribute(eContext)) resultado = false;

        //Verifica se o atributo 'refer' de <context> aponta para um outro elemento <context>.
        if(!hasValidContextReferAttribute(eContext)) resultado = false;
        

        return resultado;
    }

    private boolean hasValidContextIDAttribute(Element eContext){
        //TODO: All!
        return true;
    }

    private boolean hasValidContextReferAttribute(Element eContext){
    	if( eContext.hasAttribute("refer") ) {
    		
			String idRefer = eContext.getAttribute("refer");
			Element element = doc.getElement(idRefer);
			
			if(element==null) {
				MessageList.addError(doc.getId(),
						"There isn't an element with id '" + idRefer + "' to refer.",
						eContext, MessageList.ENGLISH);
				MessageList.addError(doc.getId(),
						"O atributo refer com valor ('" + idRefer + "'), referencia um elemento que n�o existe.",
						eContext, MessageList.PORTUGUESE);				
				return false;
			}
			
			if( element.getTagName().compareTo("context")!=0 ) {
				MessageList.addError(doc.getId(), 
						"There isn't an <context> element with id '" + idRefer + "' to refer.",
				   		eContext, MessageList.ENGLISH);
				MessageList.addError(doc.getId(), 
						"O atributo refer ('" + idRefer + "') referencia um elemento que n�o � um <context>.",
				   		eContext, MessageList.PORTUGUESE);
				return false;
			}
		}
        return true;
    }

}

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


import java.util.Vector;

import org.w3c.dom.Element;

import br.ufma.deinf.gia.labmint.document.NclValidatorDocument;
import br.ufma.deinf.gia.labmint.message.MessageList;


public class DefaultComponent extends ElementValidation{

	public DefaultComponent(NclValidatorDocument doc) {
		super(doc);
	}

	private String idDefaultComponent = null;
	
    public boolean validate(Element eDefaultComponent){
        boolean resultado = true;

        idDefaultComponent = eDefaultComponent.getAttribute("id");
        
        //Verifica se o atributo 'component' de <defaultComponent> aponta para um <component>.
        if(!hasValidDefaultComponentComponentAttribute(eDefaultComponent)) resultado = false;

        return resultado;
    }

    private boolean hasValidDefaultComponentComponentAttribute(Element eDefaultComponent){
    	if(!eDefaultComponent.hasAttribute("component")) return false;
    	//TODO: verificar apenas como filho do <switch>
		String idComponent = eDefaultComponent.getAttribute("component");  
		if( doc.getElement(idComponent)==null ) {
			Vector <String> args = new Vector <String>();
			args.add(idComponent);
			
			MessageList.addError(doc.getId(), 
					3501,
			   		eDefaultComponent, args);			
			return false;
		}
		//TODO: verificar se est� no mesmo contexto
        return true;
    }

}

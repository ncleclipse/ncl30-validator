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

public class SimpleAction extends ElementValidation{

    public SimpleAction(NclValidatorDocument doc) {
		super(doc);
	}

	public boolean validate(Element eSimpleAction){
        boolean resultado = true;

        //
        if(!hasValidSimpleActionByAttribute(eSimpleAction)) resultado = false;

        //
        if(!hasValidSimpleActionValueAttribute(eSimpleAction)) resultado = false;

        //
        if(!hasValidSimpleActionRepeatDelayAttribute(eSimpleAction)) resultado = false;

        //
        if(!hasValidSimpleActionActionTypeAttribute(eSimpleAction)) resultado = false;

        //
        if(!hasValidSimpleActionDelayAttribute(eSimpleAction)) resultado = false;

        //
        if(!hasValidSimpleActionDurationAttribute(eSimpleAction)) resultado = false;

        //
        if(!hasValidSimpleActionQualifierAttribute(eSimpleAction)) resultado = false;

        //
        if(!hasValidSimpleActionMaxAttribute(eSimpleAction)) resultado = false;

        //
        if(!hasValidSimpleActionRoleAttribute(eSimpleAction)) resultado = false;

        //
        if(!hasValidSimpleActionEventTypeAttribute(eSimpleAction)) resultado = false;

        //
        if(!hasValidSimpleActionRepeatAttribute(eSimpleAction)) resultado = false;

        //
        if(!hasValidSimpleActionMinAttribute(eSimpleAction)) resultado = false;

        return resultado;
    }

    private boolean hasValidSimpleActionByAttribute(Element eSimpleAction){
        //TODO: All!
        return true;
    }

    private boolean hasValidSimpleActionValueAttribute(Element eSimpleAction){
        //TODO: All!
        return true;
    }

    private boolean hasValidSimpleActionRepeatDelayAttribute(Element eSimpleAction){
        //TODO: All!
        return true;
    }

    private boolean hasValidSimpleActionActionTypeAttribute(Element eSimpleAction){
        //TODO: All!
        return true;
    }

    private boolean hasValidSimpleActionDelayAttribute(Element eSimpleAction){
        //TODO: All!
        return true;
    }

    private boolean hasValidSimpleActionDurationAttribute(Element eSimpleAction){
        //TODO: All!
        return true;
    }

    private boolean hasValidSimpleActionQualifierAttribute(Element eSimpleAction){
        //TODO: All!
    	if(eSimpleAction.hasAttribute("max")){
    		String attMax = eSimpleAction.getAttribute("max");
    		int maxValue = -1;
    		try{
    			maxValue = (new Integer(attMax)).intValue();
    		}catch(Exception e){}
    		if(attMax.equals("unbounded") || (maxValue > 1) ){
    			if(!eSimpleAction.hasAttribute("qualifier")){
    				MessageList.addError(doc.getId(), 
    						"The attribute 'qualifier' is mandatory when the value of attribute 'max' is longer than 1 or 'unbounded'",
    						eSimpleAction, MessageList.ENGLISH);
    				MessageList.addError(doc.getId(), 
    						"O atributo 'qualifier' � obrigat�rio quando o valor do atributo 'max' � maior que 1 ou 'unbounded'",
    						eSimpleAction, MessageList.PORTUGUESE);
    			}
    		}
    	}
        return true;
    }

    private boolean hasValidSimpleActionMaxAttribute(Element eSimpleAction){
        //TODO: All!
        return true;
    }

    private boolean hasValidSimpleActionRoleAttribute(Element eSimpleAction){
        //TODO: All!
        return true;
    }

    private boolean hasValidSimpleActionEventTypeAttribute(Element eSimpleAction){
        //TODO: All!
        return true;
    }

    private boolean hasValidSimpleActionRepeatAttribute(Element eSimpleAction){
        //TODO: All!
        return true;
    }

    private boolean hasValidSimpleActionMinAttribute(Element eSimpleAction){
        //TODO: All!
        return true;
    }

}

/******************************************************************************
Este arquivo eh uma extensao do ambiente declarativo do middleware 
Ginga (Ginga-NCL).

Direitos Autorais Reservados (c)2007 LabMint, Laboratorio de Midias Interativas 
Departamento de Informatica, UFMA - Universidade Federal do Maranhao

Este programa eh software livre; voce pode redistribui-lo e/ou modifica-lo sob 
os termos da Licenca Publica Geral GNU versao 2 conforme publicada pela Free 
Software Foundation.

Este programa eh distribuï¿½do na expectativa de que seja util, porem, SEM 
NENHUMA GARANTIA; nem mesmo a garantia implicita de COMERCIABILIDADE OU 
ADEQUACAO A UMA FINALIDADE ESPECIFICA. Consulte a Licenca Publica Geral do 
GNU versao 2 para mais detalhes. 

Voce deve ter recebido uma copia da Licenca Publica Geral do GNU versao 2 junto 
com este programa; se nao, escreva para a Free Software Foundation, Inc., no 
endereco 59 Temple Street, Suite 330, Boston, MA 02111-1307 USA. 

Para maiores informacoes:
ncl @ telemidia.puc-rio.br
http://www.ncl.org.br
http://www.ginga.org.br
http://www.softwarepublico.gov.br

Sobre o Validador NCL:
labmint @ gia.deinf.ufma.br
http://www.gia.deinf.ufma.br/~labmint/


******************************************************************************
This file is an extension of the declarative environment of 
middleware Ginga (Ginga-NCL)

Copyright: 2007 LabMint - Laboratory of Interactive Medias, Science Computing
           Department, Federal University of Maranhao, All Rights Reserved.

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
ncl @ telemidia.puc-rio.br
http://www.ncl.org.br
http://www.ginga.org.br
http://www.softwarepublico.gov.br

About NCL Validator:
labmint @ gia.deinf.ufma.br
http://www.gia.deinf.ufma.br/~labmint/

*******************************************************************************/

package br.ufma.deinf.gia.labmint.semantics;

import org.w3c.dom.Element;

import br.ufma.deinf.gia.labmint.document.NclValidatorDocument;
import br.ufma.deinf.gia.labmint.message.MessageList;

public class SimpleCondition extends ElementValidation{

    public SimpleCondition(NclValidatorDocument doc) {
		super(doc);
	}

	public boolean validate(Element eSimpleCondition){
        boolean resultado = true;

        //
        if(!hasValidSimpleConditionKeyAttribute(eSimpleCondition)) resultado = false;

        //
        if(!hasValidSimpleConditionDelayAttribute(eSimpleCondition)) resultado = false;

        //
        if(!hasValidSimpleConditionQualifierAttribute(eSimpleCondition)) resultado = false;

        //
        if(!hasValidSimpleConditionMaxAttribute(eSimpleCondition)) resultado = false;

        //
        if(!hasValidSimpleConditionRoleAttribute(eSimpleCondition)) resultado = false;

        //
        if(!hasValidSimpleConditionTransitionAttribute(eSimpleCondition)) resultado = false;

        //
        if(!hasValidSimpleConditionEventTypeAttribute(eSimpleCondition)) resultado = false;

        //
        if(!hasValidSimpleConditionMinAttribute(eSimpleCondition)) resultado = false;

        return resultado;
    }

    private boolean hasValidSimpleConditionKeyAttribute(Element eSimpleCondition){
        //TODO: All!
        return true;
    }

    private boolean hasValidSimpleConditionDelayAttribute(Element eSimpleCondition){
        //TODO: All!
        return true;
    }

    private boolean hasValidSimpleConditionQualifierAttribute(Element eSimpleCondition){
        //TODO: All!
    	if(eSimpleCondition.hasAttribute("max")){
    		String attMax = eSimpleCondition.getAttribute("max");
    		int maxValue = -1;
    		try{
    			maxValue = (new Integer(attMax)).intValue();
    		}catch(Exception e){}
    		if(attMax.equals("unbounded") || (maxValue > 1) ){
    			if(!eSimpleCondition.hasAttribute("qualifier")){
    				MessageList.addError(doc.getId(), 
    						"The attribute 'qualifier' is mandatory when the value of attribute 'max' is longer than 1 or 'unbounded'.",
    						eSimpleCondition, MessageList.ENGLISH);
    				MessageList.addError(doc.getId(), 
    						"O atributo 'qualifier' é obrigatório quando o valor do atributo 'max' é maior do que 1 ou 'unbounded'.",
    						eSimpleCondition, MessageList.PORTUGUESE);
    			}
    		}
    	}
        return true;
    }

    private boolean hasValidSimpleConditionMaxAttribute(Element eSimpleCondition){
        //TODO: All!
        return true;
    }

    private boolean hasValidSimpleConditionRoleAttribute(Element eSimpleCondition){
        //TODO: All!
        return true;
    }

    private boolean hasValidSimpleConditionTransitionAttribute(Element eSimpleCondition){
        //TODO: All!
        return true;
    }

    private boolean hasValidSimpleConditionEventTypeAttribute(Element eSimpleCondition){
        //TODO: All!
        return true;
    }

    private boolean hasValidSimpleConditionMinAttribute(Element eSimpleCondition){
        //TODO: All!
        return true;
    }

}

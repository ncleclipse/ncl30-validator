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


public class DefaultDescriptor extends ElementValidation{

	public DefaultDescriptor(NclValidatorDocument doc) {
		super(doc);
	}

	private String idDefaultDescriptor = null;
	
    public boolean validate(Element eDefaultDescriptor){
        boolean resultado = true;
        
        idDefaultDescriptor = eDefaultDescriptor.getAttribute("id");

        //Verifica se o atributo 'descriptor' do <defaultDescriptor> aponta para um <descriptor>.
        if(!hasValidDefaultDescriptorDescriptorAttribute(eDefaultDescriptor)) resultado = false;

        return resultado;
    }

    private boolean hasValidDefaultDescriptorDescriptorAttribute(Element eDefaultDescriptor){
    	if(!eDefaultDescriptor.hasAttribute("descriptor")) return false;
		String idDescriptor = eDefaultDescriptor.getAttribute("descriptor");
		Element element = doc.getElement( idDescriptor ); 
		if(element==null) {
			MessageList.addError(doc.getId(), 
					"There is not a <descriptor> element with id '" + idDescriptor + "'.",
			   		eDefaultDescriptor, MessageList.ENGLISH);
			MessageList.addError(doc.getId(), 
					"Não existe um elemento <descriptor> com identificador '" + idDescriptor + "'.",
			   		eDefaultDescriptor, MessageList.PORTUGUESE);
			return false;
		}
		else if(element.getTagName().compareTo("descriptor")!=0) {
			MessageList.addError(doc.getId(), 
					"The element with id '" + idDescriptor + "' is not a <descriptor> element.",
			   		eDefaultDescriptor, MessageList.ENGLISH);
			MessageList.addError(doc.getId(), 
					"O elemento pelo atributo descriptor ('" + idDescriptor + "') não é um elemento <descriptor>.",
			   		eDefaultDescriptor, MessageList.PORTUGUESE);			
			return false;			
		}
        return true;
    }

}

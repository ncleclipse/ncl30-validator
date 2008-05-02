/******************************************************************************
Este arquivo eh uma extensao do ambiente declarativo do middleware 
Ginga (Ginga-NCL).

Direitos Autorais Reservados (c)2007 LabMint, Laboratorio de Midias Interativas 
Departamento de Informatica, UFMA - Universidade Federal do Maranhao

Este programa eh software livre; voce pode redistribui-lo e/ou modifica-lo sob 
os termos da Licenca Publica Geral GNU versao 2 conforme publicada pela Free 
Software Foundation.

Este programa eh distribu�do na expectativa de que seja util, porem, SEM 
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


public class Descriptor extends ElementValidation{

    public Descriptor(NclValidatorDocument doc) {
		super(doc);
	}

	public boolean validate(Element eDescriptor){
        boolean resultado = true;

        //
        if(!hasValidDescriptorFocusSelSrcAttribute(eDescriptor)) resultado = false;

        //
        if(!hasValidDescriptorSelBorderColorAttribute(eDescriptor)) resultado = false;

        //
        if(!hasValidDescriptorPlayerAttribute(eDescriptor)) resultado = false;

        //
        if(!hasValidDescriptorMoveLeftAttribute(eDescriptor)) resultado = false;

        //
        if(!hasValidDescriptorFocusIndexAttribute(eDescriptor)) resultado = false;

        //
        if(!hasValidDescriptorFocusSrcAttribute(eDescriptor)) resultado = false;

        //
        if(!hasValidDescriptorExplicitDurAttribute(eDescriptor)) resultado = false;

        //
        if(!hasValidDescriptorMoveUpAttribute(eDescriptor)) resultado = false;

        //
        if(!hasValidDescriptorMoveDownAttribute(eDescriptor)) resultado = false;

        //
        if(!hasValidDescriptorTransOutAttribute(eDescriptor)) resultado = false;

        //Verifica se o atributo 'region' do elemento <descriptor> aponta para um <region>.
        if(!hasValidDescriptorRegionAttribute(eDescriptor)) resultado = false;

        //
        if(!hasValidDescriptorFocusBorderColorAttribute(eDescriptor)) resultado = false;

        //
        if(!hasValidDescriptorIDAttribute(eDescriptor)) resultado = false;

        //
        if(!hasValidDescriptorMoveRightAttribute(eDescriptor)) resultado = false;

        //
        if(!hasValidDescriptorFocusBorderWidthAttribute(eDescriptor)) resultado = false;

        //
        if(!hasValidDescriptorTransInAttribute(eDescriptor)) resultado = false;

        //
        if(!hasValidDescriptorFocusBorderTransparencyAttribute(eDescriptor)) resultado = false;

        //
        if(!hasValidDescriptorFreezeAttribute(eDescriptor)) resultado = false;

        return resultado;
    }

    private boolean hasValidDescriptorFocusSelSrcAttribute(Element eDescriptor){
        //TODO: All!
        return true;
    }

    private boolean hasValidDescriptorSelBorderColorAttribute(Element eDescriptor){
        //TODO: All!
        return true;
    }

    private boolean hasValidDescriptorPlayerAttribute(Element eDescriptor){
        //TODO: All!
        return true;
    }

    private boolean hasValidDescriptorMoveLeftAttribute(Element eDescriptor){
        //TODO: All!
        return true;
    }

    private boolean hasValidDescriptorFocusIndexAttribute(Element eDescriptor){
        //TODO: All!
        return true;
    }

    private boolean hasValidDescriptorFocusSrcAttribute(Element eDescriptor){
        //TODO: All!
        return true;
    }

    private boolean hasValidDescriptorExplicitDurAttribute(Element eDescriptor){
        //TODO: All!
        return true;
    }

    private boolean hasValidDescriptorMoveUpAttribute(Element eDescriptor){
        //TODO: All!
        return true;
    }

    private boolean hasValidDescriptorMoveDownAttribute(Element eDescriptor){
        //TODO: All!
        return true;
    }

    private boolean hasValidDescriptorTransOutAttribute(Element eDescriptor){
        //TODO: All!
        return true;
    }

    private boolean hasValidDescriptorRegionAttribute(Element eDescriptor){
    	if (eDescriptor.hasAttribute("region")){
			String idRegion = eDescriptor.getAttribute("region");
			Element element = doc.getElement(idRegion);
			if( element==null ) {
				MessageList.addError(doc.getId(), 
						"There is not a <region> element with id '" + idRegion + "'.",
				   		eDescriptor);
				return false;
			}
			else if( element.getTagName().compareTo("region")!=0 ) {
				MessageList.addError(doc.getId(), 
						"The element with id '" + idRegion + "' is not a <region> element.",
				   		eDescriptor);
				return false;
			}
		}
        return true;
    }

    private boolean hasValidDescriptorFocusBorderColorAttribute(Element eDescriptor){
        //TODO: All!
        return true;
    }

    private boolean hasValidDescriptorIDAttribute(Element eDescriptor){
        //TODO: All!
        return true;
    }

    private boolean hasValidDescriptorMoveRightAttribute(Element eDescriptor){
        //TODO: All!
        return true;
    }

    private boolean hasValidDescriptorFocusBorderWidthAttribute(Element eDescriptor){
        //TODO: All!
        return true;
    }

    private boolean hasValidDescriptorTransInAttribute(Element eDescriptor){
        //TODO: All!
        return true;
    }

    private boolean hasValidDescriptorFocusBorderTransparencyAttribute(Element eDescriptor){
        //TODO: All!
        return true;
    }

    private boolean hasValidDescriptorFreezeAttribute(Element eDescriptor){
        //TODO: All!
        return true;
    }

}

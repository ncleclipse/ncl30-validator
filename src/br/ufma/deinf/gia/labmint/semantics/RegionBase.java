/******************************************************************************
Este arquivo eh uma extensao do ambiente declarativo do middleware 
Ginga (Ginga-NCL).

Direitos Autorais Reservados (c)2008 LabMint, Laboratorio de Midias Interativas 
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

Copyright: 2008 LabMint - Laboratory of Interactive Medias, Science Computing
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

About NCL Eclipse:
labmint @ gia.deinf.ufma.br
http://www.gia.deinf.ufma.br/~robertogerson/ncleclipse

*******************************************************************************/

package br.ufma.deinf.gia.labmint.semantics;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Element;

import br.ufma.deinf.gia.labmint.document.NclValidatorDocument;
import br.ufma.deinf.gia.labmint.message.MessageList;

public class RegionBase extends ElementValidation{

    public RegionBase(NclValidatorDocument doc) {
		super(doc);
	}

	public boolean validate(Element eRegionBase){
        boolean resultado = true;

        //
        if(!hasValidRegionBaseDeviceAttribute(eRegionBase)) resultado = false;

        //
        if(!hasValidRegionBaseIDAttribute(eRegionBase)) resultado = false;

        return resultado;
    }

    private boolean hasValidRegionBaseDeviceAttribute(Element eRegionBase){
        //Atributo device deve ser da forma systemScreen(i) ou systemAudio(i)
    	if(eRegionBase.hasAttribute("device")){
    		String attString = eRegionBase.getAttribute("device");
    		Pattern p = Pattern.compile("systemScreen\\(([0-9])+\\)");
    		Matcher m = p.matcher(attString);
    		if(!m.matches()){
    			p = Pattern.compile("systemAudio\\(([0-9])+\\)");;
    			m = p.matcher(attString);
    			if(!m.matches()){
    				MessageList.addWarning(doc.getId(), "Attribute device must be like as systemScreen(i) or systemAudio(i).", eRegionBase, MessageList.ENGLISH);
    				MessageList.addWarning(doc.getId(), "Atributo 'device' deve ser do formato systemScreen(i) ou systemAudio(i).", eRegionBase, MessageList.PORTUGUESE);
    			}
    		}
    	}
        return true;
    }

    private boolean hasValidRegionBaseIDAttribute(Element eRegionBase){
        return true;
    }

}

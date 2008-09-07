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

public class AttributeAssessment extends ElementValidation{

    public AttributeAssessment(NclValidatorDocument doc){
        super(doc);
    }

    public boolean validate(Element eAttributeAssessment){
        boolean resultado = true;

        //
        if(!hasValidAttributeAssessmentKeyAttribute(eAttributeAssessment)) resultado = false;

        //
        if(!hasValidAttributeAssessmentOffsetAttribute(eAttributeAssessment)) resultado = false;

        //
        if(!hasValidAttributeAssessmentAttributeTypeAttribute(eAttributeAssessment)) resultado = false;

        //
        if(!hasValidAttributeAssessmentRoleAttribute(eAttributeAssessment)) resultado = false;

        //
        if(!hasValidAttributeAssessmentEventTypeAttribute(eAttributeAssessment)) resultado = false;

        return resultado;
    }

    private boolean hasValidAttributeAssessmentKeyAttribute(Element eAttributeAssessment){
        //TODO: All!
        return true;
    }

    private boolean hasValidAttributeAssessmentOffsetAttribute(Element eAttributeAssessment){
        //TODO: All!
        return true;
    }

    private boolean hasValidAttributeAssessmentAttributeTypeAttribute(Element eAttributeAssessment){
        //TODO: All!
        return true;
    }

    private boolean hasValidAttributeAssessmentRoleAttribute(Element eAttributeAssessment){
        //TODO: All!
        return true;
    }

    private boolean hasValidAttributeAssessmentEventTypeAttribute(Element eAttributeAssessment){
        //TODO: All!
        return true;
    }

}

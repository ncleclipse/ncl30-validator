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

public class BindRule extends ElementValidation {

	public BindRule(NclValidatorDocument doc) {
		super(doc);
	}

	public boolean validate(Element eBindRule) {
		boolean resultado = true;

		//
		if (!hasValidBindRuleConstituentAttribute(eBindRule))
			resultado = false;

		//
		if (!hasValidBindRuleRuleAttribute(eBindRule))
			resultado = false;

		return resultado;
	}

	private boolean hasValidBindRuleConstituentAttribute(Element eBindRule) {
		// This error message is in the DTD validator
		if (!eBindRule.hasAttribute("constituent"))
			return false;
		String idComponent = eBindRule.getAttribute("constituent");

		String fatherTagName = ((Element) eBindRule.getParentNode())
				.getTagName();
		Element eComponent = doc.getElement(idComponent);
		if (fatherTagName.equals("switch")) {
			if (eComponent != null && !eComponent.getTagName().equals("media")
					&& !eComponent.getTagName().equals("switch")
					&& !eComponent.getTagName().equals("context")) {

				Vector<String> args = new Vector<String>();
				args.add(idComponent);

				MessageList.addError(doc.getId(), 4701, eBindRule, args);
			}
		} else if (fatherTagName.equals("switchDescriptor")) {
			if (eComponent != null
					&& !eComponent.getTagName().equals("descriptor")) {

				Vector<String> args = new Vector<String>();
				args.add(idComponent);

				MessageList.addError(doc.getId(), 4702, eBindRule, args);
			}
		}
		if (!constituentIsInMySwitch(eBindRule)) {
			Vector<String> args = new Vector<String>();
			args.add(idComponent);

			MessageList.addError(doc.getId(), 4703, eBindRule, args);
			return false;
		}
		return true;
	}

	private boolean hasValidBindRuleRuleAttribute(Element eBindRule) {
		String idRule = eBindRule.getAttribute("rule");
		Element rule = doc.getElement(idRule);

		if (rule == null || !rule.getTagName().equals("rule")) {
			Vector<String> args = new Vector<String>();
			args.add(eBindRule.getAttribute("rule"));

			MessageList.addError(doc.getId(), 4704, eBindRule, args);
			return false;
		}
		return true;
	}

	private boolean constituentIsInMySwitch(Element eBindRule) {
		// DTD checks if component exists and print message.
		if (!eBindRule.hasAttribute("component"))
			return true;

		Element component = doc.getElement(eBindRule.getAttribute("component"));

		Element eSwitch = (Element) eBindRule.getParentNode();
		// this is error, but the DTD validates
		if (eSwitch == null)
			return false;

		// get the element with id refered by component in the context
		Element element = doc.getElementChild(eSwitch, eBindRule
				.getAttribute("component"));

		// if it doesn't exists return false
		if (element == null)
			return false;

		// check if the component refered is media, switch or component
		if (element.getTagName().equals("media")
				|| element.getTagName().equals("switch")
				|| element.getTagName().equals("context")
				|| element.getTagName().equals("descriptor"))
			return true;

		return false;

	}
}

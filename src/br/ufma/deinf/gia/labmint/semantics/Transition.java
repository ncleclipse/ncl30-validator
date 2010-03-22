/*******************************************************************************
 * Este arquivo é parte da implementação do ambiente de autoria em Nested 
 * Context Language - NCL Eclipse.
 * Direitos Autorais Reservados (c) 2007-2010 UFMA/LAWS (Laboratório de Sistemas 
 * Avançados da Web)
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou modificá-lo sob
 * os termos da Licença Pública Geral GNU versão 2 conforme publicada pela Free 
 * Software Foundation.
 *
 * Este programa é distribuído na expectativa de que seja útil, porém, SEM 
 * NENHUMA GARANTIA; nem mesmo a garantia implícita de COMERCIABILIDADE OU
 * ADEQUAÇÃO A UMA FINALIDADE ESPECÍFICA. Consulte a Licença Pública Geral do
 * GNU versão 2 para mais detalhes. Você deve ter recebido uma cópia da Licença
 * Pública Geral do GNU versão 2 junto com este programa; se não, escreva para a
 * Free Software Foundation, Inc., no endereço 59 Temple Street, Suite 330,
 * Boston, MA 02111-1307 USA.
 *
 * Para maiores informações:
 * - ncleclipse@laws.deinf.ufma.br
 * - http://www.laws.deinf.ufma.br/ncleclipse
 * - http://www.laws.deinf.ufma.br
 *
 *******************************************************************************
 * This file is part of the authoring environment in Nested Context Language -
 * NCL Eclipse.
 * Copyright: 2007-2010 UFMA/LAWS (Laboratory of Advanced Web Systems), All
 * Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License version 2 as published by
 * the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License version 2 for
 * more details. You should have received a copy of the GNU General Public 
 * License version 2 along with this program; if not, write to the Free 
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 
 * 02110-1301, USA.
 *
 * For further information contact:
 * - ncleclipse@laws.deinf.ufma.br
 * - http://www.laws.deinf.ufma.br/ncleclipse
 * - http://www.laws.deinf.ufma.br
 *
 ******************************************************************************/

package br.ufma.deinf.gia.labmint.semantics;

import org.w3c.dom.Element;

import br.ufma.deinf.gia.labmint.document.NclValidatorDocument;

public class Transition extends ElementValidation {

	public Transition(NclValidatorDocument doc) {
		super(doc);
	}

	public boolean validate(Element eTransition) {
		boolean resultado = true;

		//
		if (!hasValidTransitionSubtypeAttribute(eTransition))
			resultado = false;

		//
		if (!hasValidTransitionVertRepeatAttribute(eTransition))
			resultado = false;

		//
		if (!hasValidTransitionHorRepeatAttribute(eTransition))
			resultado = false;

		//
		if (!hasValidTransitionFadeColorAttribute(eTransition))
			resultado = false;

		//
		if (!hasValidTransitionStartProgressAttribute(eTransition))
			resultado = false;

		//
		if (!hasValidTransitionDurAttribute(eTransition))
			resultado = false;

		//
		if (!hasValidTransitionBorderColorAttribute(eTransition))
			resultado = false;

		//
		if (!hasValidTransitionTypeAttribute(eTransition))
			resultado = false;

		//
		if (!hasValidTransitionDirectionAttribute(eTransition))
			resultado = false;

		//
		if (!hasValidTransitionEndProgressAttribute(eTransition))
			resultado = false;

		//
		if (!hasValidTransitionBorderWidthAttribute(eTransition))
			resultado = false;

		return resultado;
	}

	private boolean hasValidTransitionSubtypeAttribute(Element eTransition) {
		//TODO: All!
		return true;
	}

	private boolean hasValidTransitionVertRepeatAttribute(Element eTransition) {
		//TODO: All!
		return true;
	}

	private boolean hasValidTransitionHorRepeatAttribute(Element eTransition) {
		//TODO: All!
		return true;
	}

	private boolean hasValidTransitionFadeColorAttribute(Element eTransition) {
		//TODO: All!
		return true;
	}

	private boolean hasValidTransitionStartProgressAttribute(Element eTransition) {
		//TODO: All!
		return true;
	}

	private boolean hasValidTransitionDurAttribute(Element eTransition) {
		//TODO: All!
		return true;
	}

	private boolean hasValidTransitionBorderColorAttribute(Element eTransition) {
		//TODO: All!
		return true;
	}

	private boolean hasValidTransitionTypeAttribute(Element eTransition) {
		//TODO: All!
		return true;
	}

	private boolean hasValidTransitionDirectionAttribute(Element eTransition) {
		//TODO: All!
		return true;
	}

	private boolean hasValidTransitionEndProgressAttribute(Element eTransition) {
		//TODO: All!
		return true;
	}

	private boolean hasValidTransitionBorderWidthAttribute(Element eTransition) {
		//TODO: All!
		return true;
	}

}

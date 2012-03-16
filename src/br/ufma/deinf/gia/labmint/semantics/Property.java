/*******************************************************************************
 * Este arquivo Ã© parte da implementaÃ§Ã£o do ambiente de autoria em Nested 
 * Context Language - NCL Eclipse.
 * Direitos Autorais Reservados (c) 2007-2010 UFMA/LAWS (LaboratÃ³rio de Sistemas 
 * AvanÃ§ados da Web)
 *
 * Este programa Ã© software livre; vocÃª pode redistribuÃ­-lo e/ou modificÃ¡-lo sob
 * os termos da LicenÃ§a PÃºblica Geral GNU versÃ£o 2 conforme publicada pela Free 
 * Software Foundation.
 *
 * Este programa Ã© distribuÃ­do na expectativa de que seja Ãºtil, porÃ©m, SEM 
 * NENHUMA GARANTIA; nem mesmo a garantia implÃ­cita de COMERCIABILIDADE OU
 * ADEQUAÃ‡ÃƒO A UMA FINALIDADE ESPECÃ�FICA. Consulte a LicenÃ§a PÃºblica Geral do
 * GNU versÃ£o 2 para mais detalhes. VocÃª deve ter recebido uma cÃ³pia da LicenÃ§a
 * PÃºblica Geral do GNU versÃ£o 2 junto com este programa; se nÃ£o, escreva para a
 * Free Software Foundation, Inc., no endereÃ§o 59 Temple Street, Suite 330,
 * Boston, MA 02111-1307 USA.
 *
 * Para maiores informaÃ§Ãµes:
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

public class Property extends ElementValidation {

	public Property(NclValidatorDocument doc) {
		super(doc);
	}

	public boolean validate(Element eProperty) {
		boolean resultado = true;

		//
		if (!hasValidPropertyNameAttribute(eProperty))
			resultado = false;

		//
		if (!hasValidPropertyValueAttribute(eProperty))
			resultado = false;

		return resultado;
	}

	private boolean hasValidPropertyNameAttribute(Element eProperty) {
		//TODO: All!
		return true;
	}

	private boolean hasValidPropertyValueAttribute(Element eProperty) {
		//TODO: All!
		return true;
	}

}

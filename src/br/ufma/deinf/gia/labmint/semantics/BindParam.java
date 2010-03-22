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

import java.util.Vector;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.ufma.deinf.gia.labmint.document.NclValidatorDocument;
import br.ufma.deinf.gia.labmint.message.MessageList;

public class BindParam extends ElementValidation {

	public BindParam(NclValidatorDocument doc) {
		super(doc);
	}

	public boolean validate(Element eBindParam) {
		boolean resultado = true;

		//
		if (!hasValidBindParamNameAttribute(eBindParam))
			resultado = false;

		//
		if (!hasValidBindParamValueAttribute(eBindParam))
			resultado = false;

		return resultado;
	}

	private boolean hasValidBindParamNameAttribute(Element eBindParam) {
		Element bind = (Element) eBindParam.getParentNode();
		String nameBindParam = eBindParam.getAttribute("name");

		if (nameBindParam == null)
			return false; // is validated in Structure

		if (bind != null) {
			Element link = (Element) bind.getParentNode();
			if (link != null && link.getTagName().equals("link")) {
				if (link.hasAttribute("xconnector")) {
					String xconnector = link.getAttribute("xconnector");

					// TODO: make a function to this
					Element causalConnector = doc.getElement(xconnector);
					if (causalConnector != null) {
						// find for connectorParam with this name
						NodeList child = causalConnector.getChildNodes();
						for (int i = 0; i < child.getLength(); i++) {
							Node node = child.item(i);
							if (node.getNodeType() == Node.ELEMENT_NODE) {
								Element atual = (Element) node;
								if (atual.getTagName().equals("connectorParam")) {
									if (atual.getAttribute("name") != null
											&& atual.getAttribute("name")
													.equals(nameBindParam))
										return true;
								}
							}
						}
						// TODO: end make a function to this

						Vector<String> args = new Vector<String>();
						args.add(nameBindParam);
						args.add(xconnector);
						MessageList.addWarning(doc.getId(), 4601, eBindParam,
								args);
					}
				}
			}
		}
		return false;
	}

	private boolean hasValidBindParamValueAttribute(Element eBindParam) {
		// TODO: All!
		return true;
	}

}

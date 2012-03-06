/*******************************************************************************
 * Este arquivo é parte da implementação do ambiente de autoria em Nested 
 * Context Language - NCL Eclipse.
 * Direitos Autorais Reservados (c) 2007-2012 UFMA/LAWS (Laboratório de Sistemas 
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
 * Copyright: 2007-2012 UFMA/LAWS (Laboratory of Advanced Web Systems), All
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

package br.ufma.deinf.laws.tal;

import java.util.Vector;

import org.w3c.dom.Element;

import br.ufma.deinf.gia.labmint.message.MessageList;
import br.ufma.deinf.laws.util.TALUtilities;

/**
 * @author Rodrigo Costa <rodrim.c@laws.deinf.ufma.br>
 *
 */
public class TALValidation {
	public static boolean hasValidTALClassAttribute(Element element, String docId) {
		if (!element.hasAttribute("tal:class"))
			return true;

		Element parent = (Element) element.getParentNode();

		if (element.getTagName().equals("area")
				|| element.getTagName().equals("property")) {

			if (parent == null)
				return false;

			parent = (Element) parent.getParentNode();
			if (parent == null)
				return false;
		}

		String classAttributeValue = element.getAttribute("tal:class");
		String templateAttributeValue = parent.getAttribute("tal:template");

		if (parent != null) {
			if (templateAttributeValue.equals("")) {
				MessageList.addWarning(docId, 5002, element);

			} else {

				String[] values = TALUtilities
						.splitTemplateAttributeValue(templateAttributeValue);

				String relativeTemplatePath = values[0];
				String templatePath = values[1];
				String templateId = values[2];

				Vector<String> accepts = TALUtilities
						.getValidClassAttributeValues(element.getTagName(),
								classAttributeValue, templatePath,
								relativeTemplatePath, templateId);

				if (accepts.size() == 0) {
					Vector<String> args = new Vector<String>();
					args.add(templateId);
					args.add(element.getTagName());

					MessageList.addWarning(docId, 5004, element, args);

				} else if (!accepts.contains(classAttributeValue)) {
					Vector<String> args = new Vector<String>();

					String str = "";
					for (String value : accepts)
						str += "'" + value + "' ";
					args.add(str);

					MessageList.addWarning(docId, 5003, element, args);
				}

			}

		}

		return true;
	}
}

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
package br.ufma.deinf.gia.labmint.ncl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe responsável por validar os tipos básicos da linguagem NCL.
 * @author roberto
 *
 */
public class DataType {
	public static final int UNKNOWN = -1;
	public static final int ID = 0;
	public static final int STRING  = 1;
	public static final int INTEGER  = 2;
	public static final int DOUBLE  = 3;
	public static final int COORDINATE  = 4;
	public static final int TIME  = 5;
	public static final int URI  = 6;
	public static final int MEDIA_DESCRIPTION = 7;
	public static final int SIZE = 8;
	public static final int XMLNS = 9;
	public static final int QUANTITY = 10;
	public static final int XCONNECTOR = 11;
	public static final int BOOLEAN_OPERATOR = 12;
	public static final int SYNCHRONISM_OPERATOR = 13;
	
	public DataType() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param str
	 * @return
	 * @deprecated
	 */
	public static int getType(String str){
		if(isInteger(str)) return INTEGER;
		if(isDouble(str)) return DOUBLE;
		if(isCoordinate(str)) return COORDINATE;
		if(isId(str)) return ID;
		if(isTime(str)) return TIME;
		if(isUri(str)) return URI;
		return STRING;
	}
	
	public static boolean isInteger(String str){
		Pattern p = Pattern.compile("^[0-9]*$");
	    Matcher m = p.matcher(str);

	    return m.matches();
	}
	
	public static boolean isString(String str){
		return true;
	}
	
	public static boolean isDouble(String str){
		return true;
	}
	/*	
	 * FORMATOS
	 * rectangle: left-x,top-y,right-x,bottom-y
	 * circle: center-x,center-y,radius
	 * polygon: x1,y1,x2,y2,...,xN,yN
	 * 
	 * Por enquanto os valores são inteiros. Tem que melhorar.
	 * Xn e Yn tem que ter a mesma quantidade
	*/
	public static boolean isCoordinate(String str){
		Pattern p = Pattern.compile("^[0-9]*(,[0-9]*){2,}");
	    Matcher m = p.matcher(str);

	    return m.matches();
	}
	//FORMATO: 9s, 90s, 11s
	//Tem minuto e hora também???
	public static boolean isTime(String str){
		Pattern p = Pattern.compile("^[0-9]*[s]$");
	    Matcher m = p.matcher(str);

	    return m.matches();
	}
	
	public static boolean isUri(String str){
		try {
			URI uri = new URI(str);
			return true;
		} catch (URISyntaxException e) {
			return false;
		}
	}
	
	//Conjutndo de letras ou numeros começando com uma letra
	public static boolean isId(String str){
		Pattern p = Pattern.compile("^[a-zA-Z].*$");
	    Matcher m = p.matcher(str);

	    return m.matches();
	}
	
	// 20px, 20, 20%
	public static boolean isSize(String str){
		if(isInteger(str)) return true;
		Pattern p = Pattern.compile("^[0-9]*px$");
	    Matcher m = p.matcher(str);

	    if(m.matches()) return true;
	    
	    p = Pattern.compile("^[0-9]*%$");
	    m = p.matcher(str);
	    return m.matches();
	}

	public static boolean isMediaDescription(String value) {
		// TODO Auto-generated method stub
		//	Est� sendo validado no Sem�ntico
		return true;
	}
	
	public static boolean isXMLNS(String value) {
		// TODO Auto-generated method stub
		return (value.equals("http://www.ncl.org.br/NCL3.0/EDTVProfile")
				|| value.equals("http://www.ncl.org.br/NCL3.0/BDTVProfile"));
	}
	
	public static boolean isDataType(int dataType, String value){
		boolean ok = true;
		switch(dataType){
			case DataType.COORDINATE: 
				if(!DataType.isCoordinate(value)) ok = false;
				break;
			case DataType.INTEGER: 
				if(!DataType.isInteger(value)) ok = false;
				break;
			case DataType.STRING: 
				if(!DataType.isString(value)) ok = false;
				break;
			case DataType.DOUBLE: 
				if(!DataType.isDouble(value)) ok = false;
				break;
			case DataType.ID: 
				if(!DataType.isId(value)) ok = false;
				break;
			case DataType.TIME: 
				if(!DataType.isTime(value)) ok = false;
				break;
			case DataType.URI: 
				if(!DataType.isUri(value)) ok = false;
				break;
			case DataType.MEDIA_DESCRIPTION: 
				if(!DataType.isMediaDescription(value)) ok = false;
				break;
			case DataType.SIZE: 
				if(!DataType.isSize(value)) ok = false;
				break;
			case DataType.XMLNS:
				if(!DataType.isXMLNS(value)) ok = false;
				break;
			case DataType.QUANTITY:
				if(!DataType.isQuantity(value)) ok = false;
				break;
			case DataType.XCONNECTOR:
				if(!DataType.isXConnector(value)) ok = false;
				break;
			case DataType.BOOLEAN_OPERATOR:
				if(!DataType.isBoleanOperator(value)) ok = false;
				break;
			case DataType.SYNCHRONISM_OPERATOR:
				if(!DataType.isSyncronismOperator(value)) ok = false;
				break;				
		}
		return ok;
	}
	
	private static boolean isSyncronismOperator(String value) {
		return value.equals("par") || value.equals("seq");
	}

	private static boolean isBoleanOperator(String value) {
		return value.equals("and") || value.equals("or");
	}

	private static boolean isXConnector(String value) {
		// TODO Auto-generated method stub
		return true;
	}

	private static boolean isQuantity(String value) {
		// TODO Auto-generated method stub
		try{
			Integer t = new Integer(value);
			if( t.intValue() < 0 ) return false;
		}catch (Exception e) {
			// TODO: handle exception
			return value.equals("unbounded");
		}
		return true;
	}
}

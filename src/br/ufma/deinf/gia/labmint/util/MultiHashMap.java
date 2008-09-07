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

package br.ufma.deinf.gia.labmint.util;

import java.util.HashMap;

/*
 * CommonUtil.java
 *
 * Copyright (c) 2001, 2002 Aendvari, Ltd. All Rights Reserved.
 *
 * See the file LICENSE for terms of use.
 *
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;

import java.lang.UnsupportedOperationException;

/**
 * <p>Implements {@link java.util.Map} using a {@link java.util.HashMap}.
 * This Map class allows for multiple values for a single key.</p>
 *
 * <p>Below is how the multi map is setup:</p>
 *
 * <pre>
 *	HASH_MAP
 *	{
 *		HASH_KEY => (
 *				new Collection(
 *					Object, Object, etc...
 *				)
 *		),
 *
 *		HASH_KEY => (
 *				new Collection(
 *					Object, Object, etc...
 *				)
 *		),
 *
 *		etc...
 *	}
 * </pre>
 *
 * <p>NOTE: This class DOES NOT implement the following:
 *	<ul>
 *		<li>public void putAll(Map t)</li>
 *	</ul>
 *
 * @author Scott Milne
 *
 */

public class MultiHashMap implements Map
{
	/** The {@link java.util.HashMap} that this class uses */
	private HashMap map;


	/**
	 * <p>Constructs a new, empty map with a default capacity and load factor, which is 0.75.</p>
	 *
	 */

	public MultiHashMap()
	{
		map = new HashMap();
	}

	/**
	 * <p>Constructs a new, empty map with the specified initial capacity and default load factor, which is 0.75.</p>
	 *
	 */

	public MultiHashMap(int initialCapacity)
	{
		map = new HashMap(initialCapacity);
	}

	/**
	 * <p>Constructs a new, empty map with the specified initial capacity and the specified load factor.</p>
	 *
	 */

	public MultiHashMap(int initialCapacity, float loadFactor)
	{
		map = new HashMap(initialCapacity, loadFactor);
	}

	/**
	 * <p>Determines if the given value matches any of the "collision" values
	 *
	 */

	public boolean containsCollisionValue(Object value)
	{
		// go through each value array checking for the value
		Iterator valuesIterator = values().iterator();
		while (valuesIterator.hasNext())
		{
			Collection keyList = (Collection)valuesIterator.next();
			if (keyList.contains(value) )
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * See {@link java.util.HashMap#put(Object, Object)}.
	 * Places collided values into a Collection instead of replacing previous value.
	 *
	 * @return	Returns the previous Collection.
	 *
	 */

	public Object put(Object key, Object value)
	{
		Object previousValue = null;

		// check to see if there is already a key for this type
		if( map.containsKey(key) )
		{
			// use the ArrayList and add this item to it
			Collection keyList = (Collection)map.get(key);
			keyList.add(value);
			previousValue = map.put(key, keyList);
		}
		else
		{
			// create an ArrayList to place into this key
			map.put( key, new ArrayList() );

			// add the service to the list
			Collection keyList = (Collection)map.get(key);
			keyList.add(value);
			previousValue = map.put(key, keyList);
		}

		return previousValue;
	}

	/**
	 * Get a Collection of the collided values at a give key.
	 *
	 * @return									The Collection at the given key, or null if not found.
	 *
	 */

	public Collection get(String key)
	{
		return (Collection)map.get(key);
	}

	/**
	 * Returns the Collection of "collided" values.
	 */

	public Collection getCollisionList(Object key)
	{
		return (Collection)map.get(key);
	}

	/**
	 * <p>This operation is not supported.</p>
	 *
	 */

	public void putAll(Map t)
		throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}

	/** See {@link java.util.HashMap#size()} */
	public int size()
	{
		int total = 0;
		Iterator keys = map.keySet().iterator();

		while (keys.hasNext())
		{
			String key = (String) keys.next();
			ArrayList list = (ArrayList) map.get(key);
			total += list.size();
		}

		return total;
	}

	/**
	 * Get the size of the collided values for a given key.
	 *
	 */

	public int size(String key)
	{
		ArrayList list = (ArrayList) map.get(key);

		if (list == null)
		{
			return 0;
		}
		else
		{
			return list.size();
		}
	}

	/** See {@link java.util.HashMap#isEmpty()} */
	public boolean isEmpty()
	{
		return map.isEmpty();
	}

	/** See {@link java.util.HashMap#containsValue(Object)} */
	public boolean containsValue(Object value)
	{
		return map.containsValue(value);
	}

	/** See {@link java.util.HashMap#containsKey(Object)} */
	public boolean containsKey(Object key)
	{
		return map.containsKey(key);
	}

	/** See {@link java.util.HashMap#get(Object)} */
	public Object get(Object key)
	{
		return map.get(key);
	}

	/** See {@link java.util.HashMap#remove(Object)} */
	public Object remove(Object key)
	{
		return map.remove(key);
	}

	/** See {@link java.util.HashMap#clear()} */
	public void clear()
	{
		map.clear();
	}

	/** See {@link java.util.HashMap#clone()} */
	public Object clone()
	{
		return map.clone();
	}

	/** See {@link java.util.HashMap#keySet()} */
	public Set keySet()
	{
		return map.keySet();
	}

	/** See {@link java.util.HashMap#values()} */
	public Collection values()
	{
		return map.values();
	}

	/** See {@link java.util.HashMap#entrySet()} */
	public Set entrySet()
	{
		return map.entrySet();
	}
}
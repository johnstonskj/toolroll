/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.utils.fun;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * A set of functions that allow Map-like structures (structures that map 
 * keys to values, including {@link Map}, {@link Dictionary}, and 
 * {@link Properties}) to be dealt with as lists of key/value 
 * {@link Pair}s. These can then be operated on using all the functional
 * list tools in {@link Functional}. 
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 */
public class Maps {

	/**
	 * Return all the items in the {@link Map} as a {@link List} of 
	 * pairs.
	 * 
	 * If <em>map</em> is <code>null</code> then the result of this
	 * function will also be <code>null</code>.
	 * 
	 * @param <K> the type of the keys in <em>map</em>
	 * @param <V> the type of the values in <em>map</em>
	 * @param map a {@link Map} to expand.
	 * @return a {@link List} of pairs.
	 */
	public static <K,V> List<Pair<K,V>> items(final Map<K, V> map) {
		if (map == null) {
			return null;
		}
		List<Pair<K,V>> result = new LinkedList<Pair<K,V>>();
		for (K key : map.keySet()) {
			result.add(new Pair<K,V>(key, map.get(key)));
		}
		return result;
	}
	
	/**
	 * Return all the items in the {@link Dictionary} as a {@link List} of 
	 * pairs.
	 * 
	 * If <em>map</em> is <code>null</code> then the result of this
	 * function will also be <code>null</code>.
	 * 
	 * @param <K> the type of the keys in <em>map</em>
	 * @param <V> the type of the values in <em>map</em>
	 * @param map a {@link Dictionary} to expand.
	 * @return a {@link List} of pairs.
	 */
	public static <K,V> List<Pair<K,V>> items(final Dictionary<K, V> map) {
		if (map == null) {
			return null;
		}
		List<Pair<K,V>> result = new LinkedList<Pair<K,V>>();
		Enumeration<K> keys = map.keys();
		while (keys.hasMoreElements()) {
			final K key = keys.nextElement();
			result.add(new Pair<K,V>(key, map.get(key)));
		}
		return result;
	}	

	/**
	 * Return all the items in the {@link Properties} as a {@link List} of 
	 * pairs.
	 * 
	 * If <em>map</em> is <code>null</code> then the result of this
	 * function will also be <code>null</code>.
	 * 
	 * @param map a {@link Properties} to expand.
	 * @return a {@link List} of pairs.
	 */
	public static List<Pair<String,String>> items(final Properties map) {
		if (map == null) {
			return null;
		}
		List<Pair<String,String>> result = new LinkedList<Pair<String,String>>();
		Enumeration<Object> keys = map.keys();
		while (keys.hasMoreElements()) {
			final String key = (String)keys.nextElement();
			result.add(new Pair<String,String>(key, (String)map.get(key)));
		}
		return result;
	}	

	/**
	 * Combine a {@link List} of {@link Pair}s into a new {@link Map}. Note
	 * that where more than one {@link Pair} contains the same key the 
	 * resulting map is only going to have an entry with the last value
	 * seen.
	 * 
	 * If <em>items</em> is <code>null</code> then the result of this
	 * function will also be <code>null</code>.
	 * 
	 * @param <K> the type of the keys in <em>map</em>
	 * @param <V> the type of the values in <em>map</em>
	 * @param items the list of items to turn into a map
	 * @return a new map containing all the key/value pairs
	 */
	public static <K,V> Map<K,V> combine(final List<Pair<K,V>> items) {
		if (items == null) {
			return null;
		}
		Map<K,V> result = new HashMap<K, V>();
		result = Functional.accumulate(
				items, 
				new Operation<Map<K,V>, Map<K,V>, Pair<K,V>>() {
					@Override
					public Map<K,V> call(Map<K,V> lhs, Pair<K,V> rhs) {
						lhs.put(rhs.getKey(), rhs.getValue());
						return lhs;
					}
				}, 
				result);
		return result;
	}
}

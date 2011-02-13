/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.utils.tests;

import static org.junit.Assert.*;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.johnstonshome.utils.fun.Maps;
import org.johnstonshome.utils.fun.Pair;
import org.junit.Test;

/**
 * Test suite for the {@link Maps} class.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 */
public class MapTests {

	@Test
	public void testMapItems() {
		assertNull(Maps.items((Map<String, String>)null));
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("one", "ONE");
		map.put("two", "TWO");
		map.put("three", "THREE");
		
		List<Pair<String, String>> pairs = Maps.items(map);
		assertEquals(3, pairs.size());
	}

	@Test
	public void testMapCombine() {
		assertNull(Maps.combine(null));
		
		List<Pair<String,String>> list = new LinkedList<Pair<String,String>>();
		list.add(new Pair<String, String>("one", "ONE"));
		list.add(new Pair<String, String>("two", "TWO"));
		list.add(new Pair<String, String>("three", "THREE"));
		
		Map<String, String> map = Maps.combine(list);
		assertEquals(3, map.size());
		assertTrue(map.containsKey("one"));
		assertEquals("TWO", map.get("two"));
	}
	
	@Test
	public void testDictionaryItems() {
		assertNull(Maps.items((Dictionary<String, String>)null));
		
		Dictionary<String, String> map = new Hashtable<String, String>();
		map.put("one", "ONE");
		map.put("two", "TWO");
		map.put("three", "THREE");
		
		List<Pair<String, String>> pairs = Maps.items(map);
		assertEquals(3, pairs.size());
	}

	@Test
	public void testPropertiesItems() {
		assertNull(Maps.items((Properties)null));
		
		Properties map = new Properties();
		map.put("one", "ONE");
		map.put("two", "TWO");
		map.put("three", "THREE");
		
		List<Pair<String, String>> pairs = Maps.items(map);
		assertEquals(3, pairs.size());
	}
}

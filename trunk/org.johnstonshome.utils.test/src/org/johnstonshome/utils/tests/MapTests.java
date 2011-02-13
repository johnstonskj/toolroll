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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		Map<String, String> map = new HashMap<String, String>();
		map.put("one", "ONE");
		map.put("two", "TWO");
		map.put("three", "THREE");
		
		List<Pair<String, String>> pairs = Maps.items(map);
		assertEquals(3, pairs.size());
	}
}

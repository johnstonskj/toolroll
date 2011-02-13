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

import java.util.List;
import java.util.regex.Pattern;

import org.johnstonshome.utils.lang.Strings;
import org.junit.Test;

/**
 * Test suite for the {@link Strings} class.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 */
public class StringTests {

	@Test
	public void testStringEmpty() {
		assertTrue(Strings.isEmpty(null));
		assertTrue(Strings.isEmpty(""));
		assertFalse(Strings.isEmpty(" "));
		assertFalse(Strings.isEmpty("empty"));
	}
	
	@Test
	public void testStringSplitJoin() {
		final List<String> split = Strings.split("a,b,c", ",");
		assertEquals(3, split.size());
		assertEquals("a", split.get(0));
		assertEquals("b", split.get(1));
		assertEquals("c", split.get(2));
		
		final String joined = Strings.join(split, ",");
		assertEquals("a,b,c", joined);
	}

	@Test
	public void testStringSplitOne() {
		final List<String> split = Strings.split("a", ",");
		assertEquals(1, split.size());
		assertEquals("a", split.get(0));
	}

	@Test
	public void testStringRegexSplit() {
		final List<String> split = Strings.split("a,b ,  c,   d", Pattern.compile("\\s*,\\s*"));
		assertEquals(4, split.size());
		assertEquals("a", split.get(0));
		assertEquals("b", split.get(1));
		assertEquals("c", split.get(2));
		assertEquals("d", split.get(3));
	}

	@Test
	public void testStringRegexSplitOne() {
		final List<String> split = Strings.split("a", Pattern.compile("\\s*,\\s*"));
		assertEquals(1, split.size());
		assertEquals("a", split.get(0));
	}
	
	@Test
	public void testExplode() {
		List<Character> exploded = Strings.explode(null);
		assertEquals(0, exploded.size());
		exploded = Strings.explode("");
		assertEquals(0, exploded.size());
		exploded = Strings.explode("ok");
		assertEquals(2, exploded.size());
		exploded = Strings.explode("okie-dokie");
		assertEquals(10, exploded.size());
	}

}

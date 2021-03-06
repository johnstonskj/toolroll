/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.utils.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	public void testStringSplitJoinNull() {
		assertEquals(null, Strings.split(null, ","));
		assertEquals(null, Strings.split("a,b,c", (String)null));
		assertEquals(null, Strings.split(null, (String)null));
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
	public void testStringSplitNull() {
		assertEquals(null, Strings.split("a,b", (String)null));
		assertEquals(null, Strings.split(null, ","));
		assertEquals(null, Strings.split(null, (String)null));
	}
	
	@Test
	public void testStringSplitOne() {
		final List<String> split = Strings.split("a", ",");
		assertEquals(1, split.size());
		assertEquals("a", split.get(0));
	}

	@Test
	public void testStringRegexSplitNull() {
		final Pattern regex = Pattern.compile("\\s*,\\s*");
		
		assertEquals(null, Strings.split(null, regex));
		assertEquals(null, Strings.split("a,b,c", (Pattern)null));
		assertEquals(null, Strings.split(null, (Pattern)null));
	}
	
	@Test
	public void testStringRegexSplit() {
		final Pattern regex = Pattern.compile("\\s*,\\s*");
		
		final List<String> split = Strings.split("a,b ,  c,   d", regex);
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
	public void testExplodeNull() {
		assertEquals(0, Strings.explode(null).size());
	}
	
	@Test
	public void testExplode() {
		List<Character> exploded = Strings.explode("");
		assertEquals(0, exploded.size());
		exploded = Strings.explode("ok");
		assertEquals(2, exploded.size());
		exploded = Strings.explode("okie-dokie");
		assertEquals(10, exploded.size());
	}

	@Test
	public void testReadFileNull() {
		assertEquals(null, Strings.fromFile(null));
	}
	
	@Test
	public void testReadFile() throws Exception {
		final String resource = System.getProperty("user.dir") + "/data/readFileExample.txt"; 
		final File file = new File(resource);
		if (file.exists()) {
			final String read = Strings.fromFile(file);
			assertEquals("Hello\n\tWorld!\n", read);
		} else {
			System.err.println("Error: run tests from project root folder, could not find 'data' directory.");
		}
	}
	
	@Test
	public void testReadReaderNull() {
		assertEquals(null, Strings.fromReader(null));
	}
	
	@Test 
	public void testReadReader() {
		final String original = "Hello\n\tWorld!\n";
		final String read = Strings.fromReader(new StringReader(original));
		assertEquals(original, read);
	}
	
	@Test
	public void testReadStreamNull() {
		assertEquals(null, Strings.fromInputStream(null));
	}
	
	@Test 
	public void testReadStream() {
		final String original = "Hello\n\tWorld!\n";
		final String read = Strings.fromInputStream(new ByteArrayInputStream(original.getBytes()));
		assertEquals(original, read);
	}

	@Test
	public void testInterpolateNull() {
		Map<String, String> values = new HashMap<String, String>();
		assertEquals(null, Strings.interpolate(null, values, "!error!"));
		
		String result = Strings.interpolate("welcome ${name}, to ${thing} world", null, "!missing!");
		assertEquals("welcome !missing!, to !missing! world", result);

		result = Strings.interpolate("welcome ${name}, to ${thing} world", null, null);
		assertEquals("welcome !name!, to !thing! world", result);
	}
	
	@Test
	public void testInterpolate() {
		Map<String, String> values = new HashMap<String, String>();
		values.put("name", "simon");
		values.put("thing", "one");
		
		String result = Strings.interpolate("welcome to my world", values, "!error!");
		assertEquals("welcome to my world", result);
		
		result = Strings.interpolate("welcome ${name}, to ${thing} world", values, "!error!");
		assertEquals("welcome simon, to one world", result);

		result = Strings.interpolate("welcome ${name}, to ${thing} ${bad}", values, "!error!");
		assertEquals("welcome simon, to one !error!", result);
	}
}

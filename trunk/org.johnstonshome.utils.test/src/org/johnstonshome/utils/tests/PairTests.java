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

import org.johnstonshome.utils.lang.Pair;
import org.junit.Test;

/**
 * Test suite for the {@link Pair} class.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 */
public class PairTests {

	@Test
	public void testPairValidation() {
		/*
		 * No Pair should accept a null key, but can accept a null value.
		 */
		try {
			new Pair<String, String>(null, "");
			fail();
		} catch (IllegalArgumentException e) {
			// ignore - test passed
		}
		new Pair<String, String>("", null);
		Pair<String, String> test = new Pair<String, String>("k", "v");
		try {
			test.setKey(null);
			fail();
		} catch (IllegalArgumentException e) {
			// ignore - test passed
		}
		test.setValue(null);
	}
	
	@Test
	public void testPairEquality() {
		Pair<String, String> one = new Pair<String, String>("key", "value");
		Pair<String, String> two = new Pair<String, String>("key", "value2");
		
		assertFalse(one.equals(null));
		assertTrue(one.equals(one));
		assertFalse(one.equals(two));
		
		one.setValue(null);
		assertFalse(one.equals(two));
		one.setValue("value");
		assertFalse(one.equals(two));
		one.setValue(null);
		two.setValue(null);
		assertTrue(one.equals(two));
	}

	@Test
	public void testPairHash() {
		Pair<String, String> one = new Pair<String, String>("key", "value");
		Pair<String, String> two = new Pair<String, String>("key", "value2");
		
		assertTrue(one.hashCode() != two.hashCode());
		
		two.setValue("value");
		assertTrue(one.hashCode() == two.hashCode());
		
		two.setValue(null);
		assertTrue(one.hashCode() != two.hashCode());
		
		one.setValue(null);
		assertTrue(one.hashCode() == two.hashCode());
	}

	@Test
	public void testPairToString() {
		Pair<String, String> one = new Pair<String, String>("key", "value");
		Pair<String, String> two = new Pair<String, String>("key", "value2");
		
		assertFalse(one.toString().equals(two.toString()));
		
		two.setValue("value");
		assertTrue(one.toString().equals(two.toString()));		
	}
}

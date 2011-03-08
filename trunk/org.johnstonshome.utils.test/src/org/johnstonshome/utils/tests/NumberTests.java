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

import org.johnstonshome.utils.lang.Number;
import org.junit.Test;

/**
 * Tes suite for the {@link org.johnstonshome.utils.lang.Number} class
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 */
public class NumberTests {

	@Test
	public void testIsEven() {
		assertTrue(Number.isEvent(0));
		assertTrue(Number.isEvent(2));
		assertTrue(Number.isEvent(8));
		assertTrue(Number.isEvent(88));
		assertTrue(Number.isEvent(800));
		assertFalse(Number.isEvent(1));
		assertFalse(Number.isEvent(3));
		assertFalse(Number.isEvent(9));
		assertFalse(Number.isEvent(89));
		assertFalse(Number.isEvent(801));
	}

	@Test
	public void testIsOdd() {
		assertFalse(Number.isOdd(0));
		assertFalse(Number.isOdd(2));
		assertFalse(Number.isOdd(8));
		assertFalse(Number.isOdd(88));
		assertFalse(Number.isOdd(800));
		assertTrue(Number.isOdd(1));
		assertTrue(Number.isOdd(3));
		assertTrue(Number.isOdd(9));
		assertTrue(Number.isOdd(89));
		assertTrue(Number.isOdd(801));
	}
}

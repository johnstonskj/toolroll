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

import org.johnstonshome.utils.lang.Maybe;
import org.johnstonshome.utils.lang.Maybe.Just;
import org.junit.Test;

/**
 * Tes suite for the {@link org.johnstonshome.utils.lang.Number} class
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 */
public class MaybeTests {

	@Test
	public void testSimple() {
	    assertTrue(Maybe.create(null).isNothing());
        assertFalse(Maybe.create(null).isJust());

        assertFalse(Maybe.create("hello").isNothing());
        assertTrue(Maybe.create("hello").isJust());
        assertEquals("hello", ((Just<String>)Maybe.create("hello")).just());
	}

}

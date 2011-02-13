/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.utils.lang;

import java.util.Collection;

/**
 * A collection of useful, but simple, validation functions. In general they
 * test simple objects for simple conditions and then raise the standard
 * {@link IllegalArgumentException} when values fail to validate.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 */
public class Validate {
	
	/**
	 * Test that the object <em>is</em> <code>null</code>. The value name
	 * is used to provide a meaningful error message within the exception
	 * instance.
	 * 
	 * @param name the name of the value being tested
	 * @param value the value to test, if it <em>is not</em> <code>null</code>
	 *     the function will raise an exception.
	 */
	public static void isNull(String name, Object value) {
		if (value != null) {
			throw new IllegalArgumentException((name == null ? "?" : name) + " != null");
		}
	}

	/**
	 * Test that the object <em>is not</em> <code>null</code>. The value name
	 * is used to provide a meaningful error message within the exception
	 * instance.
	 * 
	 * @param name the name of the value being tested
	 * @param value the value to test, if it <em>is</em> <code>null</code>
	 *     the function will raise an exception.
	 */
	public static void isNotNull(String name, Object value) {
		if (value == null) {
			throw new IllegalArgumentException((name == null ? "?" : name) + " == null");
		}
	}

	/**
	 * Test that the object <em>is not</em> <code>null</code>, also ensures that
	 * there are no <code>null</code> values stored within the collection. The 
	 * value name is used to provide a meaningful error message within the 
	 * exception instance.
	 * 
	 * @param name the name of the value being tested
	 * @param value the value to test, if it <em>is</em> <code>null</code>
	 *     the function will raise an exception.
	 */
	public static void isNotNull(String name, Collection<?> values) {
		if (values == null) {
			throw new IllegalArgumentException((name == null ? "?" : name) + " == null");
		}
		if (values.contains(null)) {
			throw new IllegalArgumentException((name == null ? "?" : name) + " == null");
		}
	}

	/**
	 * Test that the String <em>is not</em> empty (according to the method
	 * {@link Strings#isEmpty(String)}. The value name is used to provide a 
	 * meaningful error message within the exception instance.
	 * 
	 * @param name the name of the value being tested
	 * @param value the value to test, if it <em>is</em> empty
	 *     the function will raise an exception.
	 */
	public static void isNotEmpty(String name, String value) {
		if (Strings.isEmpty(value)) {
			throw new IllegalArgumentException((name == null ? "?" : name) + " EMPTY");
		}
	}
}

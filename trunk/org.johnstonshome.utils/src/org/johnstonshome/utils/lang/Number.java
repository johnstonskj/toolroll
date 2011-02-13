/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.utils.lang;

/**
 * Simple numeric utilities.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 */
public class Number {

	/**
	 * Return <code>true</code> if the number is even
	 * 
	 * @param number the number to test
	 * @return <code>true</code> if the number is even
	 */
	public static boolean isEvent(int number) {
		return ((number % 2) == 0);
	}

	/**
	 * Return <code>true</code> if the number is odd
	 * 
	 * @param number the number to test
	 * @return <code>true</code> if the number is odd
	 */
	public static boolean isOdd(int number) {
		return ((number % 2) != 0);
	}
}

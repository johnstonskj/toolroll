/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.utils.fun;

/**
 * Models a simple function that returns a value with no required parameters.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 * @param <V> the type of the element returned from the function.
 */
public interface ValueFunction<V> {

	/**
	 * This function will generate and return a value, this is not truly a
	 * functional "pure" notion, as it is obvious that this function, unless
	 * it always returns the same value, must rely on side-effects.
	 * 
	 * @return some value.
	 */
	public V call();
}

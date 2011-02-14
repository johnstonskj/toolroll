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
 * Models a function that takes a single element and returns no value to
 * the caller.
 *  
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 * @param <V> the type of the element to be passed into the function.
 */
public interface UnaryProcedure<V> {

	/**
	 * Call this function; this function may not be idempotent.
	 * 
	 * @param value the value to give to this function
	 */
	public void call(V value);
}

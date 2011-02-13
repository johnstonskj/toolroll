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
 * Models a simple function that takes a value and returns a value of the same
 * type.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 * @param <V> the type of the element to be passed into, and returned from, 
 * the function.
 */
public interface Function<V> extends MapFunction<V, V> {

	/**
	 * Call this function; this function <em>should</em> be idempotent
	 * and maintain no state.
	 * 
	 * @param value the value to give to this function
	 * @return the value derived from the input
	 */
	public V call(V value);
}

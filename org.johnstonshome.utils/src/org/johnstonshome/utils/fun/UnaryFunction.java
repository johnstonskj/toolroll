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
 * Models a function that maps from an input element to an output element.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 * @param <V1> the type of the element to be passed into the function.
 * @param <V2> the type of the value returned from the function.
 */
public interface UnaryFunction<V1, V2> {

	/**
	 * Call this function; this function <em>should</em> be idempotent
	 * and maintain no state.
	 * 
	 * @param value the value to give to this function
	 * @return the value mapped from the input
	 */
	public V2 call(V1 value);
}

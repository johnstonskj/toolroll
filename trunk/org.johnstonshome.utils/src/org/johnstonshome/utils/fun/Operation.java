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
 * An operation is applied to two values and returns a new value. There is
 * no assumption that the types for these values are related in any way.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 * @param <V1> the type of the value returned from the function. 
 * @param <V2> the type of the first element to be passed into the function.
 * @param <V3> the type of the first element to be passed into the function.
 */
public interface Operation<V1, V2, V3> {

	/**
	 * Calculate the return value from the two provided values, usually 
	 * denoted as the <em>left-hand-side</em> and <em>right-hand-side</em>.
	 *   
	 * @param lhs first operand
	 * @param rhs second operand
	 * @return the result of the operation
	 */
	public V1 call(V2 lhs, V3 rhs);
}

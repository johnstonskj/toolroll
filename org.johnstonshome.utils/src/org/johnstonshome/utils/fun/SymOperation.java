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
 * A <em>symmetrical</em> operation is applied to two values and returns a new 
 * value; symmetrical as the types for each value must be the same.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 * @param <V> the type for each operation parameter and the resulting value.
 */
public interface SymOperation<V> extends Operation<V,V,V> {

	/**
	 * Calculate the return value from the two provided values, usually 
	 * denoted as the <em>left-hand-side</em> and <em>right-hand-side</em>.
	 *   
	 * @param lhs first operand
	 * @param rhs second operand
	 * @return the result of the operation
	 */
	public V call(V lhs, V rhs);
}

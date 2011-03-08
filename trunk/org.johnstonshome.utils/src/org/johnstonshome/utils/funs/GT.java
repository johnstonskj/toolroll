/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.utils.funs;

import org.johnstonshome.utils.fun.UnaryPredicate;
import org.johnstonshome.utils.lang.Validate;

/**
 * Provides functional Greater Than predicate.
 *  
 * @author Simon Johnston (simon@johnstonshome.org)
 * 
 * @param <V> the value type for the operands
 */
public class GT<V extends Comparable<? super V>> implements UnaryPredicate<V> {

	private V rhs;
	
	/**
	 * Construct a new predicate with a specific value to test against
	 * 
	 * @param rhs the value to test against
	 */
	public GT(V rhs) {
		Validate.isNotNull("rhs", rhs);
		this.rhs = rhs;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.johnstonshome.utils.fun.UnaryFunction#call(java.lang.Object)
	 */
	@Override
	public Boolean call(V lhs) {
		return Boolean.valueOf(lhs.compareTo(this.rhs) > 0);
	}

}

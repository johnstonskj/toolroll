/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.utils.funs;

import java.util.Set;

import org.johnstonshome.utils.fun.UnaryPredicate;
import org.johnstonshome.utils.lang.Validate;

/**
 * Test whether a value is in a pre-defined set of values
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 * 
 * @param <V> the value type for the operands
 */
public class InSet<V> implements UnaryPredicate<V> {

	private Set<V> set;
	
	/**
	 * Construct a new predicate with a specific value to test against
	 * 
	 * @param rhs the value to test against
	 */
	public InSet(Set<V> rhs) {
		Validate.isNotNull("rhs", rhs);
		this.set = rhs;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.johnstonshome.utils.fun.UnaryFunction#call(java.lang.Object)
	 */
	@Override
	public Boolean call(V lhs) {
		return Boolean.valueOf(this.set.contains(lhs));
	}

}

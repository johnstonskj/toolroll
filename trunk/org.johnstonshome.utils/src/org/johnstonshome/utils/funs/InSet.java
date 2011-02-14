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
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 */
public class InSet<V> implements UnaryPredicate<V> {

	private Set<V> set;
	
	public InSet(Set<V> rhs) {
		Validate.isNotNull("rhs", rhs);
		this.set = rhs;
	}
	
	@Override
	public Boolean call(V lhs) {
		return this.set.contains(lhs);
	}

}

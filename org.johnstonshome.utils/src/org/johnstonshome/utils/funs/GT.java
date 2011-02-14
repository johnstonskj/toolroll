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
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 */
public class GT<V extends Comparable<? super V>> implements UnaryPredicate<V> {

	private V rhs;
	
	public GT(V rhs) {
		Validate.isNotNull("rhs", rhs);
		this.rhs = rhs;
	}
	
	@Override
	public Boolean call(V lhs) {
		return (lhs.compareTo(this.rhs) > 0);
	}

}

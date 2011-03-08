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
 * Provides a predicate to test a string against a pre-defined regular expression
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 */
public class Matches implements UnaryPredicate<String> {

	private String regex;
	
	/**
	 * Construct a new predicate with a specific value to test against
	 * 
	 * @param regex the regular expression to test against
	 */
	public Matches(String regex) {
		Validate.isNotNull("regex", regex);
		this.regex = regex;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.johnstonshome.utils.fun.UnaryFunction#call(java.lang.Object)
	 */
	@Override
	public Boolean call(String lhs) {
		return Boolean.valueOf(lhs.matches(this.regex));
	}

}

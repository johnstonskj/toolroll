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
public class Matches implements UnaryPredicate<String> {

	private String regex;
	
	public Matches(String regex) {
		Validate.isNotNull("regex", regex);
		this.regex = regex;
	}
	
	@Override
	public Boolean call(String lhs) {
		return lhs.matches(this.regex);
	}

}

package org.johnstonshome.utils.funs;

import org.johnstonshome.utils.fun.BinaryFunction;

/**
 * Simple binary function (operator) that provides addition for Integer values.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 */
public class Addition implements BinaryFunction<Integer, Integer, Integer> {

	@Override
	public Integer call(Integer lhs, Integer rhs) {
		return Integer.valueOf((lhs.intValue() + rhs.intValue()));
	}

}

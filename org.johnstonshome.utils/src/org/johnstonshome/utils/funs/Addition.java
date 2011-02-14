package org.johnstonshome.utils.funs;

import org.johnstonshome.utils.fun.BinaryFunction;

public class Addition implements BinaryFunction<Integer, Integer, Integer> {

	@Override
	public Integer call(Integer lhs, Integer rhs) {
		return lhs + rhs;
	}

}

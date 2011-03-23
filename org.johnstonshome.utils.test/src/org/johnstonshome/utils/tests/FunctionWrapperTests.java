/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.utils.tests;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.johnstonshome.utils.fun.PartialBinaryFunction;
import org.johnstonshome.utils.fun.Functional;
import org.johnstonshome.utils.fun.UnaryFunction;
import org.johnstonshome.utils.fun.BinaryFunction;
import org.johnstonshome.utils.funs.Map;
import org.junit.Test;

/**
 * Test suite for the {@link Functional} class.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 */
public class FunctionWrapperTests {
	
	@Test
	public void testCurriedOperation() {
		BinaryFunction<Integer, Integer, Integer> plus = new BinaryFunction<Integer, Integer, Integer>() {
			@Override
			public Integer call(Integer lhs, Integer rhs) {
				return Integer.valueOf(lhs.intValue() + rhs.intValue());
			}
		};
		
		UnaryFunction<Integer, Integer> plus2 = 
			new PartialBinaryFunction<Integer, Integer, Integer>(plus, Integer.valueOf(2));
		
		assertEquals(Integer.valueOf(4), plus2.call(Integer.valueOf(2)));
		assertEquals(Integer.valueOf(102), plus2.call(Integer.valueOf(100)));
	}

	@Test
	public void testCurriedMap() {
		UnaryFunction<Integer, Integer> square = new UnaryFunction<Integer, Integer>() {
			@Override
			public Integer call(Integer value) {
				return Integer.valueOf(value.intValue() * value.intValue());
			}
		};
		
		List<Integer> input = new LinkedList<Integer>();
		input.add(Integer.valueOf(2));
		input.add(Integer.valueOf(4));
		input.add(Integer.valueOf(6));
		input.add(Integer.valueOf(8));
		
		List<Integer> output = Map.curry(square).call(input);
		
		assertEquals(4, output.size());
		assertEquals(Integer.valueOf(4), output.get(0));
		assertEquals(Integer.valueOf(16), output.get(1));
		assertEquals(Integer.valueOf(36), output.get(2));
		assertEquals(Integer.valueOf(64), output.get(3));
	}

}

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

import org.johnstonshome.utils.fun.CurriedOperation;
import org.johnstonshome.utils.fun.Functional;
import org.johnstonshome.utils.fun.MapFunction;
import org.johnstonshome.utils.fun.Operation;
import org.johnstonshome.utils.funwrap.Map;
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
		Operation<Integer, Integer, Integer> plus = new Operation<Integer, Integer, Integer>() {
			@Override
			public Integer call(Integer lhs, Integer rhs) {
				return lhs + rhs;
			}
		};
		
		MapFunction<Integer, Integer> plus2 = 
			new CurriedOperation<Integer, Integer, Integer>(plus, 2);
		
		assertEquals(Integer.valueOf(4), plus2.call(2));
		assertEquals(Integer.valueOf(102), plus2.call(100));
	}

	@Test
	public void testCurriedMap() {
		MapFunction<Integer, Integer> square = new MapFunction<Integer, Integer>() {
			@Override
			public Integer call(Integer value) {
				return value * value;
			}
		};
		
		List<Integer> input = new LinkedList<Integer>();
		input.add(2);
		input.add(4);
		input.add(6);
		input.add(8);
		
		List<Integer> output = Map.curry(square).call(input);
		
		assertEquals(4, output.size());
		assertEquals(Integer.valueOf(4), output.get(0));
		assertEquals(Integer.valueOf(16), output.get(1));
		assertEquals(Integer.valueOf(36), output.get(2));
		assertEquals(Integer.valueOf(64), output.get(3));
	}

}

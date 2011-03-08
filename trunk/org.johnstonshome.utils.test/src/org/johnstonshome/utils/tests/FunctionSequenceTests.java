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

import org.johnstonshome.utils.fun.UnaryFunction;
import org.johnstonshome.utils.fun.UnaryFunctionSequence;
import org.johnstonshome.utils.funs.GT;
import org.junit.Test;

/**
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 */
public class FunctionSequenceTests {

	@Test
	public void testSimpleSequence() {
		UnaryFunctionSequence<String, String> sequence = new UnaryFunctionSequence<String, String>();
		sequence.then(new UnaryFunction<String, Integer>() {
			@Override
			public Integer call(String value) {
				return Integer.valueOf(value);
			}
		}).then(new GT<Integer>(Integer.valueOf(10))
		).then(new UnaryFunction<Boolean, String>() {
			@Override
			public String call(Boolean value) {
				return value.toString();
			}
		});
		String output1 = sequence.call("101");
		assertEquals("true", output1);
		String output2 = sequence.call("1");
		assertEquals("false", output2);
	}
}

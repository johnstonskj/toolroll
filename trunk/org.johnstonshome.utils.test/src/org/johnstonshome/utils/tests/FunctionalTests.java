/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.utils.tests;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import static org.junit.Assert.*;

import org.johnstonshome.utils.fun.Functional;
import org.johnstonshome.utils.fun.MapFunction;
import org.johnstonshome.utils.fun.SymOperation;
import org.johnstonshome.utils.fun.VoidFunction;
import org.johnstonshome.utils.lang.Strings;
import org.junit.Test;

/**
 * Test suite for the {@link Functional} class.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 */
public class FunctionalTests {
	
	@Test
	public void testForEach() {
		final List<String> test = Strings.split("1,b,c,d,e", ",");
		
		Functional.forEach(test, new VoidFunction<String>() {
			@Override
			public void call(String value) {
				// TODO: better, need to validate
				System.out.println("*" + value + "*");
			}
		});
	}

	@Test
	public void testMap() {
		final List<String> test = Strings.split("a,bb,ccc,dddd,eeeee", ",");
		
		final List<Integer> results = Functional.map(test, new MapFunction<String, Integer>() {
			@Override
			public Integer call(String value) {
				return value.length();
			}
		});
		assertEquals(5, results.size());
		for (int i = 0; i < results.size(); i++) {
			assertEquals(i+1, results.get(i).intValue()); 
		}
	}
	
	@Test
	public void testAny() {		
		List<String> test = Strings.split("1,b,c,d,e", ",");
		
		final boolean result = Functional.any(test, new MapFunction<String, Boolean>() {
			@Override
			public Boolean call(String value) {
				return value.equals("d");
			}
		});
		assertTrue(result);

		final boolean result2 = Functional.any(test, new MapFunction<String, Boolean>() {
			@Override
			public Boolean call(String value) {
				return value.equals("x");
			}
		});
		assertFalse(result2);
	}

	@Test
	public void testAll() {		
		List<String> test = Strings.split("d,d,d,d,d", ",");
		
		final boolean result = Functional.every(test, new MapFunction<String, Boolean>() {
			@Override
			public Boolean call(String value) {
				return value.equals("d");
			}
		});
		assertTrue(result);

		final boolean result2 = Functional.every(test, new MapFunction<String, Boolean>() {
			@Override
			public Boolean call(String value) {
				return value.equals("x");
			}
		});
		assertFalse(result2);
	}
	
	@Test
	public void testFilter() {		
		List<String> test = Strings.split("d,b,c,d,e", ",");
		
		final Collection<String> result = Functional.filter(test, new MapFunction<String, Boolean>() {
			@Override
			public Boolean call(String value) {
				return value.equals("d");
			}
		});
		assertEquals(2, result.size());
	}

	@Test
	public void testFoldl() {
		Integer[] array = {1, 2, 3, 4, 5};
		List<Integer> test = Arrays.asList(array);
		
		final Integer sum = Functional.foldLeft(test, new SymOperation<Integer>() {
			@Override
			public Integer call(Integer lhs, Integer rhs) {
				return lhs + rhs;
			}
		});
		assertEquals(15, sum.intValue());
	}

	@Test
	public void testFoldr() {
		String[] array = {"o", "l", "l", "e", "h"};
		List<String> test = Arrays.asList(array);
		
		final String sum = Functional.foldRight(test, new SymOperation<String>() {
			@Override
			public String call(String lhs, String rhs) {
				return lhs + rhs;
			}
		});
		assertEquals("hello", sum);
	}
	
	@Test
	public void testZip() {
		Integer[] array1 = {1, 2, 3, 4};
		List<Integer> list1 = Arrays.asList(array1);
		Integer[] array2 = {5, 6, 7};
		List<Integer> list2 = Arrays.asList(array2);
		List<List<Integer>> input = new LinkedList<List<Integer>>();
		input.add(list1);
		input.add(list2);
		
		final List<List<Integer>> result = Functional.zip(input);
		assertEquals(3, result.size());
		for (List<Integer> collection : result) {
			assertEquals(2, collection.size());
		}
	}
	
	@Test
	public void testReverse() {
		String[] array = {"o", "l", "l", "e", "h"};
		List<String> test = Arrays.asList(array);
		/*
		 * shows foldr(l) == foldl(reverse(l))
		 */
		List<String> result = Functional.reverse(test);
		final String sum = Functional.foldLeft(result, new SymOperation<String>() {
			@Override
			public String call(String lhs, String rhs) {
				return lhs + rhs;
			}
		});
		assertEquals("hello", sum);
	}
}

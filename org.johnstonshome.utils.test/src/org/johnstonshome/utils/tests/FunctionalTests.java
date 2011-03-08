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

import org.johnstonshome.utils.fun.BinaryFunction;
import org.johnstonshome.utils.fun.Functional;
import org.johnstonshome.utils.fun.UnaryFunction;
import org.johnstonshome.utils.fun.UnaryPredicate;
import org.johnstonshome.utils.fun.UnaryProcedure;
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
	public void testForEachNull() {		
		Functional.forEach(null, new UnaryProcedure<String>() {
			@Override
			public void call(String value) {
				// do nothing
			}
		});

		final List<String> test = Strings.split("1,b,c,d,e", ",");
		Functional.forEach(test, null);
	}
	
	@Test
	public void testForEach() {
		final List<String> test = Strings.split("1,b,c,d,e", ",");
		
		Functional.forEach(test, new UnaryProcedure<String>() {
			@Override
			public void call(String value) {
				// TODO: better, need to validate
				System.out.println("*" + value + "*");
			}
		});
	}

	@Test
	public void testMapNull() {
	    Collection<Integer> results = Functional.map(null, new UnaryFunction<String, Integer>() {
			@Override
			public Integer call(String value) {
				return Integer.valueOf(value.length());
			}
		});
		assertEquals(null, results);
		final List<String> test = Strings.split("a,bb,ccc,dddd,eeeee", ",");
		results = Functional.map(test, null); 
		assertEquals(null, results);
	}
	
	@Test
	public void testMap() {
		final List<String> test = Strings.split("a,bb,ccc,dddd,eeeee", ",");
		
		final List<Integer> results = Functional.map(test, new UnaryFunction<String, Integer>() {
			@Override
			public Integer call(String value) {
				return Integer.valueOf(value.length());
			}
		});
		assertEquals(5, results.size());
		for (int i = 0; i < results.size(); i++) {
			assertEquals(i+1, results.get(i).intValue()); 
		}
	}

	@Test
	public void testAnyNull() {		
		boolean result = Functional.any(null, new UnaryPredicate<String>() {
			@Override
			public Boolean call(String value) {
				return Boolean.valueOf(value.equals("d"));
			}
		});
		assertFalse(result);
		
		final List<String> test = Strings.split("1,b,c,d,e", ",");
		result = Functional.any(test, null); 
		assertFalse(result);
	}
	
	@Test
	public void testAny() {		
		final List<String> test = Strings.split("1,b,c,d,e", ",");
		
		final boolean result = Functional.any(test, new UnaryPredicate<String>() {
			@Override
			public Boolean call(String value) {
				return Boolean.valueOf(value.equals("d"));
			}
		});
		assertTrue(result);

		final boolean result2 = Functional.any(test, new UnaryPredicate<String>() {
			@Override
			public Boolean call(String value) {
				return Boolean.valueOf(value.equals("x"));
			}
		});
		assertFalse(result2);
	}

	@Test
	public void testAllNull() {		
		boolean result = Functional.every(null, new UnaryPredicate<String>() {
			@Override
			public Boolean call(String value) {
				return Boolean.valueOf(value.equals("d"));
			}
		});
		assertFalse(result);	

		final List<String> test = Strings.split("d,d,d,d,d", ",");
		result = Functional.every(test, null);
		assertFalse(result);	
	}
	
	@Test
	public void testAll() {		
		final List<String> test = Strings.split("d,d,d,d,d", ",");
		
		final boolean result = Functional.every(test, new UnaryPredicate<String>() {
			@Override
			public Boolean call(String value) {
				return Boolean.valueOf(value.equals("d"));
			}
		});
		assertTrue(result);

		final boolean result2 = Functional.every(test, new UnaryPredicate<String>() {
			@Override
			public Boolean call(String value) {
				return Boolean.valueOf(value.equals("x"));
			}
		});
		assertFalse(result2);
	}

	@Test
	public void testFilterNull() {		
		Collection<String> result = Functional.filter(null, new UnaryPredicate<String>() {
			@Override
			public Boolean call(String value) {
				return Boolean.valueOf(value.equals("d"));
			}
		});
		assertEquals(null, result);
		
		final List<String> test = Strings.split("d,b,c,d,e", ",");
		result = Functional.filter(test, null);
		assertEquals(null, result);
	}
	
	@Test
	public void testFilter() {		
		final List<String> test = Strings.split("d,b,c,d,e", ",");
		
		final Collection<String> result = Functional.filter(test, new UnaryPredicate<String>() {
			@Override
			public Boolean call(String value) {
				return Boolean.valueOf(value.equals("d"));
			}
		});
		assertEquals(2, result.size());
	}

	@Test
	public void testFoldlNull() {
		Integer sum = Functional.foldLeft(null, new BinaryFunction<Integer,Integer,Integer>() {
			@Override
			public Integer call(Integer lhs, Integer rhs) {
				return Integer.valueOf(lhs.intValue() + rhs.intValue());
			}
		});
		assertEquals(null, sum);
		
		final Integer[] array = {Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3),
				Integer.valueOf(4), Integer.valueOf(5)};
		final List<Integer> test = Arrays.asList(array);
		sum = Functional.foldLeft(test, null);
		assertEquals(null, sum);
	}
	
	@Test
	public void testFoldl() {
		final Integer[] array = {Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3),
				Integer.valueOf(4), Integer.valueOf(5)};
		final List<Integer> test = Arrays.asList(array);
		
		final Integer sum = Functional.foldLeft(test, new BinaryFunction<Integer,Integer,Integer>() {
			@Override
			public Integer call(Integer lhs, Integer rhs) {
				return Integer.valueOf(lhs.intValue() + rhs.intValue());
			}
		});
		assertEquals(15, sum.intValue());
	}

	@Test
	public void testFoldrNull() {
		String sum = Functional.foldRight(null, new BinaryFunction<String,String,String>() {
			@Override
			public String call(String lhs, String rhs) {
				return lhs + rhs;
			}
		});
		assertEquals(null, sum);
		
		final String[] array = {"o", "l", "l", "e", "h"};
		final List<String> test = Arrays.asList(array);
		
		sum = Functional.foldRight(test, null);
		assertEquals(null, sum);
	}
	
	@Test
	public void testFoldr() {
		final String[] array = {"o", "l", "l", "e", "h"};
		final List<String> test = Arrays.asList(array);
		
		final String sum = Functional.foldRight(test, new BinaryFunction<String,String,String>() {
			@Override
			public String call(String lhs, String rhs) {
				return lhs + rhs;
			}
		});
		assertEquals("hello", sum);
	}
	
	@Test
	public void testZipNull() {
		assertEquals(null, Functional.zip(null));
		
		final List<List<Integer>> input = new LinkedList<List<Integer>>();
		input.add(null);
		input.add(null);
		assertEquals(2, input.size());
		assertEquals(null, Functional.zip(input));
	}
	
	@Test
	public void testZip() {
		final Integer[] array1 = {Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4)};
		final List<Integer> list1 = Arrays.asList(array1);
		final Integer[] array2 = {Integer.valueOf(5), Integer.valueOf(6), Integer.valueOf(7)};
		final List<Integer> list2 = Arrays.asList(array2);
		final List<List<Integer>> input = new LinkedList<List<Integer>>();
		input.add(list1);
		input.add(list2);
		
		final List<List<Integer>> result = Functional.zip(input);
		assertEquals(3, result.size());
		for (List<Integer> collection : result) {
			assertEquals(2, collection.size());
		}
	}

	@Test
	public void testReverseNull() {
		assertEquals(null, Functional.reverse(null));
	}
	
	@Test
	public void testReverse() {
		final String[] array = {"o", "l", "l", "e", "h"};
		final List<String> test = Arrays.asList(array);
		/*
		 * shows foldr(l) == foldl(reverse(l))
		 */
		final List<String> result = Functional.reverse(test);
		final String sum = Functional.foldLeft(result, new BinaryFunction<String,String,String>() {
			@Override
			public String call(String lhs, String rhs) {
				return lhs + rhs;
			}
		});
		assertEquals("hello", sum);
	}

    @Test
    public void testFlattenNull() {
        assertEquals(null, Functional.flatten(null));
    }
    
    @Test
    public void testFlatten() {
        final Integer[] array1 = {Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4)};
        final List<Integer> list1 = Arrays.asList(array1);
        final Integer[] array2 = {Integer.valueOf(5), Integer.valueOf(6), Integer.valueOf(7)};
        final List<Integer> list2 = Arrays.asList(array2);
        final List<Iterable<Integer>> input = new LinkedList<Iterable<Integer>>();
        input.add(list1);
        input.add(list2);
        
        List<Integer> result = Functional.flatten(input);
        assertEquals(7, result.size());
    }
}

/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
/**
 * Functional utilities for iterating over lists, basically provides common
 * functions provided in many true functional languages. Additionally the 
 * {@link org.johnstonshome.utils.fun.Maps} class provides the ability to 
 * convert {@link java.util.Map} instances to and from a {@link java.util.List}
 * of {@link org.johnstonshome.utils.lang.Pair}s. This allows maps (as well as
 * dictionaries and properties) to be dealt with as lists and therefore the
 * rest of the {@link org.johnstonshome.utils.fun.Functional} tools to 
 * be used.
 * 
 * <h3>Example</h3>
 * 
 * <pre>
 * final List<String> test = Strings.split("a,bb,ccc,dddd,eeeee", ",");
 * 
 * final List<Integer> results = Functional.map(test, new UnaryFunction<String, Integer>() {
 *     @Override
 *     public Integer call(String value) {
 *         return value.length();
 *     }
 * });
 * assertEquals(5, results.size());
 * for (int i = 0; i < results.size(); i++) {
 *     assertEquals(i+1, results.get(i).intValue()); 
 * }
 * </pre>
 * 
 */
package org.johnstonshome.utils.fun;


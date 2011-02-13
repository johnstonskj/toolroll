/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.utils.fun;

import org.johnstonshome.utils.lang.Validate;

/**
 * This class allows two functions to be combined into a new function. The
 * two functions have to be type compatible in that the first function
 * takes a value of the same type that is returned from the second. 
 * 
 * <pre>
 * public Character x(Integer i);
 * public Boolean y(Character c);
 * 
 * MapFunction<Boolean, Integer> combined =
 *     new CombineFunction<Boolean,Character, Integer>(x, y);
 *                
 * Boolean v = combined(10);
 * 
 * // combined(10) == y(x(10))
 * </pre>
 * 
 * Note that the new combined function is itself a MapFunction and so 
 * can be combined with other functions in the same manner.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 * @param <V1> the return type of the first function
 * @param <V2> the parameter type of the first function, and the return
 *     type of the second function
 * @param <V3> the parameter type of the second function.
 */
public class CombineFunction<V1, V2, V3> implements MapFunction<V1, V3> {

	private MapFunction<V1, V2> first;
	private MapFunction<V2, V3> second;
	
	/**
	 * Construct a new function by combining the two passed functions.
	 * 
	 * @param first the first function
	 * @param second the second function
	 */
	public CombineFunction(MapFunction<V1, V2> first, MapFunction<V2, V3> second) {
		Validate.isNotNull("first", first);
		Validate.isNotNull("second", second);
		this.first = first;
		this.second = second;
	}
	
	/**
	 * Call the combined functions in order, note that this is also a new
	 * map function.
	 * 
	 * @param value the value to give to each function in turn
	 * @return the value resulting from the combined call.
	 */
	@Override
	public V3 call(V1 value) {
		return this.second.call(this.first.call(value));
	}
}

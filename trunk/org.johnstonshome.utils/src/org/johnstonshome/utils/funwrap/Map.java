/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.utils.funwrap;

import java.util.Collection;
import java.util.List;

import org.johnstonshome.utils.fun.CurriedOperation;
import org.johnstonshome.utils.fun.Functional;
import org.johnstonshome.utils.fun.MapFunction;
import org.johnstonshome.utils.fun.Operation;

/**
 * A wrapper class around the function {@link Functional#map(Collection, MapFunction)}
 * which allows this to be treated as a map function and can therefore be more 
 * readily composed using Promises.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 * @param <V1> the element type for each element in the input collection
 * @param <V2> the element type for each element in the output collection
 */
public class Map<V1, V2> implements Operation<List<V2>, Collection<V1>, MapFunction<V1, V2>> {

	/*
	 * (non-Javadoc)
	 * @see org.johnstonshome.utils.fun.Operation#call(java.lang.Object, java.lang.Object)
	 */
	@Override
	public List<V2> call(final Collection<V1> collection, final MapFunction<V1, V2> function) {
		return Functional.map(collection, function);
	}
	
	/**
	 * Curry this Operation into a MapFunction by storing the original MapFunction
	 * used by map to call each element in the list.
	 * 
	 * @param <V1> the element type for each element in the input collection
	 * @param <V2> the element type for each element in the output collection
	 * @param function the function to call for each element in the 
	 *     collection, its result is stored in the returned collection.
	 * @return a curried form of this wrapper with the function stored
	 */
	public static <V1,V2> CurriedOperation<List<V2>, Collection<V1>, MapFunction<V1, V2>> 
		curry(MapFunction<V1, V2> function) {
		return new CurriedOperation<List<V2>, Collection<V1>, MapFunction<V1, V2>>(new Map<V1, V2>(), function);
	}

}

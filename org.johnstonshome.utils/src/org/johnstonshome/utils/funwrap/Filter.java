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

import org.johnstonshome.utils.fun.CurriedOperation;
import org.johnstonshome.utils.fun.Functional;
import org.johnstonshome.utils.fun.MapFunction;
import org.johnstonshome.utils.fun.Operation;

/**
 * A wrapper class around the function {@link Functional#filter(Collection, MapFunction)}
 * which allows this to be treated as a map function and can therefore be more 
 * readily composed using Promises.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 * @param <V> the element type for each element in the input collection
 */
public class Filter<V> implements Operation<Collection<V>, Collection<V>, MapFunction<V, Boolean>> {

	/*
	 * (non-Javadoc)
	 * @see org.johnstonshome.utils.fun.Operation#call(java.lang.Object, java.lang.Object)
	 */
	@Override
	public Collection<V> call(final Collection<V> collection, final MapFunction<V, Boolean> function) {
		return Functional.filter(collection, function);
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
	public static <V> CurriedOperation<Collection<V>, Collection<V>, MapFunction<V, Boolean>> 
		curry(MapFunction<V, Boolean> function) {
		return new CurriedOperation<Collection<V>, Collection<V>, MapFunction<V, Boolean>>(new Filter<V>(), function);
	}

}

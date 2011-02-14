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
 * A wrapper class around the function {@link Functional#every(Collection, MapFunction)}
 * which allows this to be treated as a map function and can therefore be more 
 * readily composed using Promises.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 * @param <V> the element type for each element in the input collection
 */
public class Every<V> implements Operation<Boolean, Collection<V>, MapFunction<V, Boolean>> {

	/*
	 * (non-Javadoc)
	 * @see org.johnstonshome.utils.fun.Operation#call(java.lang.Object, java.lang.Object)
	 */
	@Override
	public Boolean call(Collection<V> collection, MapFunction<V, Boolean> function) {
		return Functional.every(collection, function);
	}

	/**
	 * Curry this Operation into a MapFunction by storing the original MapFunction
	 * used by map to call each element in the list.
	 * 
	 * @param <V> the element type for each element in the input collection
	 * @param function the function to call for each element in the 
	 *     collection.
	 * @return a curried form of this wrapper with the function stored
	 */
	public static <V> CurriedOperation<Boolean, Collection<V>, MapFunction<V, Boolean>>
		curry(MapFunction<V, Boolean> function) {
		return new CurriedOperation<Boolean, Collection<V>, MapFunction<V, Boolean>>(new Every<V>(), function);
	}
}

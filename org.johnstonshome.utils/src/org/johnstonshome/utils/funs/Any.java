/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.utils.funs;

import java.util.Collection;

import org.johnstonshome.utils.fun.PartialBinaryFunction;
import org.johnstonshome.utils.fun.Functional;
import org.johnstonshome.utils.fun.UnaryFunction;
import org.johnstonshome.utils.fun.BinaryFunction;
import org.johnstonshome.utils.fun.UnaryPredicate;

/**
 * A wrapper class around the function {@link Functional#any(Collection, UnaryFunction)}
 * which allows this to be treated as a map function and can therefore be more 
 * readily composed using Promises.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 * @param <V> the element type for each element in the input collection
 */
public class Any<V> implements BinaryFunction<Boolean, Collection<V>, UnaryPredicate<V>> {

	/*
	 * (non-Javadoc)
	 * @see org.johnstonshome.utils.fun.BinaryFunction#call(java.lang.Object, java.lang.Object)
	 */
	@Override
	public Boolean call(Collection<V> collection, UnaryPredicate<V> function) {
		return Boolean.valueOf(Functional.any(collection, function));
	}

	/**
	 * Curry this BinaryFunction into a UnaryFunction by storing the original UnaryFunction
	 * used by map to call each element in the list.
	 * 
	 * @param <V> the element type for each element in the input collection
	 * @param function the function to call for each element in the 
	 *     collection.
	 * @return a curried form of this wrapper with the function stored
	 */
	public static <V> PartialBinaryFunction<Boolean, Collection<V>, UnaryPredicate<V>>
		curry(UnaryPredicate<V> function) {
		return new PartialBinaryFunction<Boolean, Collection<V>, UnaryPredicate<V>>(new Any<V>(), function);
	}
}

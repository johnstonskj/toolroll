/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.utils.fun;

/**
 * Models a function that maps from an input element to a Boolean value.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 * @param <V> the type of the element to be passed into the function.
 */
public interface UnaryPredicate<V> extends UnaryFunction<V, Boolean> {
	// nothing further required
}

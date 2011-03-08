/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.utils.fun;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Provides a set of functional programming tools for list manipulation. 
 * These allow clients to iterate over, filter and construct new lists in a
 * functional manner. The implementations here provide for manipulation of
 * in-memory lists modeled using the standard Java 
 * {@link java.util.Collection} and {@link java.util.List}. In general the
 * functions provided here concern iteration and construction, they do not
 * deal with more structural issues such as addition/deletion and sorting
 * or searching which are already provided by the Java APIs.
 * 
 * In functional programming there are a some common names for some of these
 * operations, but for example the <em>foldl</em> operations is sometimes
 * known as <em>reduce</em> (Python) or <em>accumulate</em> (C#). The names
 * used here are therefore arbitrary, but in general follow the names from
 * the Scheme List Library <a href="http://srfi.schemers.org/srfi-1/srfi-1.html">
 * SRFI</a>.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 */
public class Functional {

	/**
	 * Iterate over the collection <em>collection</em> in any order, for each 
	 * element the function <em>function</em> will be called passing in the 
	 * element as a parameter. Note that as <em>function</em> has no return 
	 * value the result of any computation is entirely a side-effect.
	 * 
	 * If either <em>collection</em> or <em>function</em> is <code>null</code>
	 * then the function simply ignores both and will not attempt to iterate
	 * over the collection.
	 * 
	 * @param <V> the element type for each element in the input collection 
	 * @param collection the collection of elements, note no guarantee of order
	 *     is implied by {@link java.util.Collection}.
	 * @param function the function to call for each element in the collection,
	 *     note that {@link UnaryProcedure} has no return type.
	 */
	public static <V> void forEach(final Iterable<V> collection, final UnaryProcedure<V> function) {
		if (collection != null && function != null) {
			for (final V value : collection) {
				function.call(value);
			}
		}
	}

	/**
	 * Map each element in the input collection to a corresponding element in 
	 * the returned output collection. In essence this iterates over the 
	 * collection <em>collection</em> in any order, for each element the 
	 * function <em>function</em> will be called passing in the element as a 
	 * parameter. The result of each function call is placed into a new 
	 * collection whose order therefore matches the order of the initial list.  
	 * 
	 * If either <em>collection</em> or <em>function</em> is <code>null</code>
	 * then the function will return <code>null</code> immediately.
	 * 
	 * @param <V1> the element type for each element in the input collection
	 * @param <V2> the element type for each element in the output collection
	 * @param collection the collection of elements, note no guarantee of order
	 *     is implied by {@link java.util.Collection}.
	 * @param function the function to call for each element in the 
	 *     collection, its result is stored in the returned collection.
	 * @return a new collection with one element for each element in the input
	 *     collection.
	 */
	public static <V1, V2> List<V2> map(final Iterable<V1> collection, final UnaryFunction<V1, V2> function) {
		if (collection == null || function == null) {
			return null;
		}
		List<V2> results = new LinkedList<V2>();
		for (final V1 value : collection) {
			results.add(function.call(value));
		}
		return results;
	}

	/**
	 * Return <code>true</code> if any element in the input collection satisfies 
	 * the predicate (function that takes an element and returns a boolean). 
	 *  
	 * If either <em>collection</em> or <em>function</em> is <code>null</code>
	 * then the function will return <code>null</code> immediately.
	 * 
	 * @param <V> the element type for each element in the input collection
	 * @param collection the element type for each element in the output collection
	 *     is implied by {@link java.util.Collection}.
	 * @param predicate the function to call for each element in the collection,
	 *     returning <code>true</code> if the element matches the predicate. 
	 * @return <code>true</code> if any element in the collection satisifies the
	 *     predicate, else <code>false</code>.
	 */
	public static <V> boolean any(final Iterable<V> collection, final UnaryPredicate<V> predicate) {
		if (collection == null || predicate == null) {
			return false;
		}
		boolean result = false;
		for (V value : collection) {
			if (predicate.call(value).booleanValue()) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	/**
	 * Return <code>true</code> if each and eveer element in the input collection 
	 * satisfies the predicate (function that takes an element and returns a 
	 * boolean). 
	 *  
	 * If either <em>collection</em> or <em>function</em> is <code>null</code>
	 * then the function will return <code>null</code> immediately.
	 * 
	 * @param <V> the element type for each element in the input collection
	 * @param collection the element type for each element in the output collection
	 * @param collection the collection of elements, note no guarantee of order
	 *     is implied by {@link java.util.Collection}.
	 * @param predicate the function to call for each element in the collection,
	 *     returning <code>true</code> if the element matches the predicate. 
	 * @return <code>true</code> if each element in the collection satisifies the
	 *     predicate, else <code>false</code>.
	 */
	public static <V> boolean every(final Iterable<V> collection, final UnaryPredicate<V> predicate) {
		if (collection == null || predicate == null) {
			return false;
		}
		boolean result = true;
		for (V value : collection) {
			if (!predicate.call(value).booleanValue()) {
				result = false;
				break;
			}
		}
		return result;
	}
	
	/**
	 * Return a new collection containing only those elements that <em>did</em>
	 * satisfy the predicate provided.
	 * 
	 * If either <em>collection</em> or <em>function</em> is <code>null</code>
	 * then the function will return <code>null</code> immediately.
	 * 
	 * @param <V> the element type for each element in the input collection
	 * @param collection the element type for each element in the output collection
	 *     is implied by {@link java.util.Collection}.
	 * @param predicate the function to call for each element in the collection,
	 *     returning <code>true</code> if the element matches the predicate. 
	 * @return a new collection containing only those elements where the provided
	 *     predicate returned <code>true</code>.
	 */
	public static <V> Collection<V> filter(final Iterable<V> collection, final UnaryPredicate<V> predicate) {
		if (collection == null || predicate == null) {
			return null;
		}
		List<V> result = new LinkedList<V>();
		for (V value : collection) {
			if (predicate.call(value).booleanValue() == true) {
				result.add(value);
			}
		}
		return result;
	}
	
	/**
	 * Return a new collection containing only those elements that <em>did not</em>
	 * satisfy the predicate provided.
	 * 
	 * If either <em>collection</em> or <em>function</em> is <code>null</code>
	 * then the function will return <code>null</code> immediately.
	 * 
	 * @param <V> the element type for each element in the input collection
	 * @param collection the element type for each element in the output collection
	 * @param collection the collection of elements, note no guarantee of order
	 *     is implied by {@link java.util.Collection}.
	 * @param predicate the function to call for each element in the collection,
	 *     returning <code>true</code> if the element matches the predicate. 
	 * @return a new collection containing only those elements where the provided
	 *     predicate returned <code>false</code>.
	 */
	public static <V> Collection<V> remove(final Iterable<V> collection, final UnaryPredicate<V> predicate) {
		if (collection == null || predicate == null) {
			return null;
		}
		List<V> result = new LinkedList<V>();
		for (V value : collection) {
			if (predicate.call(value).booleanValue() == false) {
				result.add(value);
			}
		}
		return result;
	}

	/**
	 * Return a list containing two collections; the first contains all the 
	 * elements in the input collection that <em>did</em> satisfy the 
	 * provided predicate and the second contains all those elements that
	 * <em>did not</em>. 
	 * 
	 * If either <em>collection</em> or <em>function</em> is <code>null</code>
	 * then the function will return <code>null</code> immediately.
	 * 
	 * @param <V> the element type for each element in the input collection
	 * @param collection the collection of elements, note no guarantee of order
	 *     is implied by {@link java.util.Collection}.
	 * @param predicate the function to call for each element in the collection,
	 *     returning <code>true</code> if the element matches the predicate. 
	 * @return a list with two collections, one containing all elements that did
	 *     satisfy the predicate and the second containing all elements that did 
	 *     not.
	 */
	public static <V> List<Collection<V>> partition(final Iterable<V> collection, final UnaryPredicate<V> predicate) {
		if (collection == null || predicate == null) {
			return null;
		}
		List<Collection<V>> result = new ArrayList<Collection<V>>();
		result.add(new LinkedList<V>());
		result.add(new LinkedList<V>());
		for (V value : collection) {
			if (predicate.call(value).booleanValue() == true) {
				result.get(0).add(value);
			} else {
				result.get(1).add(value);
			}
		}
		return result;
	}

	/**
	 * Return a list containing <em>N</em> collections; the provided function 
	 * is a map from each element to the partition it is to be added to.
	 * 
	 * If either <em>collection</em> or <em>function</em> is <code>null</code>
	 * then the function will return <code>null</code> immediately.
	 * 
	 * @param <V> the element type for each element in the input collection
	 * @param collection the collection of elements, note no guarantee of order
	 *     is implied by {@link java.util.Collection}.
	 * @param predicate the function to call for each element in the collection,
	 *     returning an integer identifying the partition. 
	 * @param partitions in integer identifying how many partitions that need to
	 *     be created in the output.
	 * @return a list of partitions, each of which is a collection of 
	 *     <em>0..n</em> elements from the input collection.
	 */
	public static <V> List<Collection<V>> partitionN(final Iterable<V> collection, final UnaryFunction<V, Integer> predicate, int partitions) {
		if (collection == null || predicate == null) {
			return null;
		}
		List<Collection<V>> result = new ArrayList<Collection<V>>();
		for (int i = 0; i < partitions; i++) {
			result.add(new LinkedList<V>());
		}
		if (partitions > 0) {
			for (V value : collection) {
				final int partition = predicate.call(value).intValue();
				if (partition < partitions) {
					result.get(partition).add(value);
				} else {
					throw new IndexOutOfBoundsException("partition == " + partition);
				}
			}
		}
		return result;
	}

	/**
	 * Return a value that is calculated by applying each element in the list,
	 * in list order, to the result of <code>foldLeft</code> for each preceding 
	 * element. For example, the application of the logical operation "plus" to the
	 * list of integers [1, 2, 3, 4, 5] is the value 15, as can be seen below:
	 * 
	 * <pre>
	 * plus(plus(plus(plus(1, 2), 3), 4), 5)
	 * </pre>
	 *  
	 * This can be see also as "folding" the operation into the list, so that if 
	 * "plus" where the mathematical operator "+" then the list becomes:
	 * 
	 * <pre>
	 * 1 + 2 + 3 + 4 + 5
	 * </pre>
	 * 
	 * Note also that as this can be expressed in an entirely recursive manner 
	 * there is no need for the operation itself to maintain any state.
	 * 
	 * If either <em>collection</em> or <em>function</em> is <code>null</code>
	 * then the function will return <code>null</code> immediately.
	 * 
	 * @param <V> the element type for each element in the input collection and 
	 *     the type of the output value. 
	 * @param list an ordered list of elements.
	 * @param operation the operation to apply to the values in the list.
	 * @return the result of folding the operation into the list.
	 */
	public static <V> V foldLeft(final Iterable<V> list, final BinaryFunction<V,V,V> operation) {
		if (list == null || operation == null) {
			return null;
		}
		V accumulator = null;
		for (V value : list) {
			if (accumulator == null) {
				accumulator = value;
			} else {
				accumulator = operation.call(accumulator, value);
			}
		}
		return accumulator;
	}

	/**
	 * Similar to {@link #foldLeft(List, BinaryFunction)}, however this computes an
	 * alternate accumulated value. In this case the initial value for the 
	 * accumulator is passed in, and the operation will therefore be provided
	 * the current value of the accumulator and the current element value and
	 * is expected to return a new accumulator value.
	 * 
	 * If either <em>collection</em> or <em>function</em> is <code>null</code>
	 * then the function will return <code>null</code> immediately.
	 * 
	 * @param <AV> the type of the accumulator that will be calculated by this
	 *     function.
	 * @param <EV> the element type for each element in the input collection and 
	 *     the type of the output value.
	 * @param list
	 * @param operation the operation to apply to the values in the list.
	 * @param initial the initial value of the accumulator
	 * @return the result of applying operation to the accumulator and each
	 *     element of the list in order.
	 */
	public static <AV, EV> AV accumulate(final Iterable<EV> list, final BinaryFunction<AV, AV, EV> operation, AV initial) {
		if (list == null || operation == null) {
			return null;
		}
		AV accumulator = initial;
		for (EV value : list) {
			accumulator = operation.call(accumulator, value);
		}
		return accumulator;
	}

	/**
	 * Similar to {@link #foldLeft(List, BinaryFunction)}, however this function 
	 * folds the operator into the list from right to left rather than
	 * from left to right. It can be shown that the two functions are 
	 * related in the following manner:
	 * 
	 * <pre>
	 * foldLeft(list, op) == foldRight(reverse(list), op)
	 * </pre>
	 * 
	 * If either <em>collection</em> or <em>function</em> is <code>null</code>
	 * then the function will return <code>null</code> immediately.
	 * 
	 * @param <V> the element type for each element in the input collection and 
	 *     the type of the output value. 
	 * @param list an ordered list of elements.
	 * @param operation the operation to apply to the values in the list.
	 * @return the result of folding the operation into the list.
	 */
	public static <V> V foldRight(final List<V> list, final BinaryFunction<V,V,V> operation) {
		if (list == null || operation == null) {
			return null;
		}
		V accumulator = null;
		for (int i = list.size()-1; i >= 0; i--) {
			final V value = list.get(i);
			if (accumulator == null) {
				accumulator = value;
			} else {
				accumulator = operation.call(accumulator, value);
			}
		}
		return accumulator;
	}

	/**
	 * "zip together" two lists, or in fact in the case <em>N</em> lists into
	 * a new list. Like a zip fastener this interleaves the lists, creating a 
	 * list for each first element in the input, then a list for each second
	 * element in the input and so forth. Where the input lists are of different
	 * length the size of the output is the length of the shortest input
	 * list.
	 * 
	 * For example:
	 * 
	 * <pre>
	 * zip({{1, 2, 3, 4}
	 *      {5, 6, 7, 8, 9}})
	 * =>
	 * { {1, 5}, {2, 6}, {3, 7}, {4, 8} }
	 * </pre>
	 * 
	 * Note, is <em>lists</em> is <code>null</code>, or every element in the 
	 * list is <code>null</code> then this function will return <code>null</code>.
	 * 
	 * @param <V> the element type for each element in both the input 
	 *     and output collections. 
	 * @param lists a list of lists that contain the values to zip
	 * @return a list of lists that contains the zipped values
	 */
	public static <V> List<List<V>> zip(final List<List<V>> lists) {
		if (lists == null) {
			return null;
		}
		int nulls = 0;
		int max = Integer.MAX_VALUE;
		for (Collection<V> collection : lists) {
			if (collection != null) {
				max = Math.min(max, collection.size());
			} else {
				nulls++;
			}
		}
		if (nulls == lists.size()) {
			return null;
		}
		List<List<V>> results = new ArrayList<List<V>>(max);
		for (int i = 0; i < max; i++) {
			List<V> row = new ArrayList<V>(lists.size());
			results.add(row);
		}
		int column = 0;
		for (List<V> collection : lists) {
			int row = 0;
			for (V value : collection) {
				results.get(row).add(column, value);
				if (row >= max-1) {
					break;
				}
				row++;
			}
			column++;
		}
		return results;
	}
	
	/**
	 * Reverse the order of the list provided and return as another list.
	 * 
	 * If either <em>collection</em> or <em>function</em> is <code>null</code>
	 * then the function will return <code>null</code> immediately.
	 * 
	 * @param <T> the element type for each element in both the input 
	 *     and output collections. 
	 * @param list an ordered list of elements.
	 * @return a new list in reverse order 
	 */
	public static <T> List<T> reverse(final List<T> list) {
		if (list == null) {
			return null;
		}
		Stack<T> results = new Stack<T>();
		for (int i = list.size()-1; i >= 0; i--) {
			results.push(list.get(i));
		}
		return results;
	}
	
	/**
	 * Flatten a list of lists into a single list, this is ordered based on
	 * the order of the lists themselves.
	 * 
     * If <em>lists</em> is <code>null</code> then the function will return 
     * <code>null</code> immediately.
	 * 
	 * @param <T> the element type for each element in both the input 
     *     and output collections. 
	 * @param lists an ordered list of lists
	 * @return a single, flattened, list
	 */
	public static <T> List<T> flatten(final Iterable<Iterable<T>> lists) {
	    if (lists == null) {
	        return null;
	    }
	    List<T> result = new LinkedList<T>();
	    for (Iterable<T> list : lists) {
	        if (list != null) {
	            for (T item : list) {
                    result.add(item);
	            }
	        }
	    }
	    return result;
	}
}

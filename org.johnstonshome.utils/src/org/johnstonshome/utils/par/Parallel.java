/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.utils.par;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.johnstonshome.utils.fun.BinaryFunction;
import org.johnstonshome.utils.fun.UnaryFunction;
import org.johnstonshome.utils.fun.ValueFunction;
import org.johnstonshome.utils.fun.UnaryProcedure;

public class Parallel {
	
	private static ThreadPoolExecutor DEFAULT_EXECUTOR;
	private static int DEFAULT_SLICE_SIZE = 500;
	
	static {
		DEFAULT_EXECUTOR = new ThreadPoolExecutor(
				10, 
				15, 
				5000, 
				TimeUnit.MILLISECONDS, 
				new LinkedBlockingQueue<Runnable>());
	}

	class Slice<E> {
		public List<E> list;
		public int offset;
	}
	
	class Element<E> {
		public E element;
		public int index;
	}
	
	public static <V> void forEach(List<V> collection, UnaryProcedure<V> function) {
		forEach(collection, function, DEFAULT_SLICE_SIZE, DEFAULT_EXECUTOR);
	}
	
	public static <V> void forEach(List<V> collection, UnaryProcedure<V> function, int sliceSize) {
		forEach(collection, function, sliceSize, DEFAULT_EXECUTOR);
	}
	
	public static <V> void forEach(List<V> collection, UnaryProcedure<V> function, int sliceSize, ThreadPoolExecutor executor) {
	}
	
	public static <V1, V2> ValueFunction<List<V2>> map(List<V1> collection, UnaryFunction<V1, V2> function) {
		MessageFunctions<List<V2>> channel = new MessageFunctions<List<V2>>();
		return channel.getReader();
	}
	
	public static <V> Promise<Collection<V>> filter(Collection<V> collection, UnaryFunction<V, Boolean> function) {
		return null;
	}
	
	public static <V> Promise<V> foldl(List<V> collection, BinaryFunction<V,V,V> operation) {
		return null;
	}
	
	public static <V> Promise<V> foldr(List<V> collection, BinaryFunction<V,V,V> operation) {
		return null;
	}

}

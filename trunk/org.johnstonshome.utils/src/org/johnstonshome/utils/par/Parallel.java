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

import org.johnstonshome.utils.fun.MapFunction;
import org.johnstonshome.utils.fun.SymOperation;
import org.johnstonshome.utils.fun.VoidFunction;

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
	
	// TODO: can you wait for completion?
	public static <V> void forEach(List<V> collection, VoidFunction<V> function) {
		forEach(collection, function, DEFAULT_SLICE_SIZE, DEFAULT_EXECUTOR);
	}
	
	public static <V> void forEach(List<V> collection, VoidFunction<V> function, int sliceSize) {
		forEach(collection, function, sliceSize, DEFAULT_EXECUTOR);
	}
	
	public static <V> void forEach(List<V> collection, VoidFunction<V> function, int sliceSize, ThreadPoolExecutor executor) {
	}
	
	// TODO: use futures
	public static <V1, V2> List<V2> map(List<V1> collection, MapFunction<V1, V2> function) {
		return null;
	}
	
	public static <V> Collection<V> filter(Collection<V> collection, MapFunction<V, Boolean> function) {
		return null;
	}
	
	public static <V> V foldl(List<V> collection, SymOperation<V> operation) {
		return null;
	}
	
	public static <V> V foldr(List<V> collection, SymOperation<V> operation) {
		return null;
	}

}

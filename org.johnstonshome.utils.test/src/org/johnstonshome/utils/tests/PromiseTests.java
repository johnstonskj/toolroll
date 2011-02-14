/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.utils.tests;

import org.johnstonshome.utils.fun.UnaryFunction;
import org.johnstonshome.utils.fun.ValueFunction;
import org.johnstonshome.utils.fun.UnaryProcedure;
import org.johnstonshome.utils.par.AsynchronousPromise;
import org.johnstonshome.utils.par.SynchronousPromise;
import org.junit.Test;

/**
 * Tests for the {@link Promise}, {@link SynchronousPromise}, and
 * {@link AsynchronousPromise} classes.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 */
public class PromiseTests {
	
	@Test
	public void testSimpleAsyncPromise() {
		SynchronousPromise<String> promise = new SynchronousPromise<String>();
		promise.then(new UnaryFunction<String, Integer>() {
			@Override
			public Integer call(String value) {
				System.out.println("HELLO " + value);
				return 101;
			}
		}).then(new UnaryProcedure<Integer>() {
			@Override
			public void call(Integer value) {
				System.out.println(value);
			}			
		});
		promise.call(new ValueFunction<String>() {
			@Override
			public String call() {
				return "world";
			}
		});
 	}

	@Test
	public void testSimpleAsyncExceptionPromise() {
		System.out.println(Thread.currentThread().getId() + " -->");
		AsynchronousPromise<String> promise = new AsynchronousPromise<String>();
		promise.then(new UnaryFunction<String, Integer>() {
			@Override
			public Integer call(String value) {
				System.out.println(Thread.currentThread().getId() + " HELLO " + value);
				return 101;
			}
		},
		new UnaryProcedure<Throwable>() {
			@Override
			public void call(Throwable value) {
				System.out.println(Thread.currentThread().getId() + " Error handler called! error: " + value);
			}
		}).then(new UnaryProcedure<Integer>() {
			@Override
			public void call(Integer value) {
				System.out.println(Thread.currentThread().getId() + " " + value);
				throw new IllegalArgumentException();
			}			
		});
		promise.call(new ValueFunction<String>() {
			@Override
			public String call() {
				System.out.println(Thread.currentThread().getId() + " call");
				return "world";
			}
		});
		System.out.println(Thread.currentThread().getId() + " <--");
 	}
}

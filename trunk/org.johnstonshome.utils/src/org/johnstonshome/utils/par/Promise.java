/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.utils.par;

import org.johnstonshome.utils.fun.UnaryFunction;
import org.johnstonshome.utils.fun.ValueFunction;
import org.johnstonshome.utils.fun.UnaryProcedure;

/**
 * A promise is an object that performs some calculation and may have an 
 * associated handler which will be called with the result of this 
 * calculation when complete. The handler function can be a 
 * {@link UnaryFunction} which implies that the handler itself produces a 
 * value, in which case a new Promise is created for this new value.
 * Note that when adding a UnaryFunction a chain of handlers is created,
 * when a UnaryProcedure s added the chain is complete.
 * 
 * A Promise is also a {@link UnaryProcedure} which means it can be the
 * input to another Promise. It also requires that it's input is a 
 * {@link ValueFunction}, this function is then scheduled to generate
 * a value either synchronously or asynchronously.
 * 
 * This is akin to the {@link java.util.concurrent.Future}, except that
 * rather than having a blocking function to retrieve the value of the 
 * calculation we chain together handlers which consume the value(s).
 * 
 * Note that the behavior of multiple calls to the method <em>call</em>
 * or the invocation of intermediate promise <em>call</em> calls is
 * indeterminate. 
 * 
 * <h3>Example</h3>
 * 
 * <pre>
 * SynchronousPromise<String> promise = new SynchronousPromise<String>();
 * 
 * promise.then(new UnaryFunction<String, Integer>() {
 *     @Override
 *     public Integer call(String value) {
 *         System.out.println("HELLO " + value);
 *         return 101;
 *     }
 * }).then(new UnaryProcedure<Integer>() {
 *     @Override
 *     public void call(Integer value) {
 *         System.out.println(value);
 *     }			
 * });
 * 
 * promise.call(new ValueFunction<String>() {
 *     @Override
 *     public String call() {
 *         return "world";
 *     }
 * });
 * </pre>
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 * 
 * @param <V> the type for the initial input to this promise.
 *
 */
public interface Promise<V> extends UnaryProcedure<ValueFunction<V>> {

	/**
	 * Add a handler to this promise, this will be called when the promise
	 * has generated a value.
	 * 
	 * @param <V2> the return value from the UnaryFunction, also therefore the
	 *     value type for the Promise returned from this call.
	 * @param handler the handler to be added for this Promise
	 * @return a new Promise for the value of the handler
	 */
	public <V2> Promise<V2> then(UnaryFunction<V, V2> handler);

	/**
	 * Add a handler to this promise, this will be called when the promise
	 * has generated a value.
	 * 
	 * @param <V2> the return value from the UnaryFunction, also therefore the
	 *     value type for the Promise returned from this call.
	 * @param handler the handler to be added for this Promise
	 * @param errorHandler a handler to be called if the primary value 
	 *     throws an exception.
	 * @return a new Promise for the value of the handler
	 */
	public <V2> Promise<V2> then(UnaryFunction<V, V2> handler, UnaryProcedure<Throwable> errorHandler);
	
	/**
	 * Add a handler to this promise, this will be called when the promise
	 * has generated a value.
	 * 
	 * @param handler the handler to be added for this Promise
	 */
	public void then(UnaryProcedure<V> handler);

	/**
	 * Add a handler to this promise, this will be called when the promise
	 * has generated a value.
	 * 
	 * @param handler the handler to be added for this Promise
	 * @param errorHandler a handler to be called if the primary value 
	 *     throws an exception.
	 */
	public void then(UnaryProcedure<V> handler, UnaryProcedure<Throwable> errorHandler);
}

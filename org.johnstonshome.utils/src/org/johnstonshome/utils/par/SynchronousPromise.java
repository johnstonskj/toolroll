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
 * A synchronous {@link Promise}, each handler is called in turn when the 
 * initial promise {@link #call(Object)} method is invoked. All handlers 
 * will run in the current thread, all will run immediately and the initial
 * {@link #call(Object)} method will not return until all handlers have
 * been executed.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 * @param <V> the type for the initial input to this promise.
 */
public class SynchronousPromise<V> implements Promise<V> {

	private UnaryFunction<V, ?> handler;
	private UnaryProcedure<V> voidHandler;
	private UnaryProcedure<Throwable> errorHandler;
	private SynchronousPromise<?> next;
	
	/*
	 * (non-Javadoc)
	 * @see org.johnstonshome.utils.par.Promise#then(org.johnstonshome.utils.fun.UnaryFunction)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <V2> Promise<V2> then(UnaryFunction<V, V2> handler) {
		this.handler = handler;
		this.voidHandler = null;
		this.next = new SynchronousPromise<V2>();
		return (Promise<V2>) this.next;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.johnstonshome.utils.par.Promise#then(org.johnstonshome.utils.fun.UnaryFunction, org.johnstonshome.utils.fun.UnaryProcedure)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <V2> Promise<V2> then(UnaryFunction<V, V2> handler,
			UnaryProcedure<Throwable> errorHandler) {
		this.handler = handler;
		this.voidHandler = null;
		this.errorHandler = errorHandler;
		this.next = new SynchronousPromise<V2>();
		return (Promise<V2>) this.next;
	}

	/*
	 * (non-Javadoc)
	 * @see org.johnstonshome.utils.par.Promise#then(org.johnstonshome.utils.fun.UnaryProcedure)
	 */
	@Override
	public void then(UnaryProcedure<V> handler) {
		this.handler = null;
		this.voidHandler = handler;
	}

	/*
	 * (non-Javadoc)
	 * @see org.johnstonshome.utils.par.Promise#then(org.johnstonshome.utils.fun.UnaryProcedure, org.johnstonshome.utils.fun.UnaryProcedure)
	 */
	@Override
	public void then(UnaryProcedure<V> handler,
			UnaryProcedure<Throwable> errorHandler) {
		this.handler = null;
		this.voidHandler = handler;
		this.errorHandler = errorHandler;
	}

	/*
	 * (non-Javadoc)
	 * @see org.johnstonshome.utils.fun.UnaryProcedure#call(java.lang.Object)
	 */
	@Override
	public void call(ValueFunction<V> value) {
		try {
			this._call(value.call());
		} catch (Throwable t) {
			if (this.errorHandler != null) {
				this.errorHandler.call(t);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void _call(Object value) {
		/*
		 * This method was required to get around compiler errors because of 
		 * Java's generic type erasure. 
		 */
		if (this.next == null) {
			if (this.handler != null) {
				this.handler.call((V)value);
			}
			if (this.voidHandler != null) {
				this.voidHandler.call((V)value);
			}
		} else {
			this.next._call(this.handler.call((V)value));
		}
	}
	
}
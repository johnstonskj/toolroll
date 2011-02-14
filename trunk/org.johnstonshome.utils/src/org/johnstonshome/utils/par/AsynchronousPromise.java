/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.utils.par;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.johnstonshome.utils.fun.MapFunction;
import org.johnstonshome.utils.fun.ValueFunction;
import org.johnstonshome.utils.fun.VoidFunction;

/**
 * 
 * Create thread, lock everything, run entire chain on new thread
 * 
 * Allows handler to be added, if value function still running
 * 
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 * @param <V> the type for the initial input to this promise.
 */
public class AsynchronousPromise<V> implements Promise<V>, Runnable {
	
	private static ThreadPoolExecutor executor = new ThreadPoolExecutor(
			10, 
			10, 
			10000, 
			TimeUnit.MILLISECONDS, 
			new LinkedBlockingQueue<Runnable>()); 

	private ValueFunction<V> value;
	private MapFunction<V, ?> handler;
	private VoidFunction<V> voidHandler;
	private VoidFunction<Throwable> errorHandler;
	private AsynchronousPromise<?> next;
	
	private Lock lock = new ReentrantLock();
	
	/*
	 * (non-Javadoc)
	 * @see org.johnstonshome.utils.par.Promise#then(org.johnstonshome.utils.fun.MapFunction)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <V2> Promise<V2> then(MapFunction<V, V2> handler) {
		if (this.lock.tryLock()) {
			this.handler = handler;
			this.voidHandler = null;
			this.next = new AsynchronousPromise<V2>();
			this.lock.unlock();
			return (Promise<V2>) this.next;
		}
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.johnstonshome.utils.par.Promise#then(org.johnstonshome.utils.fun.MapFunction, org.johnstonshome.utils.fun.VoidFunction)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <V2> Promise<V2> then(MapFunction<V, V2> handler,
			VoidFunction<Throwable> errorHandler) {
		if (this.lock.tryLock()) {
			this.handler = handler;
			this.voidHandler = null;
			this.errorHandler = errorHandler;
			this.next = new AsynchronousPromise<V2>();
			this.lock.unlock();
			return (Promise<V2>) this.next;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.johnstonshome.utils.par.Promise#then(org.johnstonshome.utils.fun.VoidFunction)
	 */
	@Override
	public void then(VoidFunction<V> handler) {
		if (this.lock.tryLock()) {
			this.handler = null;
			this.voidHandler = handler;
			this.lock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.johnstonshome.utils.par.Promise#then(org.johnstonshome.utils.fun.VoidFunction, org.johnstonshome.utils.fun.VoidFunction)
	 */
	@Override
	public void then(VoidFunction<V> handler,
			VoidFunction<Throwable> errorHandler) {
		if (this.lock.tryLock()) {
			this.handler = null;
			this.voidHandler = handler;
			this.errorHandler = errorHandler;
			this.lock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.johnstonshome.utils.fun.VoidFunction#call(java.lang.Object)
	 */
	@Override
	public void call(ValueFunction<V> value) {
		this.value = value;
		executor.execute(this);
	}

	@Override
	public void run() {
		try {
			this.lock.lock();
			this._call(this.value.call());
		} catch (Throwable t) {
			if (this.errorHandler != null) {
				this.errorHandler.call(t);
			}
		} finally {
			this.lock.unlock();
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
/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.utils.par;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

import org.johnstonshome.utils.fun.ValueFunction;
import org.johnstonshome.utils.fun.UnaryProcedure;

/**
 * This class provides two functions, a reader and a writer which use a shared
 * synchronous buffer to exchange messages.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 * @param <V> the type of the message sent between writer and reader.
 */
public class MessageFunctions<V> {

	private class WriteValueFunction implements UnaryProcedure<V> {
		
		private BlockingQueue<V> rendezvous;

		public WriteValueFunction(BlockingQueue<V> rendezvous) {
			this.rendezvous = rendezvous;
		}

		@Override
		public void call(V value) {
			try {
				this.rendezvous.put(value);
			} catch (InterruptedException e) {
				// ?
			}			
		}
		
	}
	
	private class ReadValueFunction implements ValueFunction<V> {

		private BlockingQueue<V> rendezvous;

		public ReadValueFunction(BlockingQueue<V> rendezvous) {
			this.rendezvous = rendezvous;
		}
		
		@Override
		public V call() {
			try {
				return this.rendezvous.take();
			} catch (InterruptedException e) {
				return null;
			}
		}
		
	}
	
	private WriteValueFunction writer;
	private ReadValueFunction reader;
	
	/**
	 * Create a new object and initialize the communication channel. In this
	 * case the buffer has a zero-length size and so can only communicate a
	 * single message at a time.
	 */
	public MessageFunctions() {
		BlockingQueue<V> rendezvous = new SynchronousQueue<V>();
		this.writer = new WriteValueFunction(rendezvous);
		this.reader = new ReadValueFunction(rendezvous);
	}
	
	/**
	 * Create a new object and initialize the communication channel. In this
	 * case the communication may be buffered to allow the writer to work
	 * ahead of the reader.
	 * 
	 * @param bufferSize the number of messages allowed to be buffered. This
	 *     must be greater than zero and less than <code>Integer.MAX_VALUE</code>
	 */
	public MessageFunctions(int bufferSize) {
		BlockingQueue<V> rendezvous = new LinkedBlockingQueue<V>(bufferSize);
		this.writer = new WriteValueFunction(rendezvous);
		this.reader = new ReadValueFunction(rendezvous);
	}
	
	/**
	 * Return the writer function which can be used to send a message to 
	 * the reader.
	 * 
	 * @return the writer function.
	 */
	public UnaryProcedure<V> getWriter() {
		return this.writer;
	}
	
	/**
	 * Return the reader function which can be used to read messages sent
	 * to it from the writer.
	 * 
	 * @return the reader function.
	 */
	public ValueFunction<V> getReader() {
		return this.reader;
	}
}

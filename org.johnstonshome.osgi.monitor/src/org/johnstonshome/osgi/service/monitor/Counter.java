/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.osgi.service.monitor;

/**
 * A Counter monitor allows for a single integer value corresponding
 * to a count of events. While the counter does support a {@link #set(long)} 
 * method it is usually used with just {@link #increment()} and 
 * {@link #decrement()}.
 * 
 * @author Simon Johnston
 *
 */
public interface Counter extends Monitor {

	/**
	 * Get the current value of the counter.
	 * 
	 * @return the current value, the initial value is <code>0</code>.
	 */
	public long get();
	
	/**
	 * Set the value of the counter.
	 * 
	 * @param value a new value for the counter.
	 */
	public void set(long value);
	
	/**
	 * Increment the counter by <code>1</code>.
	 */
	public void increment();
	
	/**
	 * Increment the counter by <code>delta</code>. Equivalent to:
	 * 
	 * <pre>
	 *     counter.set(counter.get() + delta);
	 * </pre>
	 * 
	 * @param delta the amount to add to the current counter value.
	 */
	public void increment(long delta);

	/**
	 * Decrement the counter by <code>1</code>.
	 */
	public void decrement();

	/**
	 * Decrement the counter by <code>delta</code>. Equivalent to:
	 * 
	 * <pre>
	 *     counter.set(counter.get() - delta);
	 * </pre>
	 * 
	 * @param delta the amount to subtract from the current counter value.
	 */
	public void decrement(long delta);
}

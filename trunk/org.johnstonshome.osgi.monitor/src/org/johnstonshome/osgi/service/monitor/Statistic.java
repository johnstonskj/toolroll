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
 * A monitor that tracks a set of integer values over time and keeps track of
 * statistics for those values. 
 * 
 * @author Simon Johnston
 *
 */
public interface Statistic extends Monitor {

	/**
	 * Add a new value to the statistical set.
	 * 
	 * @param value the new value.
	 */
	public void addValue(long value);
	
	/**
	 * Return the count of how many values have been added to the set.
	 * 
	 * @return the number of calls to {@link #addValue(long)}.
	 */
	public long getCount();
	
	/**
	 * Returns the minimum value of all those added to the set.
	 * 
	 * @return the minimum value provided to {@link #addValue(long)}.
	 */
	public long getMin();

	/**
	 * Returns the maximum value of all those added to the set.
	 * 
	 * @return the maximum value provided to {@link #addValue(long)}.
	 */
	public long getMax();
		
	/**
	 * Returns the average of all values added to the set.
	 * 
	 * @return the average of all values provided to {@link #addValue(long)}.
	 */
	public double getAverage();
}

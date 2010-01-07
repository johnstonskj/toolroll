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
 * Similar to the {@link Statistic} monitor this monitor tracks a set of duration
 * values and records statistics for those values. The duration values can be 
 * recorded in different resolutions, although some platforms may not provide
 * all resolutions. Timer values use the {@link Duration} interface to handle
 * actual duration values.
 * 
 * @author Simon Johnston
 *
 */
public interface TimerStatistic extends Monitor {

	/**
	 * Start a timer, that is record a value for the current time.
	 */
	public void startTimer();
	
	/**
	 * Stop the timer and record the duration since {@link #startTimer()} 
	 * as a value in the value set.
	 */
	public void stopTimer();
	
	/**
	 * Stop the timer, do not record any duration in the value set.
	 */
	public void abortTimer();
	
	/**
	 * Return the count of how many values have been added to the set.
	 * 
	 * @return the number of duration values in the set.
	 */
	public long getCount();

	/**
	 * Returns the minimum value of all those added to the set.
	 * 
	 * @return the minimum duration recorded in the set.
	 */
	public Duration getMin();

	/**
	 * Returns the maximum value of all those added to the set.
	 * 
	 * @return the maximum duration recorded in the set.
	 */
	public Duration getMax();

	/**
	 * Returns the average of all values added to the set.
	 * 
	 * @return the average of durations recorded in the set.
	 */
	public Duration getAverage();
	
	/**
	 * Return the current resolution of the timer used to record
	 * duration values. Clients need this to be able to interpret
	 * the values returned from the statistical value methods 
	 * above.
	 * 
	 * @return the resolution used to record durations.
	 */
	public TimerResolution getResolution();
}

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
 * A Duration is a relatively simple wrapper around the values provided by a 
 * {@link TimerStatistic} instance.
 * 
 * @author Simon Johnston
 *
 */
public interface Duration {

	/**
	 * Return the number of hours in this duration (may exceed 24).
	 * 
	 * @return the number of hours
	 */
	int getHours();
	
	/**
	 * Return the number of minutes in this duration.
	 * 
	 * @return the number of minutes
	 */
	int getMinutes();
	
	/**
	 * Return the number of seconds in this duration.
	 * 
	 * @return the seconds of minutes
	 */
	int getSeconds();
	
	/**
	 * Return the fractions of a second in this duration.
	 * 
	 * @return the fractions of a second.
	 */
	long getFractional();
}

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
 * Describes the possible resolution provided by the {@link TimerStatistic}
 * monitor.
 * 
 * @author Simon Johnston
 *
 */
public enum TimerResolution {
	
	/**
	 * The {@link TimerStatistic} records durations in seconds.
	 */
	SECONDS,
	
	/**
	 * The {@link TimerStatistic} records durations in milliseconds
	 * (using the {@link java.lang.System#currentTimeMillis()} method).
	 */
	MILLI_SECONDS,
	
	/**
	 * The {@link TimerStatistic} records durations in nanoseconds
	 * (using the {@link java.lang.System#nanoTime()} method).
	 */
	NANO_SECONDS
}

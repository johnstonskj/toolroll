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
 * An implementation of this interface will be provided with events 
 * corresponding to the lifecycle of monitors. All monitors will generate
 * <em>created</em> and <em>removed</em> events, but only those created
 * with the <em>signalled</em> flag set to <code>true</code> will generate
 * <em>updated</em> events. In general it is not a good idea to create 
 * signalling monitors unless they generate a small number of updates. 
 * 
 * @author Simon Johnston
 *
 */
public interface MonitorListener {

	/**
	 * Signals that a new monitor was created by the {@link MonitorService}
	 * service.
	 * 
	 * @param monitor the newly created monitor.
	 */
	public void monitorCreated(Monitor monitor);
	
	/**
	 * Signals that the monitor value was updated, a client would need to 
	 * track the monitor to be able to determine what has changed.
	 *  
	 * @param monitor the changed monitor.
	 */
	public void monitorUpdated(Monitor monitor);
	
	/**
	 * Signals that a new monitor was removed by the {@link MonitorService}
	 * service.
	 * 
	 * @param monitor the removed monitor.
	 */
	public void monitorRemoved(Monitor monitor);
}

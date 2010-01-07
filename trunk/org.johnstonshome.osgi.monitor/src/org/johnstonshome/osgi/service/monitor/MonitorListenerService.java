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
 * Manage listeners that implement the {@link MonitorListener} interface.
 * 
 * @author Simon Johnston
 *
 */
public interface MonitorListenerService {

	/**
	 * Add a new listener.
	 * 
	 * @param listener a new {@link MonitorListener} instance. Must not be 
	 * <code>null</code>.
	 */
	public void addMonitorListener(MonitorListener listener);

	/**
	 * Remove an existing listener.
	 * 
	 * @param listener a {@link MonitorListener} instance. Must not be 
	 * <code>null</code>.
	 */
	public void removeMonitorListener(MonitorListener listener);
}

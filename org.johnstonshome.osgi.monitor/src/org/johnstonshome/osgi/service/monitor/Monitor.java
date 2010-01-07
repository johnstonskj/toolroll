/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.osgi.service.monitor;

import org.osgi.framework.ServiceReference;

/**
 * This interface captures the basic functions of a monitor, predominantly
 * to return the properties used in creating the monitor in the first place.
 * 
 * @author Simon Johnston
 *
 */
public interface Monitor {

	/**
	 * Return the service reference passed to the creation methods on 
	 * {@link MonitorService}.
	 * 
	 * @return the service reference, or <code>null</code> if none
	 * were passed when the service was created.
	 */
	public ServiceReference getServiceReference();

	/**
	 * Return the group name passed to the creation methods on 
	 * {@link MonitorService}.
	 * 
	 * @return the group name.
	 */
	public String getGroup();
	
	/**
	 * Return the monitor name passed to the creation methods on 
	 * {@link MonitorService}.
	 * 
	 * @return the monitor name
	 */
	public String getName();
	
	/**
	 * Return a more readable display label for this monitor, this would
	 * also allow for internationalization of labels for display in a 
	 * management console.
	 * 
	 * @return the current display label, or it will return the value of
	 * {@link #getName()} if a label has not been set.
	 */
	public String getLabel();
	
	/**
	 * Set a display label for this monitor, this would
	 * also allow for internationalization of labels for display in a 
	 * management console.
	 * 
	 * @param label a display label for this monitor.
	 */
	public void setLabel(String label);

	/**
	 * Reset the state of the counter back to it's initial state.
	 */
	public void reset();
}

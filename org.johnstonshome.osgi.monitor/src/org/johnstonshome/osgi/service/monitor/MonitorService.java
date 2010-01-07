/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.osgi.service.monitor;

import java.util.Collection;
import java.util.Set;

import org.osgi.framework.ServiceReference;

/**
 * <p>
 * The monitor service provides the ability to create and remove monitors that
 * can be used by other services to maintain counters and statistics for their
 * specific behaviors.
 * </p>
 * 
 * @author Simon Johnston
 *
 */
public interface MonitorService {

	/**
	 * The property value used in the component specification for the URL
	 * alias the service will provide a JSON feed of all statistic data.
	 */
	public static final String PROP_SERVLET_ALIAS = "alias"; //$NON-NLS-1$
	
	/**
	 * Create a new {@link Counter} instance. This will cause an event to be 
	 * passed to all registered {@link MonitorListener} instances. 
	 * 
	 * @param group the name of the group in which this monitor will be 
	 * created.
	 * @param name the name of this individual group, must be unique 
	 * within the named group.
	 * @param signalling determines whether this monitor signals it's updates
	 * to any listeners.
	 * @return a new {@link Counter} instance, or <code>null</code> if 
	 * an error occurred.
	 */
	public Counter createCounter(String group, String name, boolean signalling);

	/**
	 * Create a new {@link Counter} instance. This will cause an event to be 
	 * passed to all registered {@link MonitorListener} instances. 
	 * 
	 * @param sr a service reference for the service owning this monitor.
	 * @param group the name of the group in which this monitor will be 
	 * created.
	 * @param name the name of this individual group, must be unique 
	 * within the named group.
	 * @param signalling determines whether this monitor signals it's updates
	 * to any listeners.
	 * @return a new {@link Counter} instance, or <code>null</code> if 
	 * an error occurred.
	 */
	public Counter createCounter(ServiceReference sr, String group, String name, boolean signalling);

	/**
	 * Create a new {@link Statistic} instance. This will cause an event to be 
	 * passed to all registered {@link MonitorListener} instances. 
	 * 
	 * @param group the name of the group in which this monitor will be 
	 * created.
	 * @param name the name of this individual group, must be unique 
	 * within the named group.
	 * @param signalling determines whether this monitor signals it's updates
	 * to any listeners.
	 * @return a new {@link Statistic} instance, or <code>null</code> if 
	 * an error occurred.
	 */
	public Statistic createStatistic(String group, String name, boolean signalling);

	/**
	 * Create a new {@link Statistic} instance. This will cause an event to be 
	 * passed to all registered {@link MonitorListener} instances. 
	 * 
	 * @param sr a service reference for the service owning this monitor.
	 * @param group the name of the group in which this monitor will be 
	 * created.
	 * @param name the name of this individual group, must be unique 
	 * within the named group.
	 * @param signalling determines whether this monitor signals it's updates
	 * to any listeners.
	 * @return a new {@link Statistic} instance, or <code>null</code> if 
	 * an error occurred.
	 */
	public Statistic createStatistic(ServiceReference sr, String group, String name, boolean signalling);

	/**
	 * Create a new {@link TimerStatistic} instance. This will cause an event to be 
	 * passed to all registered {@link MonitorListener} instances. 
	 * 
	 * @param group the name of the group in which this monitor will be 
	 * created.
	 * @param name the name of this individual group, must be unique 
	 * within the named group.
	 * @param signalling determines whether this monitor signals it's updates
	 * to any listeners.
	 * @return a new {@link TimerStatistic} instance, or <code>null</code> if 
	 * an error occurred.
	 */
	public TimerStatistic createTimerStatistic(String group, String name, boolean signalling);

	/**
	 * Create a new {@link TimerStatistic} instance. This will cause an event to be 
	 * passed to all registered {@link MonitorListener} instances. 
	 * 
	 * @param sr a service reference for the service owning this monitor.
	 * @param group the name of the group in which this monitor will be 
	 * created.
	 * @param name the name of this individual group, must be unique 
	 * within the named group.
	 * @param signalling determines whether this monitor signals it's updates
	 * to any listeners.
	 * @return a new {@link TimerStatistic} instance, or <code>null</code> if 
	 * an error occurred.
	 */
	public TimerStatistic createTimerStatistic(ServiceReference sr, String group, String name, boolean signalling);

	/**
	 * Remove the provided monitor from the internal registry.  This will cause 
	 * an event to be passed to all registered {@link MonitorListener} instances. 
	 * Note that even if a service hangs onto the monitor after calling 
	 * <code>removeMonitor</code> no further events will be sent to listeners, 
	 * even if the <em>signalling</em> flag were set when the monitor was created.
	 * 
	 * @param monitor the monitor instance to remove.
	 */
	public void removeMonitor(Monitor monitor);

	/**
	 * Return the set of group names currently holding monitors. Note that groups
	 * are added when any monitor is created with a new, unique, name and that 
	 * groups are removed from this list when the last monitor in the group is
	 * removed using the <code>removeMonitor</code> method.
	 * 
	 * @return a {@link java.util.Set} of the unique groups holding current
	 * monitors. This is a read-only set.
	 */
	public Set<String> getMonitorGroups();
	
	/**
	 * Return a collection of all monitor instances for a given group name. Note
	 * that while the 
	 * 
	 * @param group
	 * @return a {@link java.util.Collection} of the monitors in the named group. 
	 * This is a read-only collection.
	 */
	public Collection<Monitor> getMonitorsForGroup(String group);
}

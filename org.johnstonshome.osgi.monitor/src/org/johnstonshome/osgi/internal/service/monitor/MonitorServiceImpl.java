/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.osgi.internal.service.monitor;

import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import org.johnstonshome.osgi.service.monitor.Counter;
import org.johnstonshome.osgi.service.monitor.Monitor;
import org.johnstonshome.osgi.service.monitor.MonitorService;
import org.johnstonshome.osgi.service.monitor.Statistic;
import org.johnstonshome.osgi.service.monitor.TimerStatistic;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.log.LogService;


/**
 * The monitor service is started as a declarative service so will be 
 * automatically registered, but the listener service is subservient to
 * the monitor service and so is registered and removed by the monitor
 * service as it is activated and deactivated.
 * 
 * @author Simon Johnston
 *
 */
public class MonitorServiceImpl implements MonitorService {
	
	private LogService log = null;
	private HttpService httpService = null;
	
	private Map<String, Map<String, Monitor>> monitors = null;
	
	private MonitorListenerServiceImpl listener = null;
	private ServiceRegistration listenerRegistration = null;

	private String httpAlias = null;

	@SuppressWarnings("unchecked")
	protected void activate(ComponentContext context) {
		this.monitors = new HashMap<String, Map<String, Monitor>>();
		
		/*
		 * Register the monitor listener service.
		 */
		Dictionary properties = new Hashtable(); 
		this.listener = new MonitorListenerServiceImpl();
		this.listenerRegistration = context.getBundleContext().registerService(
				MonitorListenerServiceImpl.class.getName(), 
				this.listener, 
				properties);
		
		/*
		 * If possible, register the monitor JSON Servlet.
		 */
		this.httpAlias = (String)context.getProperties().get(PROP_SERVLET_ALIAS);
		if (this.httpService != null) {
	        try {
	        	this.httpService.registerServlet(
	        			this.httpAlias,
						new MonitorServlet(this),
						null, /* init params */
						null  /* HTTP context */);
			} catch (Exception e) {
				this.log.log(LogService.LOG_WARNING, 
						String.format("Could not register servlet %s with alias %s", 
								MonitorServlet.class.getName(),
								this.httpAlias),
						e);
			}
		}
		else {
			this.log.log(LogService.LOG_DEBUG, "No HttpService, no log Servlet registered.");
		}
	}
	
	protected void deactivate(ComponentContext context) {
		if (this.httpService != null) {
			this.httpService.unregister(this.httpAlias);
		}
		this.listenerRegistration.unregister();
		this.listener.close();
		this.listener = null;
		this.monitors.clear();
		this.monitors = null;
	}

	@Override
	public Counter createCounter(String group, String name, boolean signalling) {
		return this.createCounter(null /* sr */, group, name, signalling);
	}

	@Override
	public Counter createCounter(ServiceReference sr, String group, String name, boolean signalling) {
		return (Counter)createMonitor(CounterImpl.class, sr, group, name, signalling);
	}

	@Override
	public Statistic createStatistic(String group, String name, boolean signalling) {
		return this.createStatistic(null /* sr */, group, name, signalling);
	}
	
	@Override
	public Statistic createStatistic(ServiceReference sr, String group, String name, boolean signalling) {
		return (Statistic)createMonitor(StatisticImpl.class, sr, group, name, signalling);
	}
	
	@Override
	public TimerStatistic createTimerStatistic(String group, String name, boolean signalling) {
		return this.createTimerStatistic(null /* sr */, group, name, signalling);
	}
	
	@Override
	public synchronized TimerStatistic createTimerStatistic(ServiceReference sr, String group, String name, boolean signalling) {
		return (TimerStatistic)createMonitor(TimerStatisticImpl.class, sr, group, name, signalling);
	}
	
	private synchronized Monitor createMonitor(Class<?> clazz, ServiceReference sr, String group, String name, boolean signalling) {
		if (clazz == null || 
			group == null || group.equals("") || 
			name == null || name.equals("")) {
			throw new IllegalArgumentException();
		}
		if (this.monitors == null) {
			return null;
		}
		this.log.log(LogService.LOG_DEBUG, 
				String.format("Adding monitor group: %s, name: %s", group, name));
		MonitorImpl monitor = null;
		try {
			monitor = (MonitorImpl) clazz.newInstance();
		} catch (Exception e) {
			this.log.log(LogService.LOG_ERROR, "Could not instantiate monitor.", e);
			return null;
		}
		monitor.setServiceReference(sr);
		monitor.setGroup(group);
		monitor.setName(name);
		monitor.setListener(signalling ? this.listener : null);
		if (!this.monitors.containsKey(group)) {
			this.monitors.put(group, new HashMap<String, Monitor>());
		}
		this.monitors.get(group).put(name, monitor);
		this.listener.monitorCreated(monitor);
		return monitor;
	}
	
	@Override
	public synchronized void removeMonitor(Monitor monitor) {
		if (monitor == null) {
			throw new IllegalArgumentException();
		}
		if (this.monitors == null) {
			return;
		}
		this.log.log(LogService.LOG_DEBUG, 
				String.format("Removing monitor group: %s, name: %s", 
						monitor.getGroup(), 
						monitor.getName()));
		Map<String, Monitor> group = this.monitors.get(monitor.getGroup());
		group.remove(monitor.getName());
		this.listener.monitorRemoved(monitor);
		if (group.size() == 0) {
			this.monitors.remove(monitor.getGroup());
		}
	}

	@Override
	public Set<String> getMonitorGroups() {
		if (this.monitors == null) {
			return null;
		}
		return Collections.unmodifiableSet(this.monitors.keySet());
	}

	@Override
	public Collection<Monitor> getMonitorsForGroup(String group) {
		if (this.monitors == null) {
			return null;
		}
		return Collections.unmodifiableCollection(this.monitors.get(group).values());
	}

	/*
	 * Dynamic Service methods.
	 */
	public void bindLog(LogService log)  {
		this.log = log;
	}
	
	public void unbindLog(LogService log) {
		this.log = null;
	}
	
	public void bindHttp(HttpService httpService)  {
		this.httpService = httpService;
	}
	
	public void unbindHttp(HttpService httpService) {
		this.httpService = null;
	}
}

/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.osgi.internal.service.monitor;

import java.util.LinkedList;
import java.util.List;

import org.johnstonshome.osgi.service.monitor.Monitor;
import org.johnstonshome.osgi.service.monitor.MonitorListener;
import org.johnstonshome.osgi.service.monitor.MonitorListenerService;


/**
 * 
 * @author Simon Johnston
 *
 */
public class MonitorListenerServiceImpl implements MonitorListenerService, MonitorListener {

	private List<MonitorListener> listeners = new LinkedList<MonitorListener>();
	
	@Override
	public synchronized void addMonitorListener(MonitorListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	@Override
	public synchronized void removeMonitorListener(MonitorListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		listeners.remove(listener);
	}
	
	@Override
	public synchronized void monitorCreated(Monitor monitor) {
		if (monitor == null) {
			throw new IllegalArgumentException();
		}
		for (MonitorListener listener : listeners) {
			listener.monitorCreated(monitor);
		}
	}

	@Override
	public synchronized void monitorUpdated(Monitor monitor) {
		if (monitor == null) {
			throw new IllegalArgumentException();
		}
		for (MonitorListener listener : listeners) {
			listener.monitorUpdated(monitor);
		}
	}

	@Override
	public synchronized void monitorRemoved(Monitor monitor) {
		if (monitor == null) {
			throw new IllegalArgumentException();
		}
		if (monitor instanceof MonitorImpl) {
			((MonitorImpl)monitor).setListener(null);
		}
		for (MonitorListener listener : listeners) {
			listener.monitorRemoved(monitor);
		}
	}
	
	public final int getListenerCount() {
		return listeners.size();
	}
	
	public void close() {
		listeners.clear();
	}
}

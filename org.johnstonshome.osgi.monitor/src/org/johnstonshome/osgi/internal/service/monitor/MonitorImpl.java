/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.osgi.internal.service.monitor;

import org.johnstonshome.osgi.service.monitor.Monitor;
import org.johnstonshome.osgi.service.monitor.MonitorListener;
import org.osgi.framework.ServiceReference;

/**
 * 
 * @author Simon Johnston
 *
 */
public abstract class MonitorImpl implements Monitor {

	private MonitorListener listener = null;
	private ServiceReference sr;
	private String group;
	private String name;
	private String label;

	@Override
	public String getGroup() {
		return this.group;
	}

	public MonitorListener getListener() {
		return this.listener;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public ServiceReference getServiceReference() {
		return this.sr;
	}

	public ServiceReference getSr() {
		return this.sr;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public void setListener(MonitorListener listener) {
		this.listener = listener;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getLabel() {
		if (this.label != null) {
			return this.label;
		}
		else {
			return this.name;
		}
	}
	
	public void setLabel(String label) {
		this.label = label;
	}

	public void setServiceReference(ServiceReference sr) {
		this.sr = sr;
	}

	@Override
	public abstract void reset();

	public void signalUpdate() {
		if (listener != null) {
			listener.monitorUpdated(this);
		}
	}
}

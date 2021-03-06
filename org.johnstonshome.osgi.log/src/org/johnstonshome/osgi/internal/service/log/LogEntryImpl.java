/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009-2010. All rights reserved.
 *
 * For full license details, see the file LICENSE inncluded in the
 * distribution of this code.
 *
 */
package org.johnstonshome.osgi.internal.service.log;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogEntry;

/**
 * Implementation of the OSGi {@link org.osgi.service.log.LogEntry} interface
 * to log events.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 */
public class LogEntryImpl implements LogEntry {

	private LogEntryLocation location = null;
	private ServiceReference sr = null;
	private int level = 0;
	private String message = null;
	private Throwable exception = null;
	private long time = 0;
	
	public LogEntryImpl(LogEntryLocation location, ServiceReference sr, int level, String message, Throwable exception) {
		this.location = location;
		this.sr = sr;
		this.level = level;
		this.message = message;
		this.exception = exception;
		this.time = System.currentTimeMillis();
	}
	
	@Override
	public Bundle getBundle() {
		if (this.sr != null) {
			return this.sr.getBundle();
		} else {
			return null;
		}
	}

	@Override
	public Throwable getException() {
		return this.exception;
	}

	@Override
	public int getLevel() {
		return this.level;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public ServiceReference getServiceReference() {
		return this.sr;
	}

	@Override
	public long getTime() {
		return this.time;
	}
	
	public LogEntryLocation getLocation() {
		return this.location;
	}

}

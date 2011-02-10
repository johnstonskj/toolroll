/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009-2010. All rights reserved.
 *
 * For full license details, see the file LICENSE inncluded in the
 * distribution of this code.
 *
 */
package org.johnstonshome.osgi.internal.service.log;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogReaderService;
import org.osgi.service.log.LogService;

/**
 * Implementation of the standard OSGi {@link org.osgi.service.log.LogService}
 * that logs to the Java library logging API. It also provides an implementation
 * of the {@link org.osgi.service.log.LogReaderService} to read the current
 * log and receive log events.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 */
public class LogServiceImpl implements LogService, LogReaderService {

	public static final String PROP_MEMORY_LOG_LIMIT = "memory.log.limit";
	public static final String PROP_FILE_LOG = "file.log.enabled";
	public static final String PROP_FILE_LOG_NAME = "file.log.name";
	public static final String PROP_CONSOLE_LOG = "console.log.enabled";
	
	private int stackTraceBacktrack = 2;
	
	private int memoryLogLimit = 100;
	private Deque<LogEntry> memoryLog = null;
	
	private List<LogListener> listeners = null;

	protected void activate(ComponentContext context) {
		this.memoryLog = new LinkedList<LogEntry>();
		this.listeners = new ArrayList<LogListener>();
		
		addLogListener(new LogListenerJdk14());
	}
	
	protected void deactivate(ComponentContext context) {
		this.memoryLog.clear();
		this.memoryLog = null;
		this.listeners.clear();
		this.listeners = null;
	}

	private synchronized void logEntry(LogEntry entry) {
		if (this.memoryLog.size() == this.memoryLogLimit) {
			this.memoryLog.removeLast();
		}
		this.memoryLog.addFirst(entry);
		for (LogListener listener : this.listeners) {
			listener.logged(entry);
		}
	}
	
	@Override
	public void log(int level, String message) {
		logEntry(new LogEntryImpl(
				new LogEntryLocation(this.stackTraceBacktrack),
				null /* service reference */,
				level,
				message,
				null /* exception */));
	}

	@Override
	public void log(int level, String message, Throwable exception) {
		logEntry(new LogEntryImpl(
				new LogEntryLocation(this.stackTraceBacktrack),
				null /* service reference */,
				level,
				message,
				exception));
	}

	@Override
	public void log(ServiceReference sr, int level, String message) {
		logEntry(new LogEntryImpl(
				new LogEntryLocation(this.stackTraceBacktrack),
				sr,
				level,
				message,
				null /* exception */));
	}

	@Override
	public void log(ServiceReference sr, int level, String message, Throwable exception) {
		logEntry(new LogEntryImpl(
				new LogEntryLocation(this.stackTraceBacktrack),
				sr,
				level,
				message,
				exception));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Enumeration getLog() {
		return new LogEntryEnumeration(this.memoryLog.iterator());
	}

	@Override
	public void addLogListener(LogListener listener) {
		this.listeners.add(listener);
	}

	@Override
	public void removeLogListener(LogListener listener) {
		this.listeners.remove(listener);
	}

}

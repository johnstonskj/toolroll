package org.johnstonshome.osgi.internal.service.log;

import java.util.Enumeration;
import java.util.Iterator;

import org.osgi.service.log.LogEntry;

public class LogEntryEnumeration implements Enumeration<LogEntry> {

	private Iterator<LogEntry> iterator;
	
	public LogEntryEnumeration(Iterator<LogEntry> iterator) {
		this.iterator = iterator;
	}
	
	@Override
	public boolean hasMoreElements() {
		return this.iterator.hasNext();
	}

	@Override
	public LogEntry nextElement() {
		return this.iterator.next();
	}

}

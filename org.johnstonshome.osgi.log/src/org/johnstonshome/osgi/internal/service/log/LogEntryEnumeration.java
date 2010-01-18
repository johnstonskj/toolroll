/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009-2010. All rights reserved.
 *
 * For full license details, see the file LICENSE inncluded in the
 * distribution of this code.
 *
 */
package org.johnstonshome.osgi.internal.service.log;

import java.util.Enumeration;
import java.util.Iterator;

import org.osgi.service.log.LogEntry;

/**
 * Implementation class, used to implement the enumeration required by the 
 * {@link org.osgi.service.log.LogReaderService} service API.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 */
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

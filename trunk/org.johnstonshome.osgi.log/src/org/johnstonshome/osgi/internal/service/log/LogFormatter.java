/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009-2010. All rights reserved.
 *
 * For full license details, see the file LICENSE inncluded in the
 * distribution of this code.
 *
 */
package org.johnstonshome.osgi.internal.service.log;

import java.text.MessageFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Provides a JDK log formatter to produce a more compact log string.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 */
public class LogFormatter extends Formatter {
		 
	private static final MessageFormat messageFormat = 
		new MessageFormat("[{0,date,h:mm:ss} {1} {2}] {3} {4}\n"); //$NON-NLS-1$
	
	public LogFormatter() {
		super();
	}
	
	@Override 
	public String format(LogRecord record) {
		Object[] arguments = new Object[5];
		arguments[0] = new Date(record.getMillis());
		arguments[1] = record.getLoggerName();
		arguments[2] = record.getLevel();
		arguments[3] = record.getMessage();
		arguments[4] = record.getThrown();
		return messageFormat.format(arguments);
	}	
 
}


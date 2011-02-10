/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009-2010. All rights reserved.
 *
 * For full license details, see the file LICENSE inncluded in the
 * distribution of this code.
 *
 */
package org.johnstonshome.osgi.internal.service.log;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Formatter;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogService;

/**
 * Implementation class, this class implements the OSGi
 * {@org.osgi.service.log.LogListener} interface and logs all received
 * {@@org.osgi.service.log.LogEntry} instances to the JDK logger.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 */
public class LogListenerJdk14 implements LogListener {
	
	private static final String DEFAULT_LOGGER = LogService.class.getPackage().getName();
	
	private Set<String> loggers = new HashSet<String>();
	
	private Handler consoleHandler = new ConsoleHandler();
	private Level consoleLevel = Level.WARNING;
	private Formatter consoleFormatter = new LogFormatter();
	
	public LogListenerJdk14() {
		/*
		 * initialize Java Logging
		 */
		this.consoleHandler.setLevel(this.consoleLevel);
		this.consoleHandler.setFormatter(this.consoleFormatter);
	}

	private Level level(int osgiLevel) {
		switch (osgiLevel) {
		case LogService.LOG_ERROR:
			return Level.SEVERE;
		case LogService.LOG_WARNING:
			return Level.WARNING;
		case LogService.LOG_INFO:
			return Level.INFO;
		case LogService.LOG_DEBUG:
			return Level.FINE;
		default:
			return Level.FINEST;
		}
	}
	
	private void setupLogger(Logger logger) {
		if (!this.loggers.contains(logger.getName())) {
			Handler[] existingHandlers = logger.getHandlers();
			if (existingHandlers.length > 0) {
				for (Handler handler : existingHandlers) {
					if (handler instanceof ConsoleHandler) {
						handler.setLevel(this.consoleLevel);
						handler.setFormatter(this.consoleFormatter);
					}
				}
			} else {
				logger.addHandler(this.consoleHandler);
			}
			this.loggers.add(logger.getName());
		}
	}
	
	@Override
	public void logged(LogEntry entry) {
		Logger logger = null;
		if (entry.getBundle() == null) {
			logger = Logger.getLogger(DEFAULT_LOGGER);
		} else {
			logger = Logger.getLogger(entry.getBundle().getSymbolicName());
		}
		setupLogger(logger);
		if (entry instanceof LogEntryImpl) {
			LogEntryImpl fullEntry = (LogEntryImpl)entry;
			logger.logp(
					level(entry.getLevel()), 
					fullEntry.getLocation().getClassName(), 
					fullEntry.getLocation().getMethodName(), 
					entry.getMessage(),
					entry.getException());
		}
		else {
			logger.logp(
					level(entry.getLevel()), 
					LogEntryLocation.UNKNOWN, 
					LogEntryLocation.UNKNOWN, 
					entry.getMessage(),
					entry.getException());
		}
	}

}

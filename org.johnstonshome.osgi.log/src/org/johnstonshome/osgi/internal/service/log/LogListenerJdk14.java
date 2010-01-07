package org.johnstonshome.osgi.internal.service.log;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogService;

public class LogListenerJdk14 implements LogListener {
	
	private static final String DEFAULT_LOGGER = LogService.class.getPackage().getName();
	
	static {
		System.setProperty("java.util.logging.config.file", "osgi-logging.properties");
	}
	public LogListenerJdk14() {
		/*
		 * initialize Java Logging
		 */
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
	
	@Override
	public void logged(LogEntry entry) {
		Logger logger = null;
		if (entry.getBundle() == null) {
			logger = Logger.getLogger(DEFAULT_LOGGER);
		} else {
			logger = Logger.getLogger(entry.getBundle().getSymbolicName());
		}
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

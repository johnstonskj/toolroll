/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009-2010. All rights reserved.
 *
 * For full license details, see the file LICENSE inncluded in the
 * distribution of this code.
 *
 */
package org.johnstonshome.osgi.internal.service.log;

/**
 * Internal class, captures the location where the log call was made
 * using the usual exception creation trick. This allows us to spoof
 * this location, if available, later.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 */
public class LogEntryLocation {
	
	public static final String UNKNOWN = "unknown";

	private String className = UNKNOWN;
	private String methodName = UNKNOWN;
	private String fileName = UNKNOWN;
	private int lineNumber = 0;
	
	public LogEntryLocation(int backtrack) {
		Throwable exception = new Exception();
		StackTraceElement[] stack = exception.getStackTrace();
		if (stack.length >= backtrack) {
			StackTraceElement caller = stack[backtrack];
			this.className = caller.getClassName();
			this.methodName = caller.getMethodName();
			this.fileName = caller.getFileName();
			this.lineNumber = caller.getLineNumber();
		}
	}

	public String getClassName() {
		return this.className;
	}

	public String getMethodName() {
		return this.methodName;
	}

	public String getFileName() {
		return this.fileName;
	}

	public int getLineNumber() {
		return this.lineNumber;
	}
}

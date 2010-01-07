package org.johnstonshome.osgi.internal.service.log;

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
		return className;
	}

	public String getMethodName() {
		return methodName;
	}

	public String getFileName() {
		return fileName;
	}

	public int getLineNumber() {
		return lineNumber;
	}
}

/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.utils.conf;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This provider looks for possible overrides from the command-line
 * parameters passed to the application. Specifically command-line
 * parameters are specified in the form:
 * 
 * <pre>
 * ... --config.key.nam=config.value
 * </pre>
 * 
 * Where the entire parameter must contain no whitespace, or be escaped
 * according to your system requirements, and must start with some 
 * prefix string.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 */
public class CommandLineConfigProvider implements Configuration {

	/**
	 * The default prefix, and command-line parameter that starts with
	 * this prefix will be parsed to see if it is a legal configuration
	 * setting.
	 */
	public static final String DEFAULT_PREFIX = "--";
	
	private static Pattern pattern = Pattern.compile("(\\S+)=(\\S+)");
	
	private Map<String, String> args = new HashMap<String, String>();

	/**
	 * Construct a new instance with the command-line parameters passed
	 * into this application. This implies that the parameters passed 
	 * into the static function <em>main</em> must be captured.
	 * 
	 * @param args the arguments passed to the static function <em>main</em>. 
	 */
	public CommandLineConfigProvider(final String[] args) {
		this(DEFAULT_PREFIX, args);
	}
	
	/**
	 * Construct a new instance with the command-line parameters passed
	 * into this application. This implies that the parameters passed 
	 * into the static function <em>main</em> must be captured.
	 * 
	 * @param prefix some characters that prefix any legal command-line
	 *         argument, usually <code>"--"</code>
	 * @param args the arguments passed to the static function <em>main</em>. 
	 */
	public CommandLineConfigProvider(final String prefix, final String[] args) {
		for (String arg : args) {
			if (arg.startsWith(prefix)) {
				final String arg2 = arg.substring(prefix.length());
				final Matcher matcher = pattern.matcher(arg2);
				if (matcher.matches()) {
					this.args.put(matcher.group(1), matcher.group(2));
				}
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.johnstonshome.utils.conf.Configuration#getConfigProperty(java.lang.String)
	 */
	@Override
	public String getConfigProperty(final String configKey) {
		return this.args.get(configKey);
	}

	/*
	 * (non-Javadoc)
	 * @see org.johnstonshome.utils.conf.Configuration#getConfigProperty(java.lang.String, java.lang.String)
	 */
	@Override
	public String getConfigProperty(final String configKey, final String defaultValue) {
		final String value = this.getConfigProperty(configKey);
		return (value == null ? defaultValue : value);
	}

}

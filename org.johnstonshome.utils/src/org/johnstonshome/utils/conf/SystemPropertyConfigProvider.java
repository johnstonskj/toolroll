/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.utils.conf;

/**
 * Use the Java system properties to provide configuration data, these values
 * can be specified on the command line with the form:
 * 
 * <pre>
 * java -Dconfig.key.name=config.value
 * </pre>
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 */
public class SystemPropertyConfigProvider implements Configuration {

	/*
	 * (non-Javadoc)
	 * @see org.johnstonshome.utils.conf.Configuration#getConfigProperty(java.lang.String)
	 */
	@Override
	public String getConfigProperty(final String configKey) {
		return System.getProperty(configKey);
	}

	/*
	 * (non-Javadoc)
	 * @see org.johnstonshome.utils.conf.Configuration#getConfigProperty(java.lang.String, java.lang.String)
	 */
	@Override
	public String getConfigProperty(final String configKey, final String defaultValue) {
		return System.getProperty(configKey, defaultValue);
	}
}

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
 * Provide configuration values from environment variables. This allows easy
 * system level overrides of configuration required by the application.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 */
public class EnvironmentConfigProvider implements Configuration {

	/*
	 * (non-Javadoc)
	 * @see org.johnstonshome.utils.conf.Configuration#getConfigProperty(java.lang.String)
	 */
	@Override
	public String getConfigProperty(final String configKey) {
		return System.getenv(configKey);
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

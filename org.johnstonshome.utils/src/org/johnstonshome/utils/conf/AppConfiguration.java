/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.utils.conf;

import java.util.LinkedList;
import java.util.List;

/**
 * Both a {@link Configuration} provider but also the main application client
 * this class contains a chain of providers to which it delegates the 
 * requests to in turn. The first provider in the chain that returns a non-null
 * value will provide the value returned from this class. 
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 */
public class AppConfiguration implements Configuration {

	private List<Configuration> providers = new LinkedList<Configuration>();
	
	/**
	 * Create a default lookup chain using an environment variable provider,
	 * a system properties provider and a property bundle provider. The 
	 * resulting lookup order is as follows:
	 * 
	 * <ol>
	 *   <li>environment variables</li>
	 *   <li>system properties</li>
	 *   <li>Properties file</li>
	 * </ol>
	 * 
	 * Note that we have a simple mechanism for finding the property bundle,
	 * we use the passed-in class to locate a class loader, we use the name
	 * of the class as the bundle name and append ".properties".
	 * 
	 * @param mainClass the class that is the logical main application class,
	 *     this is used to locate and name the property bundle.
	 * @return a new configuration chain.
	 */
	public static AppConfiguration getDefault(final Class<?> mainClass) {
		AppConfiguration config = new AppConfiguration();
		config.push(new PropertyBundleConfigProvider(mainClass));
		config.push(new SystemPropertyConfigProvider());
		config.push(new EnvironmentConfigProvider());
		return config;
	}

	/**
	 * Push a new {@link Configuration} provider onto the head of the chain.
	 * This means it will be used first in the lookup chain.
	 * 
	 * @param provider the provider to add to the lookup chain.
	 */
	public void push(final Configuration provider) {
		this.providers.add(0, provider);
	}

	/**
	 * Add a new {@link Configuration} provider onto the tail of the chain.
	 * This means it will be used last in the lookup chain.
	 * 
	 * @param provider the provider to add to the lookup chain.
	 */
	public void add(final Configuration provider) {
		this.providers.add(provider);
	}

	/*
	 * (non-Javadoc)
	 * @see org.johnstonshome.utils.conf.Configuration#getConfigProperty(java.lang.String)
	 */
	@Override
	public String getConfigProperty(String configKey) {
		String value = null;
		for (final Configuration config : this.providers) {
			value = config.getConfigProperty(configKey);
			if (value != null) {
				break;
			}
		}
		return value;
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

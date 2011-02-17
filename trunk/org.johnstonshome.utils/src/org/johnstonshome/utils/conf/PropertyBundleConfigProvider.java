/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.utils.conf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This provider uses standard Java property resources to hold configuration
 * data. These resources can be looked up either by name, or by using an
 * existing class to provide the resource name and class path. 
 * 
 * Note that if the requested property file does not exist the provider will
 * simply initialize with an empty store. You can tell if the provider
 * initialized successfully by calling {@link #loadedOk()}.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 */
public class PropertyBundleConfigProvider implements Configuration {
	
	private static Logger log = Logger.getLogger(PropertyBundleConfigProvider.class.getCanonicalName());

	private boolean loaded = false;
	private Properties propertyBundle = new Properties();

	/**
	 * Initialize the provider using a string bundle name which will be resolved
	 * by the class loader of this provider instance.
	 * 
	 * @param bundleName the name of the bundle to load.
	 */
	public PropertyBundleConfigProvider(final String bundleName) {
		this(PropertyBundleConfigProvider.class.getClassLoader(), bundleName);
	}
	
	/**
	 * Initialize the provider using a class, the class canonical name will
	 * be used as the name of the bundle, with the suffix ".properties". 
	 * Additionally the class loader from <em>bundleClass</em> will be used
	 * to load the resource.
	 * 
	 * @param bundleClass provides the name of the bundle and the class loader
	 */
	public PropertyBundleConfigProvider(final Class<?> bundleClass) {
		this(bundleClass.getClassLoader(), 
			 bundleClass.getCanonicalName().replace(".", "/") + ".properties");
	}
	
	/**
	 * Initialize the provider using a string bundle name which will be resolved
	 * by the class loader provided.
	 * 
	 * @param loader the loader used to locate the bundle.
	 * @param bundleName the name of the bundle to load.
	 */
	public PropertyBundleConfigProvider(final ClassLoader loader, final String bundleName) {
		InputStream inStream = loader.getResourceAsStream(bundleName);
		try {
			this.propertyBundle.load(inStream);
			this.loaded = true;
		} catch (IOException e) {
			log.log(Level.WARNING, "Could not read property bundle: " + bundleName, e);
		}
	}
	
	/**
	 * Determines whether the provider initialized correctly, and specifically
	 * did the constructor successfully load a property file.
	 * 
	 * @return <code>true</code> if a property file were loaded, else <code>false</code>.
	 */
	public boolean loadedOk() {
		return this.loaded;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.johnstonshome.utils.conf.Configuration#getConfigProperty(java.lang.String)
	 */
	@Override
	public String getConfigProperty(final String configKey) {
		return (this.propertyBundle == null ? null : this.propertyBundle.getProperty(configKey));
	}

	/*
	 * (non-Javadoc)
	 * @see org.johnstonshome.utils.conf.Configuration#getConfigProperty(java.lang.String, java.lang.String)
	 */
	@Override
	public String getConfigProperty(final String configKey, final String defaultValue) {
		return (this.propertyBundle == null ? null : this.propertyBundle.getProperty(configKey, defaultValue));
	}
}

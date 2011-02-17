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
 * This interface defines a provider of configuration data from some well-known
 * source. Implementors, in general, of this interface only provide data from a 
 * single source whereas the primary client access is through the class
 * {@link AppConfiguration} which hosts a chain of configuration providers and
 * so allows one provider to override another and so forth.
 * 
 * Note that there is no restriction on the form of keys, many configuration 
 * systems provide either explicit or implicit hierarchy and it is preferred
 * that the dotted notation common with Java properties be used to identify
 * such hierarchies rather than other separators such as "/" or ":". In this 
 * way such a value can more easily be overridden by an environment variable or
 * system property. 
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 */
public interface Configuration {

	/**
	 * Return the value of the configuration property identified by the key 
	 * <em>configKey</em>. If the key does not exist in the provider then
	 * return <code>null</code>. 
	 * 
	 * @param configKey the key to look up in this provider.
	 * @return the value from the config provider, or <code>null</code> if the
	 *     key is not found.
	 */
	public String getConfigProperty(final String configKey);

	/**
	 * Return the value of the configuration property identified by the key 
	 * <em>configKey</em>. If the key does not exist in the provider then
	 * return the value of <em>defaultValue</em>. 
	 * 
	 * @param configKey the key to look up in this provider.
	 * @param defaultValue the default value to use if the key is not found
	 *     in the store.
	 * @return the value from the config provider, or <the value of
	 *     <em>defaultValue</em>.
	 */
	public String getConfigProperty(final String configKey, final String defaultValue);
}

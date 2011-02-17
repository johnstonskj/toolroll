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
 * This exception is used to denote that an error has occurred reading, parsing
 * or acting on configuration data.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 */
public class ConfigurationException extends Exception {

    private static final long serialVersionUID = 1L;

    public ConfigurationException(String message) {
        super(message);
    }
    
    /**
     * Construct a new exception with a standard "key not found"
     * message.
     * 
     * @param key the configuration data key in error.
     * 
     * @return a new instance of the exception.
     */
    public static ConfigurationException configKeyNotFound(String key) {
        return new ConfigurationException("Configuration value not found for key: " + key);
    }
}

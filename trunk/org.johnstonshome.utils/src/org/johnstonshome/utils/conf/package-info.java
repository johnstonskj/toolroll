/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
/**
 * Configuration utilities used to provide a hierarchical application config
 * system so that property files can easily be overridden by system properties
 * or environment variables.
 * 
 * <h3>Example</h3>
 * 
 * class MyApplication {
 * 
 *     private Configuration config = AppConfiguration.getDefault(MyApplication.class);
 *     
 *     public static void main(String[] args) {
 *         config.push(new CommandLineConfigProvider(args));
 *         
 *         System.out.println("My version: " + config.getConfigProperty("app.version"));
 *     }
 * </pre>
 */
package org.johnstonshome.utils.conf;


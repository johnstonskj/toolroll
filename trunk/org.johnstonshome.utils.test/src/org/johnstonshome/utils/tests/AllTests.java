/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.utils.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Create test suite for all the utility classes.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	FunctionalTests.class,
	FunctionWrapperTests.class,
	FunctionSequenceTests.class,
	MapTests.class,
	PairTests.class,
	StringTests.class,
	NumberTest.class,
	PromiseTests.class
})
public class AllTests {
	// nothing further required
}
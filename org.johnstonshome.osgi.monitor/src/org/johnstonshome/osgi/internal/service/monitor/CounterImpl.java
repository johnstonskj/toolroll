/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.osgi.internal.service.monitor;

import org.johnstonshome.osgi.service.monitor.Counter;

/**
 * 
 * @author Simon Johnston
 *
 */
class CounterImpl extends MonitorImpl implements Counter {
	
	private long value = 0;
	
	public CounterImpl() { }
	
	@Override
	public void decrement() {
		this.value--;
		signalUpdate();
	}

	@Override
	public void decrement(long delta) {
		this.value -= delta;
		signalUpdate();
	}

	@Override
	public long get() {
		return this.value;
	}

	@Override
	public void increment() {
		this.value++;
		signalUpdate();
	}

	@Override
	public void increment(long delta) {
		this.value += delta;
		signalUpdate();
	}

	@Override
	public void set(long value) {
		this.value= value;
		signalUpdate();
	}

	@Override
	public void reset() {
		this.value = 0;
		signalUpdate();
	}
}

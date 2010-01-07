/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.osgi.internal.service.monitor;

import java.math.BigInteger;

import org.johnstonshome.osgi.service.monitor.Statistic;


/**
 * 
 * @author Simon Johnston
 *
 */
class StatisticImpl extends MonitorImpl implements Statistic {

	private BigInteger cumulative = BigInteger.ZERO;
	private long count = 0;
	private long max = 0;
	private long min = 0;
	private double avg = 0;
	
	public StatisticImpl() { }
	
	@Override
	public synchronized void addValue(long value) {
		this.count++;
		this.cumulative = this.cumulative.add(BigInteger.valueOf(value));
		if (this.count == 1) {
			this.min = value;
			this.max = value;
			this.avg = value;
		} else {
			this.min = Math.min(this.min, value);
			this.max = Math.max(this.max, value);
			this.avg = cumulative.divide(BigInteger.valueOf(this.count)).doubleValue();
		}
		signalUpdate();
	}

	@Override
	public double getAverage() {
		return this.avg;
	}

	@Override
	public long getCount() {
		return this.count;
	}

	@Override
	public long getMax() {
		return this.max;
	}

	@Override
	public long getMin() {
		return this.min;
	}

	@Override
	public synchronized void reset() {
		this.cumulative = BigInteger.ZERO;
		this.count = 0;
		this.min = 0;
		this.max = 0;
		this.avg = 0.0;
		signalUpdate();
	}

	@Override
	public String toString() {
		return String.format(
				"{Statistic count:%d min:%d max:%d avg:%f}", 
				this.getCount(),
				this.getMin(),
				this.getMax(),
				this.getAverage());
	}
}

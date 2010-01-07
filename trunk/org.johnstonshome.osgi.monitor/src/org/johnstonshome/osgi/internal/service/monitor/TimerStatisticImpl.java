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

import org.johnstonshome.osgi.service.monitor.Duration;
import org.johnstonshome.osgi.service.monitor.TimerResolution;
import org.johnstonshome.osgi.service.monitor.TimerStatistic;

/**
 * 
 * @author Simon Johnston
 *
 */
public class TimerStatisticImpl extends MonitorImpl implements TimerStatistic {

	private BigInteger cumulative = BigInteger.ZERO;
	private long count = 0;
	private long max = 0;
	private long min = 0;
	private double avg = 0;
	
	private long startTime = 0;
	private long resolutionOffset = 1;
	private TimerResolution resolution = TimerResolution.NANO_SECONDS;

	public TimerStatisticImpl() { }
	
	@Override
	public void abortTimer() {
		this.startTime = 0;
	}

	@Override
	public void startTimer() {
		this.startTime = System.nanoTime();
	}

	@Override
	public void stopTimer() {
		if (this.startTime != 0) {
			this.addValue((System.nanoTime() - this.startTime) / this.resolutionOffset);
			this.startTime = 0;
		}
	}
	
	private synchronized void addValue(long value) {
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


	public TimerResolution getResolution() {
		return this.resolution;
	}
	
	public void setResolution(TimerResolution resolution) {
		this.resolution = resolution;
		switch (this.resolution) {
		case SECONDS:
			this.resolutionOffset = 1000 * 1000;
			break;
		case MILLI_SECONDS:
			this.resolutionOffset = 1000;
			break;
		case NANO_SECONDS:
			this.resolutionOffset = 1;
			break;
		}
	}
	
	@Override
	public Duration getAverage() {
		return new DurationImpl((long)this.avg);
	}

	@Override
	public long getCount() {
		return this.count;
	}

	@Override
	public Duration getMax() {
		return new DurationImpl(this.max);
	}

	@Override
	public Duration getMin() {
		return new DurationImpl(this.min);
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
				"{TimerStatistic count:%d min:%s max:%s avg:%s}", 
				this.getCount(),
				this.getMin().toString(),
				this.getMax().toString(),
				this.getAverage().toString());
	}
}

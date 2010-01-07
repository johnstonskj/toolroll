/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.osgi.internal.service.monitor;

import org.johnstonshome.osgi.service.monitor.TimerResolution;
import org.johnstonshome.osgi.service.monitor.TimerStatistic;

/**
 * 
 * @author Simon Johnston
 *
 */
public class TimerStatisticImpl extends StatisticImpl implements TimerStatistic {

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
}

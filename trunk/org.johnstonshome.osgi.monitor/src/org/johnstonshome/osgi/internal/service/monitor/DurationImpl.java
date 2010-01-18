package org.johnstonshome.osgi.internal.service.monitor;

import org.johnstonshome.osgi.service.monitor.Duration;

public class DurationImpl implements Duration {

	int hours = 0;
	int minutes = 0;
	int seconds = 0;
	long fractional = 0;
	
	public DurationImpl(long duration) {
		long _hours = 0;
		long _minutes = 0;
		long _seconds = 0;
		long _fractional = 0;
		
		_fractional = duration % 1000000;
		_seconds = duration / 1000000;
		if (_seconds >= 60) {
			_minutes = _seconds / 60;
			_seconds = _seconds % 60;
		}
		if (_minutes >= 60) {
			_hours = _minutes / 60;
			_minutes = _minutes % 60;
		}
		this.hours = (int)_hours;
		this.minutes = (int)_minutes;
		this.seconds = (int)_seconds;
		this.fractional = _fractional;
	}
	
	@Override
	public long getFractional() {
		return this.fractional;
	}

	@Override
	public int getHours() {
		return this.hours;
	}

	@Override
	public int getMinutes() {
		return this.minutes;
	}

	@Override
	public int getSeconds() {
		return this.seconds;
	}

	@Override
	public String toString() {
		return String.format(
				"%02d:%02d:%02d.%d", 
				this.hours, 
				this.minutes,
				this.seconds, 
				this.fractional);
	}
}

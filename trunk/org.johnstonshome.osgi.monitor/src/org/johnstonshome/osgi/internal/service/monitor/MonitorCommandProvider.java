package org.johnstonshome.osgi.internal.service.monitor;

import java.util.Collection;
import java.util.Set;

import org.eclipse.osgi.framework.console.CommandInterpreter;
import org.eclipse.osgi.framework.console.CommandProvider;
import org.johnstonshome.osgi.service.monitor.Counter;
import org.johnstonshome.osgi.service.monitor.Monitor;
import org.johnstonshome.osgi.service.monitor.MonitorService;
import org.johnstonshome.osgi.service.monitor.Statistic;
import org.johnstonshome.osgi.service.monitor.TimerStatistic;

public class MonitorCommandProvider implements CommandProvider {

	private static final int    FORMAT_LIMIT = 20;
	private static final String FORMAT_HEADER = "%-20s | %-20s | %6s | %10s | %10s | %10s"; //$NON-NLS-1$
	private static final String FORMAT_LINE   = "%-20s | %-20s | %6d | %10s | %10s | %10s"; //$NON-NLS-1$
	
	private static final String COL_GROUP = Messages.getString("MonitorCommandProvider.output.column.group"); //$NON-NLS-1$
	private static final String COL_NAME = Messages.getString("MonitorCommandProvider.output.column.name"); //$NON-NLS-1$
	private static final String COL_COUNT = Messages.getString("MonitorCommandProvider.output.column.count"); //$NON-NLS-1$
	private static final String COL_MIN = Messages.getString("MonitorCommandProvider.output.column.min"); //$NON-NLS-1$
	private static final String COL_MAX = Messages.getString("MonitorCommandProvider.output.column.max"); //$NON-NLS-1$
	private static final String COL_AVG = Messages.getString("MonitorCommandProvider.output.column.avg"); //$NON-NLS-1$

	private static final String HELP_FORMAT = "%s\n\t%s\t%s\n"; //$NON-NLS-1$
	private static final String HELP_TOPIC = Messages.getString("MonitorCommandProvider.output.help.topic"); //$NON-NLS-1$
	private static final String HELP_CMD = Messages.getString("MonitorCommandProvider.output.help.command"); //$NON-NLS-1$
	private static final String HELP_HELP = Messages.getString("MonitorCommandProvider.output.help.help"); //$NON-NLS-1$

	private static final String EMPTY = ""; //$NON-NLS-1$

	private MonitorService monitorService = null;
	
	public void _monitor(CommandInterpreter ci) throws Exception {		
		System.out.println(
				String.format(
						FORMAT_HEADER,
						COL_GROUP, COL_NAME, COL_COUNT, COL_MIN, COL_MAX, COL_AVG));
		
		String group = ci.nextArgument();
		if (group == null) {
			Set<String> groupNames = this.monitorService.getMonitorGroups();
			for (String groupName : groupNames) {
				enumGroup(groupName);
			}
		} else {
			enumGroup(group);
		}
	}
	
	private void enumGroup(String groupName) {
		Collection<Monitor> monitors = this.monitorService.getMonitorsForGroup(groupName);
		for (Monitor monitor : monitors) {
			output(monitor.getGroup(),
					monitor.getLabel(),
					monitor);
		}
	}
	
	private void output(String group, String name, Monitor monitor) {
		long count = 0;
		String min = EMPTY;
		String max = EMPTY;
		String avg = EMPTY;
		if (monitor instanceof Counter) {
			count = ((Counter)monitor).get();
		}
		else if (monitor instanceof Statistic) {
			count = ((Statistic)monitor).getCount();
			min = String.valueOf(((Statistic)monitor).getMin());
			max = String.valueOf(((Statistic)monitor).getMax());
			avg = String.valueOf(((Statistic)monitor).getAverage());
		}
		else if (monitor instanceof TimerStatistic) {
			count = ((TimerStatistic)monitor).getCount();
			min = ((TimerStatistic)monitor).getMin().toString();
			max = ((TimerStatistic)monitor).getMax().toString();
			avg = ((TimerStatistic)monitor).getAverage().toString();
		}
		System.out.println(
				String.format(
						FORMAT_LINE, 
						group.length() > FORMAT_LIMIT ? 
								group.substring(0, FORMAT_LIMIT) : group, 
						name.length() > FORMAT_LIMIT ? 
								name.substring(0, FORMAT_LIMIT) : name, 
						count,
						min,
						max,
						avg));
	}
	
	@Override
	public String getHelp() {
		return String.format(HELP_FORMAT, HELP_TOPIC, HELP_CMD, HELP_HELP);
	}

	public void bindMonitor(MonitorService monitorService) {
		this.monitorService = monitorService;
	}

	public void unbindMonitor(MonitorService monitorService) {
		this.monitorService = null;
	}
}

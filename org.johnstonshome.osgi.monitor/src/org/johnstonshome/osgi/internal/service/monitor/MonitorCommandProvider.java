package org.johnstonshome.osgi.internal.service.monitor;

import java.util.Collection;
import java.util.Set;

import org.eclipse.osgi.framework.console.CommandInterpreter;
import org.eclipse.osgi.framework.console.CommandProvider;
import org.johnstonshome.osgi.service.monitor.Monitor;
import org.johnstonshome.osgi.service.monitor.MonitorService;

public class MonitorCommandProvider implements CommandProvider {

	private MonitorService monitorService = null;
	
	public void _monitor(CommandInterpreter ci) throws Exception {
		String group = ci.nextArgument();
		System.out.println("Group\t\tName\t\tValues");
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
			System.out.println(
					String.format("%s\t\t%s\t\t%s",
							monitor.getGroup(),
							monitor.getLabel(),
							monitor.toString()));
		}
	}
	
	@Override
	public String getHelp() {
		return "--Monitor Commands--\n\tmonitor [group]\tList all monitors or those for a given group.\n";
	}

	public void bindMonitor(MonitorService monitorService) {
		this.monitorService = monitorService;
	}

	public void unbindMonitor(MonitorService monitorService) {
		this.monitorService = null;
	}
}

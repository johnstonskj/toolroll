/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.osgi.internal.service.monitor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.johnstonshome.osgi.service.monitor.Counter;
import org.johnstonshome.osgi.service.monitor.Monitor;
import org.johnstonshome.osgi.service.monitor.Statistic;


/**
 * This servlet is used to expose the current state of all monitors as
 * a JSON data feed. It is registered by the monitor service itself.
 * 
 * @author Simon Johnston
 *
 */
class MonitorServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private MonitorServiceImpl monitorService = null;

	public MonitorServlet(MonitorServiceImpl monitorService) {
		this.monitorService = monitorService;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter output = resp.getWriter();
		output.println("{");
        output.println("    groups: [");
        for (Iterator<String> groups = this.monitorService.getMonitorGroups().iterator(); groups.hasNext();) {
			String group = groups.next();
            output.println("        {");
            output.println("            group: \"" + group + "\",");
            output.println("            monitors: [");
            for (Iterator<Monitor> monitors = this.monitorService.getMonitorsForGroup(group).iterator(); monitors.hasNext();) {
            	Monitor monitor = monitors.next();
                output.println("                {");
                if (monitor.getServiceReference() != null) {
                	output.println("                    service: \"" + monitor.getServiceReference().toString() + "\"");
                }
                else {
                	output.println("                    service: \"\"");
                }
                output.println("                    name: \"" + monitor.getName() + "\"");
                if (monitor instanceof Counter) {
                    Counter counter = (Counter)monitor;
                    output.println("                    type: \"Counter\",");
                    output.println("                    value: \"" + counter.get() + "\"");
                }
                else if (monitor instanceof Statistic) {
                    Statistic statistic = (Statistic)monitor;
                    output.println("                    type: \"Statistic\",");
                    output.println("                    count: \"" + statistic.getCount() + "\",");
                    output.println("                    min: \"" + statistic.getMin() + "\",");
                    output.println("                    max: \"" + statistic.getMax() + "\",");
                    output.println("                    average: \"" + statistic.getAverage() + "\"");
                }
                if (monitors.hasNext()) {
                	output.println("                },");
                } else {
                	output.println("                }");
                }
            }
            output.println("            ]");
            if (groups.hasNext()) {
            	output.println("        },");
            } else {
            	output.println("        }");
            }
		}
        output.println("    ]");
		output.println("}");
		resp.setCharacterEncoding(JSON.CONTENT_ENCODING);
		resp.setContentType(JSON.CONTENT_TYPE);
		resp.setStatus(200);
	}
}

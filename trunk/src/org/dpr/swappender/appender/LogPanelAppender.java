/**
 *  SwLogger - Swing based appender for log4j
 *  Copyright (C) 2009 dpr
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.dpr.swappender.appender;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;
import org.dpr.swappender.components.LogPanel;
import org.dpr.swappender.models.LogTableModel;
import org.dpr.swappender.utils.LogUtils;


/**
 * Appender for logTable
 * 
 * @author Christophe Roger
 * 
 */
public class LogPanelAppender extends AppenderSkeleton implements SwLogAppender {

	private static final Log log = LogFactory.getLog(LogPanelAppender.class);
	protected LogPanel panel = null;
	protected LogTableModel model = null;

	protected Layout layout = null;
	public static final String newline = System.getProperty("line.separator");

	public LogPanelAppender() {
		super();
	}

	/**
	 * Constructor with layout and table
	 * 
	 * @param layout
	 * @param table
	 */
	public LogPanelAppender(Layout layout, LogPanel panel) {

		name = "LogTableAppender";
		setLayout(layout);
		setLogPanel(panel);
	}

	/**
	 * Constructor with table and default layout
	 * 
	 * @param table
	 */
	public LogPanelAppender(LogPanel panel) {

		layout = new PatternLayout(LogUtils.DEFAULT_LOG_PATTERN);
		setLayout(layout);
		setLogPanel(panel);
	}

	public void close() {
	}

	public boolean requiresLayout() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.log4j.AppenderSkeleton#append(org.apache.log4j.spi.LoggingEvent
	 * )
	 */
	public void append(LoggingEvent event) {
		// System.out.println(((PatternLayout) layout).getConversionPattern());
		if (panel == null) {
			panel = getLogPanel();
		}
		String text = this.layout.format(event);
		String trace[];

		if (panel == null) {
			log.error("The log panel is null !");
		}
		getModel().addLine(text);

		if ((trace = event.getThrowableStrRep()) != null) {
			for (int i = 0; i < trace.length; i++) {
				getModel().addDummy((trace[i]));
			}
		}
		// cause the table to automatically
		// scroll to the bottom.
		panel.getTableLog().scroll();
		// worker.execute();

	}

	/**
	 * @return
	 */
	private LogTableModel getModel() {
		if (panel != null) {
			return (LogTableModel) panel.getTableLog().getModel();
		}
		return null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.log4j.AppenderSkeleton#getLayout()
	 */
	public Layout getLayout() {
		return layout;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.log4j.AppenderSkeleton#setLayout(org.apache.log4j.Layout)
	 */
	public void setLayout(Layout layout) {
		this.layout = layout;

	}

	/**
	 * @return the LogPanel element
	 */
	public LogPanel getLogPanel() {
		if (panel == null && getLayout() != null) {
			panel = new LogPanel(
					((PatternLayout) layout).getConversionPattern());
		}
		return panel;
	}

	/**
	 * Set the table element
	 * 
	 * @param table
	 */
	public void setLogPanel(LogPanel panel) {
		this.panel = panel;
	}

	// Background task for loading images.
	// SwingWorker worker = new SwingWorker<String, Void>() {
	// @Override
	// public String doInBackground() {
	//
	// return null;
	// }
	//
	// @Override
	// public void done() {
	// //Remove the "Loading images" label.
	//
	// try {
	// get();
	// //getModel().fireTableDataChanged();
	// //panel.getTableLog().scroll();
	// } catch (InterruptedException ignore) {}
	// catch (java.util.concurrent.ExecutionException e) {
	// String why = null;
	// Throwable cause = e.getCause();
	// if (cause != null) {
	// why = cause.getMessage();
	// } else {
	// why = e.getMessage();
	// }
	// System.err.println("Error retrieving file: " + why);
	// }
	// }
	// };

}

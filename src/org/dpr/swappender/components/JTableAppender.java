package org.dpr.swappender.components;

/**
 * The JTextAreaAppender is a log4j appender, adapted from the TextPaneAppender
 * by Sven Reimers.  This adapter allows log4j log statements to be logged
 * to a JTextArea.
 *
 * @author Mark Evans
 */

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Category;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;
import org.dpr.swappender.models.LogTableModel;

/**
 * Appender for logTable
 * 
 * @author C. Roger
 * 
 */
public class JTableAppender extends AppenderSkeleton {

	protected LogTable table = null;
	protected LogTableModel model = null;

	protected Layout layout = null;
	public static final String newline = System.getProperty("line.separator");

	public JTableAppender(Layout layout, LogTable table) {

		name = "JTableAppender";
		setLayout(layout);
		setJTable(table);
	}

	public void close() {
	}

	public boolean requiresLayout() {
		return true;
	}

	public void append(LoggingEvent event) {
		String text = this.layout.format(event);
		String trace[];

		if (table == null) {

		}
		getModel().addLine(text);

		if ((trace = event.getThrowableStrRep()) != null) {
			for (int i = 0; i < trace.length; i++) {
				getModel().addDummy((trace[i]));
			}
		}
		// cause the window to automatically
		// scroll to the bottom.
		table.scroll();

	}

	private LogTableModel getModel() {
		if (table != null) {
			return (LogTableModel) table.getModel();
		}
		return null;

	}

	public Layout getLayout() {
		return layout;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	public LogTable getTable() {
		return table;
	}

	public void setJTable(LogTable table) {
		this.table = table;
	}

}

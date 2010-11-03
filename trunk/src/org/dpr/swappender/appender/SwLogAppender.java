/**
 * 
 */
package org.dpr.swappender.appender;

import org.apache.log4j.Appender;
import org.dpr.swappender.components.LogPanel;

/**
 * Public interface for swing appenders
 * 
 * @author Christophe Roger
 * 
 */
public interface SwLogAppender extends Appender {

	/**
	 * @return the LogPanel element
	 */
	public LogPanel getLogPanel();

	/**
	 * Set the LogPanel element
	 * 
	 * @param LogPanel
	 */
	public void setLogPanel(LogPanel panel);
}

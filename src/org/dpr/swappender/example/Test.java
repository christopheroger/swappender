/**
 *  SwAppender - Swing based appender for log4j
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
package org.dpr.swappender.example;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * 
 * @author Christophe Roger
 * 
 */
public class Test {
	public boolean running=true;
	Log log = LogFactory.getLog(Test.class);

	public void addLog() {
		long l = System.currentTimeMillis();
		while (running) {
			long temp = System.currentTimeMillis();
			if (temp > l + 1000) {
				log.error("klmkkklmk");
				l = temp;
			}
		}
	}

	public void stop() {
		running=false;
		
	}
}

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
package org.dpr.swappender.components;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import org.dpr.swappender.utils.LogUtils;

/**
 * Cell Renderer for log level in jtable
 * 
 * @author Christophe Roger
 * 
 */
public class LevelCellRenderer extends DefaultTableCellRenderer {

	public LevelCellRenderer() {
		super();
		LogUtils.initImageIcons();
	}

	/*
	 * @see TableCellRenderer#getTableCellRendererComponent(JTable, Object,
	 * boolean, boolean, int, int)
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		String levelStr = (String) value;
		Icon icon = LogUtils.getLevelImageIcon(levelStr);

		setIcon(icon);
		this.setHorizontalAlignment(SwingConstants.CENTER);
		return this;
	}

}

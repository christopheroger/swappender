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

import java.awt.Rectangle;
import java.io.File;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import org.dpr.swappender.models.LineModel;
import org.dpr.swappender.models.LogTableLine;
import org.dpr.swappender.models.LogTableModel;
import org.dpr.swappender.utils.LogUtils;

/**
 * JTable appended with logs
 * 
 * @author Christophe Roger
 * 
 */
public class LogTable extends JTable {

	private static final long serialVersionUID = 3890806135306929479L;

	// TODO: height shouldnt be here
	private static final int ROW_HEIGHT = 30;
	private static final int ICON_MARGIN = 8;
	public boolean scrolling = true;
	TableRowSorter<LogTableModel> sorter;
	RowFilter<LogTableModel, Integer> infoFilter;
	RowFilter<LogTableModel, Integer> warnFilter;
	RowFilter<LogTableModel, Integer> errorFilter;

	LogTableModel model;

	public LogTable() {
		super();

	}

	public LogTable(String log_pattern) {

		super();
		model = new LogTableModel(log_pattern);
		LineModel lineModel = model.getLineModel();
		sorter = new TableRowSorter<LogTableModel>(model);
		this.setModel(model);
		this.setRowSorter(sorter);
		this.setRowHeight(ROW_HEIGHT);

		// found level column: (icon column)
		if (lineModel.getLevelColumn() >= 0) {
			TableColumn column = this.getColumnModel().getColumn(
					lineModel.getLevelColumn());

			column.setResizable(false);
			column.setCellRenderer(new LevelCellRenderer());
			column.setMaxWidth(LogUtils.getMaxIconWidth() + ICON_MARGIN);
		}
		// found date column
		if (lineModel.getDateColumn() >= 0) {
			TableColumn column = this.getColumnModel().getColumn(
					lineModel.getDateColumn());

			int width = lineModel.getDateBounds(getFont());

			column.setPreferredWidth(width);
			column.setMaxWidth(width);

		}

		setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);

	}

	/**
	 * autoscroll to last line
	 */
	public void scroll() {
		if (scrolling && getRowCount() > 0) {
			setRowSelectionInterval(getRowCount() - 1, getRowCount() - 1);
			scrollRectToVisible(new Rectangle(0, getRowCount() * getRowHeight()
					- getRowHeight(), 50, 3 * getRowHeight()));
		}

	}

	/**
	 * @param scrolling
	 */
	public void setScrolling(boolean scrolling) {
		this.scrolling = scrolling;
	}

	public TableRowSorter<LogTableModel> getSorter() {
		return sorter;
	}

	public RowFilter<LogTableModel, Integer> getInfoFilter() {
		if (infoFilter == null) {
			infoFilter = createFilter("INFO");
		}
		return infoFilter;
	}

	public RowFilter<LogTableModel, Integer> getWarnFilter() {
		if (warnFilter == null) {
			warnFilter = createFilter("WARN");
		}
		return warnFilter;
	}

	public RowFilter<LogTableModel, Integer> getErrorFilter() {
		if (errorFilter == null) {
			errorFilter = createFilter("ERROR", "FATAL");
		}
		return errorFilter;
	}

	/**
	 * create filter on level
	 * 
	 * @param level
	 * @return
	 */
	public RowFilter<LogTableModel, Integer> createFilter(final String level) {
		RowFilter<LogTableModel, Integer> filter = new RowFilter<LogTableModel, Integer>() {
			public boolean include(
					Entry<? extends LogTableModel, ? extends Integer> entry) {
				LogTableModel logModel = entry.getModel();
				LogTableLine line = logModel.getTableLine(
						entry.getIdentifier(), model.getLineModel()
								.getLevelColumn());
				if (line.getLevel().equals(level)) {
					return true;
				}
				return false;
			}
		};
		return filter;
	}

	/**
	 * create filter on level
	 * 
	 * @param level1
	 * @param level2
	 * @return
	 */
	public RowFilter<LogTableModel, Integer> createFilter(final String level1,
			final String level2) {
		RowFilter<LogTableModel, Integer> filter = new RowFilter<LogTableModel, Integer>() {
			public boolean include(
					Entry<? extends LogTableModel, ? extends Integer> entry) {
				LogTableModel logModel = entry.getModel();
				LogTableLine line = logModel.getTableLine(
						entry.getIdentifier(), model.getLineModel()
								.getLevelColumn());
				if (line.getLevel().equals(level1)
						|| line.getLevel().equals(level2)) {
					return true;
				}
				return false;
			}
		};
		return filter;
	}

	/**
	 * Append file content to log panel
	 * 
	 * @param file
	 * @param pattern
	 */
	public void appendFile(File file, String pattern) {
		model.appendFile(file, pattern);

	}

	// public LineModel getLineModel() {
	// return lineModel;
	// }

	// private String[] convertLine(String line, LineModel lm) {
	// String[] sortedLine = new String[getLineModel().getColumnLength()];
	// // TODO: use specific splitcar for file
	// String[] splittedLine = line.split(LogUtils.SPLITCHAR_REGEX);
	//
	// for (int i = 0; i < getLineModel().getColumnLength(); i++) {
	// for (int j = 0; j < getLineModel().getColumnLength(); j++) {
	// if (getLineModel().getColumnHeaders()[j].equals(lm
	// .getColumnHeaders()[i])) {
	// sortedLine[i] = splittedLine[j];
	// }
	// }
	// }
	// return sortedLine;
	// }

}

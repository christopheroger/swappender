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
package org.dpr.swappender.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Level;
import org.dpr.swappender.utils.LogUtils;

/**
 * Table model
 * 
 * @author Christophe Roger
 * 
 */
public class LogTableModel extends AbstractTableModel {

	private String[] columnNames;

	private List<String> data = new ArrayList<String>();

	private LineModel lineModel;

	protected ImageIcon[] imgs;

	public LogTableModel(String[] columnNames) {
		this.columnNames = columnNames;

	}

	/**
	 * New Constructor
	 * 
	 * @param log_pattern
	 */
	public LogTableModel(String log_pattern) {
		lineModel = new LineModel(log_pattern);
		this.columnNames = lineModel.getColumnHeaders();
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return data.size();

	}

	public Object getValueAt(int row, int col) {
		String ligne = data.get(row);
		String[] elements = ligne.split(LogUtils.SPLITCHAR_REGEX);
		if (col <= elements.length - 1) {
			return elements[col];
		}
		return "";
	}

	public Object getRowValueAt(int row) {

		String ligne = data.get(row);

		return ligne;
	}

	public void addLine(String row) {
		data.add(row);
		delayedFireTableDataChanged();
	}

	public void addLine2(String row) {
		data.add(row);

	}

	public void clear() {
		data = null;
		data = new ArrayList<String>();
		delayedFireTableDataChanged();
	}

	public void addDummy(String string) {
		// TODO Auto-generated method stub

	}

	public LogTableLine getTableLine(int row, int levelCol) {
		return new LogTableLine(getRowValueAt(row), levelCol);
	}

	/**
	 * Used to append a log file in log table
	 * 
	 * @param file
	 * @param pattern
	 */
	public void appendFile(File file, String pattern) {

		try {
			final File logFile = file;
			if (logFile != null) {

				if (logFile.exists() && logFile.canRead()) {
					final BufferedReader rdr = new BufferedReader(
							new FileReader(logFile));
					LineModel lm = new LineModel(pattern);
					String line = null;
					while ((line = rdr.readLine()) != null) {
						String convertedLine = convertLines(line, lm);
						// addLine(convertedLine);
						data.add(convertedLine);
					}
					delayedFireTableDataChanged();
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Convert lines for file appending
	 * 
	 * @param line
	 * @param lm
	 * @return
	 */
	private String convertLines(String line, LineModel lm) {
		StringBuffer buffer = new StringBuffer();
		// TODO: use specific splitcar for file
		String[] splittedLine = line.split(LogUtils.SPLITCHAR_REGEX);
		int colLength = getLineModel().getColumnLength();
		for (int i = 0; i < colLength; i++) {
			for (int j = 0; j < colLength; j++) {
				if (getLineModel().getColumnHeaders()[j].equals(lm
						.getColumnHeaders()[i])) {
					if (j < splittedLine.length) {
						buffer.append(splittedLine[j].trim());
					} else {
						buffer.append("empty log");
					}
					if (i < colLength - 1) {
						buffer.append(LogUtils.SPLITCHAR);
					}

				}
			}
		}
		return buffer.toString();
	}

	/**
	 * @return the columnNames
	 */
	public String[] getColumnNames() {
		return columnNames;
	}

	/**
	 * @param columnNames
	 *            the columnNames to set
	 */
	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}

	/**
	 * @return the lineModel
	 */
	public LineModel getLineModel() {
		return lineModel;
	}

	/**
	 * @param lineModel
	 *            the lineModel to set
	 */
	public void setLineModel(LineModel lineModel) {
		this.lineModel = lineModel;
	}

	/**
	 * Invokes fireTableDataChanged after all the pending events have been
	 * processed. SwingUtilities.invokeLater is used to handle this.
	 */
	protected void delayedFireTableDataChanged() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				fireTableDataChanged();
			}
		});
	}

}

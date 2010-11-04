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
package org.dpr.swappender.models;

import java.awt.Font;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dpr.swappender.utils.LogUtils;

/**
 * Define struture of a table row
 * 
 * @author Christophe Roger
 * 
 */
public class LineModel {

	int levelColumn = -1;

	int dateColumn = -1;
	int dateColumnWidth = -1;
	int columnLength = 0;
	String datePattern;
	private String[] columnHeaders;

	public LineModel(String logPattern) {
		String[] pattern = logPattern.split(LogUtils.SPLITCHAR_REGEX);
		List<String> colonnes = new ArrayList<String>();
		for (int i = 0; i < pattern.length; i++) {
			String newPattern = pattern[i].trim();
			// level
			if (newPattern.startsWith("%")) { // its a log4j pattern !
				if (newPattern.endsWith("p")) {
					if (newPattern.length() == 2) {
						levelColumn = i;
						colonnes.add(LogUtils.getLocale("column.level"));
					} else {
						if (newPattern.length() > 2) {
							String patternFormat = newPattern.substring(1,
									newPattern.length() - 1);
							try {
								Integer.parseInt(patternFormat);
				levelColumn = i;
				colonnes.add(LogUtils.getLocale("column.level"));
							} catch (NumberFormatException e) {
								// not a string: not a level format
							}
						}
					}
			}
				// date
				if (newPattern.startsWith("%d")) {
				dateColumn = i;
				colonnes.add(LogUtils.getLocale("column.date"));
					Pattern p = Pattern.compile("(\\%d\\{)(.*)(\\})",
							Pattern.CASE_INSENSITIVE);
					Matcher m = p.matcher(pattern[i]);

					if (m.matches() && m.groupCount() == 3) {
						datePattern = m.group(2);
					}
			}

				// message
				if (newPattern.startsWith("%m")) {
				colonnes.add(LogUtils.getLocale("column.message"));
					// System.out.println("message");
			}
				// TODO: not tested
				if (newPattern.startsWith("%X")) {
					Pattern p = Pattern.compile("(\\%X\\{)(.*)(\\})",
							Pattern.CASE_INSENSITIVE);
					Matcher m = p.matcher(pattern[i]);

					if (m.matches() && m.groupCount() == 3) {
						String key = m.group(2);// pattern[i].substring(3,
						// pattern[i].length() - 1);
						colonnes.add(LogUtils.getLocale("column.message.ext."
								+ key));

					}
				}
			}

		}
		columnLength = colonnes.size();
		columnHeaders = new String[colonnes.size()];

		colonnes.toArray(columnHeaders);
	}

	/**
	 * @return the levelColumn
	 */
	public int getLevelColumn() {
		return levelColumn;
	}

	/**
	 * @param levelColumn
	 *            the levelColumn to set
	 */
	public void setLevelColumn(int levelColumn) {
		this.levelColumn = levelColumn;
	}

	/**
	 * @return the dateColumn
	 */
	public int getDateColumn() {
		return dateColumn;
	}

	/**
	 * @param dateColumn
	 *            the dateColumn to set
	 */
	public void setDateColumn(int dateColumn) {
		this.dateColumn = dateColumn;
	}

	/**
	 * @return the dateColumnWidth
	 */
	public int getDateColumnWidth() {
		return dateColumnWidth;
	}

	/**
	 * @param dateColumnWidth
	 *            the dateColumnWidth to set
	 */
	public void setDateColumnWidth(int dateColumnWidth) {
		this.dateColumnWidth = dateColumnWidth;
	}

	/**
	 * @return the columnHeaders
	 */
	public String[] getColumnHeaders() {
		return columnHeaders;
	}

	/**
	 * @param columnHeaders
	 *            the columnHeaders to set
	 */
	public void setColumnHeaders(String[] columnHeaders) {
		this.columnHeaders = columnHeaders;
	}

	public int getColumnLength() {

		return columnLength;
	}

	/**
	 * @param columnLength
	 *            the columnLength to set
	 */
	public void setColumnLength(int columnLength) {
		this.columnLength = columnLength;
	}

	/**
	 * Calculate cell width (trying)
	 * 
	 * @param font
	 * @return
	 */
	public int getDateBounds(Font font) {
		Date date = new Date();
		Format formatter = new SimpleDateFormat(datePattern);
		String s = formatter.format(date);
		return s.length() * 7;
		// TODO:Have to use metrics but how ?
		// FontRenderContext frc = g.getFontRenderContext();
		// TextLayout layout = new TextLayout("This is a string", font, frc);
	}

}

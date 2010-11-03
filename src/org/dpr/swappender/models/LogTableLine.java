package org.dpr.swappender.models;

public class LogTableLine {

	private int levelColumn;

	// String level;
	String[] columns;

	public LogTableLine(Object value, int levelCol) {
		columns = ((String) value).split("\\|");
		levelColumn = levelCol;
	}

	public String getLevel() {
		return columns[levelColumn];
	}

}

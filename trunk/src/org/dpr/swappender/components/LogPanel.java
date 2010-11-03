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
package org.dpr.swappender.components;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import org.dpr.swappender.models.LogTableModel;
import org.dpr.swappender.utils.Config;
import org.dpr.swappender.utils.LogUtils;

/**
 * A panel with a table, filters and detail area
 * 
 * @author Christophe Roger
 * 
 */
public class LogPanel extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1079022248849360837L;

	private AbstractAction toolBarAction = new ToolBarAction();

	private LogTable tableLog = null;

	private JPanel detailPanel;

	enum ActionBar {
		CLEAR, INFO, ERROR, WARN, FIX, ALL
	};

	/**
	 * Constructor with default pattern
	 */
	public LogPanel() {

		tableLog = createTableLog(LogUtils.DEFAULT_LOG_PATTERN);
		createPanel();

	}

	public LogPanel(String log_pattern) {

		tableLog = createTableLog(log_pattern);
		createPanel();

	}

	public LogPanel(String log_pattern, LogTable table) {

		tableLog = table;
		createPanel();

	}

	private void createPanel() {
		JScrollPane scrollPane = new JScrollPane(tableLog);
		// JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
		// JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		JToolBar toolBar = new JToolBar("ToolBar");
		toolBar.setFloatable(false);
		addToolButtons(toolBar);
		toolBar.setAlignmentX(LEFT_ALIGNMENT);
		// BoxLayout bl = new BoxLayout(this, BoxLayout.Y_AXIS);
		BorderLayout bl = new BorderLayout();
		setLayout(bl);
		String buttonPos = Config.getProperty("buttons.position", "top");
		if (buttonPos.equals("top")){
			add(toolBar, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		add(getDetailPanel(), BorderLayout.SOUTH);
		}
		if (buttonPos.equals("bottom")){
			add(toolBar, BorderLayout.SOUTH);
		add(scrollPane, BorderLayout.NORTH);
		add(getDetailPanel(), BorderLayout.CENTER);
		}
	}

	private LogTable createTableLog(String log_pattern) {
		if (tableLog == null) {
			tableLog = new LogTable(log_pattern);
			tableLog.addMouseListener(this);
		}
		return tableLog;
	}

	public LogTable getTableLog() {
		return tableLog;
	}

	protected void addToolButtons(JToolBar toolBar) {
		AbstractButton button = null;

		ButtonGroup group = new ButtonGroup();
		boolean isShowText = Boolean.parseBoolean(Config.getProperty("display.text",
		"true"));
		// first button
		button = makeToolButton("television", ActionBar.ALL,
				LogUtils.getLocale("button.all.tooltip"),
				LogUtils.getLocale("button.all"), true,isShowText);
		group.add(button);
		toolBar.add(button);

		if (Boolean.parseBoolean(Config.getProperty("display.level-error",
				"true"))) {
			button = makeToolButton("level-error", ActionBar.ERROR,
					LogUtils.getLocale("button.error.tooltip"),
					LogUtils.getLocale("button.error"), true,isShowText);
			group.add(button);
			toolBar.add(button);
		}
		if (Boolean.parseBoolean(Config.getProperty("display.level-warn",
				"true"))) {
			button = makeToolButton("level-warn", ActionBar.WARN,
					LogUtils.getLocale("button.warn.tooltip"),
					LogUtils.getLocale("button.warn"), true,isShowText);
			group.add(button);
			toolBar.add(button);
		}
		if (Boolean.parseBoolean(Config.getProperty("display.level-info",
				"true"))) {
			button = makeToolButton("level-info", ActionBar.INFO,
					LogUtils.getLocale("button.info.tooltip"),
					LogUtils.getLocale("button.info"), true,isShowText);
			group.add(button);
			toolBar.add(button);
		}
		if (Boolean.parseBoolean(Config.getProperty("display.clear", "true"))) {
			button = makeToolButton("clear", ActionBar.CLEAR,
					LogUtils.getLocale("button.clear.tooltip"),
					LogUtils.getLocale("button.clear"), false,isShowText);
			toolBar.add(button);
			toolBar.addSeparator();
		}
		if (Boolean.parseBoolean(Config.getProperty("display.fix", "false"))) {
			button = makeToolButton("fix", ActionBar.FIX,
					LogUtils.getLocale("button.fixscroll.tooltip"),
					LogUtils.getLocale("button.fixscroll"), true,isShowText);
			toolBar.add(button);
			toolBar.addSeparator();
		}

	}

	protected AbstractButton makeToolButton(String imageName,
			ActionBar actionCommand, String toolTipText, String altText,
			boolean isToggle, boolean showtext) {
		// Look for the image.
		String imgLocation = "/images/" + imageName + ".png";
		URL imageURL = LogPanel.class.getResource(imgLocation);
		AbstractButton button = null;
		// Create and initialize the button.
		if (isToggle) {
			button = new JToggleButton();
		} else {
			button = new JButton();
		}
		button.setActionCommand(actionCommand.toString());
		button.setToolTipText(toolTipText);
		button.addActionListener(toolBarAction);

		if (imageURL != null) { // image found
			button.setIcon(new ImageIcon(imageURL, altText));
		}
		if (altText != null && showtext) { // no image found
			button.setText(altText);
			// System.err.println("Resource not found: " + imgLocation);
		}

		return button;
	}

	public class ToolBarAction extends AbstractAction {

		private static final long serialVersionUID = 8668681928077113956L;

		public void actionPerformed(ActionEvent event) {
			String command = event.getActionCommand();
			if (command.equals(ActionBar.CLEAR.toString())) {
				getModel().clear();
			}
			if (command.equals(ActionBar.INFO.toString())) {
				getTableLog().getSorter().setRowFilter(
						getTableLog().getInfoFilter());
			}
			if (command.equals(ActionBar.WARN.toString())) {
				getTableLog().getSorter().setRowFilter(
						getTableLog().getWarnFilter());
			}

			if (command.equals(ActionBar.ERROR.toString())) {
				getTableLog().getSorter().setRowFilter(
						getTableLog().getErrorFilter());
			}

			if (command.equals(ActionBar.ALL.toString())) {
				getTableLog().getSorter().setRowFilter(null);
			}

			if (command.equals(ActionBar.FIX.toString())) {
				getTableLog().setScrolling(
						!((JToggleButton) event.getSource()).isSelected());
			}

			if (command.equals("CHOOSE_OUT")) {
				JFileChooser jfc2 = new JFileChooser();

				if (jfc2.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

				}
			}
			//
		}
	}

	public JPanel getDetailPanel() {
		if (detailPanel == null) {
			detailPanel = new DetailPanel();

		}
		return detailPanel;
	}

	private LogTableModel getModel() {
		if (tableLog != null) {
			return (LogTableModel) tableLog.getModel();
		}
		return null;

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {

		LogTable table = (LogTable) e.getSource();
		Point p = e.getPoint();
		if (e.getClickCount() == 2) {

			int row = table.rowAtPoint(p);
			// int col = table.columnAtPoint(p);
			// int[] selection = table.getSelectedRows();

			// table.convertRowIndexToModel(selection[0]);

			String value = (String) ((LogTableModel) table.getModel())
					.getRowValueAt(table.convertRowIndexToModel(row));

			((DetailPanel) detailPanel).setValue(value, ((LogTableModel) table
					.getModel()).getLineModel().getLevelColumn());
		}

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}

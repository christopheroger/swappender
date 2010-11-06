package org.dpr.swappender.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Appender;
import org.apache.log4j.PatternLayout;
import org.dpr.swappender.appender.LogPanelAppender;
import org.dpr.swappender.components.LogPanel;
import org.dpr.swappender.utils.LogUtils;

/**
 * SwLog Example
 * 
 * @author CRR
 * 
 */
public class LogFrame extends JFrame {

	//
	public static Log log = LogFactory.getLog(LogFrame.class);

	private JPanel jContentPane;

	private JPanel jPanel;

	private LogPanel panelLog = null;

	public LogFrame() {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();

	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		log.error("start");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				LogFrame frame = new LogFrame();

				frame.setVisible(true);
			}
		});

	}

	protected void init() {

		this.setContentPane(getJContentPane());

		this.pack();
		//this.setVisible(true);

	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {

			jContentPane = new JPanel();
			BorderLayout bl = new BorderLayout();
			jContentPane.setLayout(bl);
			jContentPane.add(getJPanel(), BorderLayout.CENTER);

		}
		return jContentPane;
	}

	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setBackground(Color.WHITE);
			BoxLayout bl = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
			jPanel.setLayout(bl);

			jPanel.add(getLogPanel());


		}
		return jPanel;
	}

	private LogPanel getLogPanel() {

		if (panelLog == null) {
			panelLog = LogUtils.getLogPanel(log);
		}
		return panelLog;
	}



}

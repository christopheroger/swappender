package org.dpr.swappender.example;

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
import org.apache.log4j.Layout;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.dpr.swappender.appender.LogPanelAppender;
import org.dpr.swappender.components.LogPanel;

/**
 * SwLog Example
 * 
 * @author CRR
 * 
 */
public class LogFrameThreadExample extends JFrame {

	private static String APP_LOG_PATTERN = "%p|%d{dd MMM yyyy HH:mm:ss}|%m%n";
	//
	public static Log log = LogFactory.getLog("");

	private JPanel jContentPane;

	private JPanel jPanel;

	private LogPanel panelLog = null;
	private boolean isRunning=false;
	Test t = null;
	public LogFrameThreadExample() {
		super();

		this.setTitle("SwLog Demo");
		 t = new Test();
	}


	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				LogFrameThreadExample frame = new LogFrameThreadExample();

				frame.init();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});

	}

	protected void init() {

		this.setContentPane(getJContentPane());
		Logger logger = LogManager.getRootLogger();

		Layout layout = new PatternLayout(APP_LOG_PATTERN);

		LogPanelAppender appender = new LogPanelAppender(layout, panelLog);
		logger.addAppender(appender);

		log.info("Test information: clickme: you can put a lot of text in the log. This text is displayed in the detail Area and is wrapped "
				+ "if is too long.\nyou can also set text on new lines\nif you want.");
		log.warn("test warn ");

		this.pack();
		this.setVisible(true);

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
			JButton jb = new JButton("TEST");
			jb.setActionCommand("START");
			jb.addActionListener(new MyAction());
			jPanel.add(jb);

		}
		return jPanel;
	}

	private LogPanel getLogPanel() {

		if (panelLog == null) {
			panelLog = new LogPanel(APP_LOG_PATTERN);
		}
		return panelLog;
	}

	public void framePack() {
		this.pack();

	};

	public class MyAction extends AbstractAction {

		public void actionPerformed(ActionEvent event) {
			String command = event.getActionCommand();
			if (command.equals("START")) {
				if (!isRunning){
					isRunning = true;
				  new Thread(new Runnable() {
				      public void run() {
//							SwingUtilities.invokeLater(new Runnable() {
//								public void run() {
//									
									t.addLog();
							//	}
						//	});
				      }
				  }).start();
				  
				}else{
					isRunning = false;
					t.stop();
				}
				

			}
			if (command.equals("STOP")) {

			}
		}
	}

}

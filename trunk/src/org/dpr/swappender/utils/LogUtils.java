package org.dpr.swappender.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.Appender;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dpr.swappender.appender.LogPanelAppender;
import org.dpr.swappender.appender.SwLogAppender;
import org.dpr.swappender.components.LogPanel;

/**
 * @author Christophe Roger
 * 
 */
public class LogUtils {

	private static final Log log = LogFactory.getLog(LogUtils.class);
	private static final String ROOT_STRING = "level-";
	public static final String IMG_PATH = "/images/";
	public static String DEFAULT_LOG_PATTERN = "%p|%d{dd MMM yyyy HH:mm:ss}|%m%n";

	public static String SPLITCHAR_REGEX = "\\|";
	public static String SPLITCHAR = "|";

	private static Properties properties;

	private static String[] levels = new String[] { "FATAL", "ERROR", "WARN",
			"INFO", "DEBUG" };
	private static Map<String, Icon> imageIcons;


	public static Icon getLevelImageIcon(String levelStr) {
		return imageIcons.get(levelStr.toLowerCase());
	}

	public static Icon getLevelImageIcon2(String levelStr) {
		String imgName = ROOT_STRING + levelStr.toLowerCase() + ".png";
		URL imageURL = LogUtils.class.getResource(IMG_PATH + File.separator
				+ imgName);
		if (imageURL != null) {
			Icon ic = new ImageIcon(imageURL);
			return ic;
		} else {
			log.debug("cant't find resource: " + IMG_PATH + File.separator
					+ imgName);
		}
		return null;
	}		
	public void init() {
		initImageIcons();
	}

	public static void initImageIcons() {
		if (imageIcons == null) {
			imageIcons = new HashMap<String, Icon>();
			for (String level : levels) {
				String imgName = ROOT_STRING + level.toLowerCase() + ".png";
				URL imageURL = LogUtils.class.getResource(IMG_PATH + File.separator + imgName);
				if (imageURL != null) {
					imageIcons
							.put(level.toLowerCase(), new ImageIcon(imageURL));
				}else {
					log.debug("cant't find resource: " + IMG_PATH + File.separator
							+ imgName);
				}
			}
		}

	}

	public static int getMaxIconWidth() {
		Collection<Icon> icons = imageIcons.values();
		int width = 0;
		for (Icon icone : icons) {
			if (icone.getIconWidth() > width) {
				width = icone.getIconWidth();
			}
		}
		return width;

	}

	public static String getLevelText(String levelStr) {
		return getLocale("level." + levelStr.trim().toLowerCase());
	}

	public static String getLocale(String key) {
		ResourceBundle messages = ResourceBundle.getBundle("locales.locale",
				Locale.getDefault());
		String value = messages.getString(key);
		return value;

	}





	public static String getAppender(String logger1) {
		Enumeration<?> enu1 = LogManager.getCurrentLoggers();
		while (enu1.hasMoreElements()) {

			Logger logger = (Logger) enu1.nextElement();
			System.out.println("logger:" + logger.getName());
			Enumeration<?> enu = logger.getAllAppenders();
			while (enu.hasMoreElements()) {
				Appender app = (Appender) enu.nextElement();
				System.out.println("appender:" + app.getName());
				if (app instanceof LogPanelAppender) {
					System.out.println("tourrve");
					System.out.println(app);
				}
			}

		}

		return null;
	}

	/**
	 * @param log
	 * @return
	 * @deprecated
	 */
	@SuppressWarnings("unused")
	private static Appender getLogAppenderOld(Log log) {
		Logger logger = null;
		if (log != null && log instanceof Log4JLogger) {
			logger = ((Log4JLogger) log).getLogger();
			return getAppender(logger);
		}

		return null;
	}

	public static SwLogAppender getLogAppender(Log log) {
		Logger logger = null;
		if (log != null && log instanceof Log4JLogger) {
			logger = ((Log4JLogger) log).getLogger();
			return getAppender(logger);
		}

		return null;
	}

	/**
	 * @param logger
	 * @return
	 * @deprecated
	 */
	@SuppressWarnings("unused")
	private static Appender getAppenderOld(Logger logger) {

		Enumeration<?> enu = logger.getAllAppenders();
		while (enu.hasMoreElements()) {
			Appender app = (Appender) enu.nextElement();
			System.out.println("appender:" + app.getName());
			if (app instanceof LogPanelAppender) {
				System.out.println("trouve");
				return app;
			}
		}
		if (logger.getParent() != null) {
			return getAppender((Logger) logger.getParent());
		}
		return null;

	}

	private static SwLogAppender getAppender(Logger logger) {

		Enumeration<?> enu = logger.getAllAppenders();
		while (enu.hasMoreElements()) {
			Appender app = (Appender) enu.nextElement();
			if (app instanceof SwLogAppender) {
				return (SwLogAppender) app;
			}
		}
		if (logger.getParent() != null) {
			return getAppender((Logger) logger.getParent());
		}
		return null;

	}

	public static LogPanel getLogPanel(Log log) {
		SwLogAppender app = getLogAppender(log);
		return app.getLogPanel();

	}
	public static URL getImageURL(String imageName) {
		String imgLocation = IMG_PATH + File.separator + imageName;
		URL imageURL = LogUtils.class.getResource(imgLocation);
		if (imageURL == null) {
			log.debug("cant't find resource: " + imgLocation);
		}
		return imageURL;
	}
	

}

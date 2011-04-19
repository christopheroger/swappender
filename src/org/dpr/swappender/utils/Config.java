package org.dpr.swappender.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Config {

	static Properties propertyFile;

	static String defaultFileName = "swappender.properties";

	private static final Log log = LogFactory.getLog(Config.class);

	public static void load() {
		if (propertyFile == null) {
			propertyFile = new Properties();

			File currentDir = null;
			File cfgFile = null;
			InputStream in = null;
			String message = "swappender property file loaded";
			try {
				currentDir = new File(new File(".").getCanonicalPath());
				cfgFile = new File(currentDir, defaultFileName);
				if (cfgFile == null || !cfgFile.exists()) {
					message = "internal swappender property file loaded";
					in = Config.class.getClassLoader().getResourceAsStream(
							"config/" + defaultFileName);

				} else {
					in = new FileInputStream(cfgFile);
				}

				propertyFile.load(in);
				// close ?
				//log.debug(message);
			} catch (IOException e) {
				log.debug("swappender property file load error", e);
				throw new RuntimeException(e);
			}

		}

		// return propertyFile;
	}

	public static void save() {
		// if (userConfig == null) {
		// return;
		// }
		//
		// try {
		// userConfig.save();
		// } catch (ConfigurationException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	public static String getProperty(String key) {
		load();
		return propertyFile.getProperty(key);
	}

	public static String getProperty(String key, String value) {
		load();
		return propertyFile.getProperty(key, value);
	}

}

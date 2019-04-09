package com.sample.jms.toolkit;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class Config {

	private static Properties pps;
	private static String CONFIG_FILE = "/config/config.properties";

	public static String getValue(String key) {
		if (pps == null) {
			File config = new File(CONFIG_FILE);
			if (!config.exists()) {
				File jar = new File(Config.class.getProtectionDomain().getCodeSource().getLocation().getFile());
				config = new File(jar.getParentFile().getParentFile() + CONFIG_FILE);
			}
			pps = new Properties();
			try {
				pps.load(new FileInputStream(config));
			} catch (Exception e) {
				throw new RuntimeException("error loading " + CONFIG_FILE, e);
			}
		}
		return pps.getProperty(key);
	}

}

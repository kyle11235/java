package com.example.util;

import java.util.Properties;

public class Config {

	private static Properties pps;

	public static synchronized void init() {
		if (pps == null) {
			pps = new Properties();
			try {
				pps.load(Config.class.getResourceAsStream("/config.properties"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String getValue(String key) {
		if (pps == null) {
			init();
		}
		return pps.getProperty(key);
	}
}

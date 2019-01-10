package com.oracle.hcm.util;

import java.util.Properties;

public class Config {

	private static Properties pps;

	public static String getValue(String key) {
		if (pps == null) {
			pps = new Properties();
			try {
				pps.load(Config.class.getResourceAsStream("/config.properties"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return pps.getProperty(key);
	}

}

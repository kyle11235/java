package com.example.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class SchedulerConfig extends Config {

	public static final String SCHEDULER_FILE = "scheduler.properties";

	private static Properties pps;

	public static synchronized void init() {

		Config.init();

		if (pps == null) {
			pps = new Properties();
			try {
				pps.load(new FileInputStream(CONFIG_HOME + File.separator + SCHEDULER_FILE));
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

	public static synchronized void setValue(String key, String value) {
		if (pps == null) {
			init();
		}
		try {
			FileOutputStream out = new FileOutputStream(CONFIG_HOME + File.separator + SCHEDULER_FILE);
			pps.setProperty(key, value);
			pps.store(out, null);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static synchronized Properties copy() {
		if (pps == null) {
			init();
		}
		Properties out = new Properties();
		out.putAll(pps);
		return out;
	}

}

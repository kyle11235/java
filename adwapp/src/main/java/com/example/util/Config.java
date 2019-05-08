package com.example.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

public class Config {

	public static final String ENV_CONFIG_HOME = "ENV_CONFIG_HOME";
	public static final String CONFIG_FILE = "config.properties";
	public static File CONFIG_HOME;
	public static final String DIR_CONFIG = ".adw";

	private static Properties pps;

	public static synchronized void init() {
		if (pps == null) {
			pps = new Properties();
			try {
				// read env
				String configHome = System.getenv(ENV_CONFIG_HOME);
				System.out.println(ENV_CONFIG_HOME + "=" + configHome);
				
				if (configHome != null) {
					CONFIG_HOME = new File(configHome);
				} else {
					// use home dir
					CONFIG_HOME = new File(FileUtils.getUserDirectory(), DIR_CONFIG);
				}
				System.out.println("Config home will be " + CONFIG_HOME.getAbsolutePath());
				
				// read config
				pps.load(new FileInputStream(CONFIG_HOME + File.separator + CONFIG_FILE));

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

package com.example.rest.ociapi;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.example.util.*;

public class MockOCIAPI {

	
	private static final CopyOnWriteArrayList<DB> list = new CopyOnWriteArrayList<>();

    static {
      
        DB db = new DB("id123", "test", "AVAILABLE", "1", "", "");
        list.add(db);
       
  }

	public static List<DB> listAutonomousDatabases(String compartmentID) {
		for (int i = 0; i < list.size(); i++) {
			DB db = list.get(i);
			String value = SchedulerConfig.getValue(db.getID() + "-" + MyScheduler.ACTION_START);
			if(value != null) {
				db.setCronStart(value);
			}
			value = SchedulerConfig.getValue(db.getID() + "-" + MyScheduler.ACTION_STOP);
			if(value != null) {
				db.setCronStop(value);
			}
		}
		return list;
	}

	public static String updateAutonomousDatabase(String dbID, Integer cpuCount) throws UnsupportedEncodingException {
		if (dbID == null) {
			System.out.println("invalid dbID=" + dbID);
			return "invalid dbID";
		}
		if (cpuCount <= 0 || cpuCount > Integer.parseInt(Config.getValue("oci.maxCPUCount"))) {
			System.out.println("invalid cpuCount=" + cpuCount);
			return "invalid cpuCount";
		}
		System.out.println("updated=" + dbID);
		return "updated";
	}

	public static String startAutonomousDatabase(String dbID) throws UnsupportedEncodingException {
		if (dbID == null) {
			System.out.println("invalid dbID=" + dbID);
			return "invalid dbID";
		}
		System.out.println("started=" + dbID);
		return "started";
	}

	public static String stopAutonomousDatabase(String dbID) throws UnsupportedEncodingException {
		if (dbID == null) {
			System.out.println("invalid dbID=" + dbID);
			return "invalid dbID";
		}
		System.out.println("stopped=" + dbID);
		return "stopped";
	}

	
}
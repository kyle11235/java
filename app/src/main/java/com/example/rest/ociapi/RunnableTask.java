package com.example.rest.ociapi;

import java.io.UnsupportedEncodingException;

import com.example.rest.ociapi.OCIAPI;

class RunnableTask implements Runnable {
	private String dbID;
	private String cron;
	private String action;

	public RunnableTask(String dbID, String cron, String action) {
		this.dbID = dbID;
		this.cron = cron;
		this.action = action;
	}

	@Override
	public void run() {
		try {
			System.out.println("begin run scheduled task... ");
			System.out.println("dbID=" + dbID + ", cron=" + cron + ", action=" + action);
			if (MyScheduler.ACTION_START.equals(action)) {
				OCIAPI.startAutonomousDatabase(dbID);
			} else if (MyScheduler.ACTION_STOP.equals(action)) {
				OCIAPI.stopAutonomousDatabase(dbID);
			}
			System.out.println("end run scheduled task");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
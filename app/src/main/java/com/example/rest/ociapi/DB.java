/* Copyright © 2016 Oracle and/or its affiliates. All rights reserved. */

package com.example.rest.ociapi;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DB {

	private String id;
	private String dbName;
	private String lifecycleState;
	private String cpuCoreCount;

	private String cronStart;
	private String cronStop;

	public DB() {

	}

	public DB(String id, String dbName, String lifecycleState, String cpuCoreCount, String cronStart, String cronStop) {
		this.id = id;
		this.dbName = dbName;
		this.lifecycleState = lifecycleState;
		this.cpuCoreCount = cpuCoreCount;
		this.cronStart = cronStart;
		this.cronStop = cronStop;
	}

	public String getID() {
		return this.id;
	}

	public String getDbName() {
		return this.dbName;
	}

	public String getLifecycleState() {
		return this.lifecycleState;
	}

	public String getCpuCoreCount() {
		return this.cpuCoreCount;
	}

	public String getCronStart() {
		return this.cronStart;
	}

	public String getCronStop() {
		return this.cronStop;
	}

	public void setCronStart(String cron) {
		this.cronStart = cron;
	}

	public void setCronStop(String cron) {
		this.cronStop = cron;
	}

}

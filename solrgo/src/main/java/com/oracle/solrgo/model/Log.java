package com.oracle.solrgo.model;

import java.util.Date;

public class Log {

	private String sid;
	private Date time;
	private LogTypeEnum type;
	private String value;

	public Log(){
		this.time = new Date();
	}
	
	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public LogTypeEnum getType() {
		return type;
	}

	public void setType(LogTypeEnum type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}

package com.oracle.solrgo.controller;

import javax.inject.Singleton;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.apache.log4j.Logger;

import com.oracle.solrgo.model.Log;
import com.oracle.solrgo.service.LogService;

@Path("/log")
@Singleton
public class LogController {

	private static Logger logger = Logger.getLogger(LogController.class);
	private LogService logService = new LogService();

	@POST
	@Path("/")
	public void log(Log log) {
		logger.debug("log controller");
		logService.saveLog(log);
	}

}

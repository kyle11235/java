package com.oracle.solrgo.service;

import org.apache.log4j.Logger;

import com.oracle.solrgo.dao.LogDao;
import com.oracle.solrgo.model.Log;

public class LogService {

	private static Logger logger = Logger.getLogger(LogService.class);
	private LogDao logDao = new LogDao();
	
	public void saveLog(Log log) {
		if (log == null) {
			return;
		}
		logger.debug(log.getSid());
		logger.debug(log.getTime());
		logger.debug(log.getType());
		logger.debug(log.getValue());
		logDao.saveLog(log);
	}
}

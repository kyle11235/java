package com.oracle.solrgo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.oracle.solrgo.model.Log;
import com.oracle.solrgo.model.LogTypeEnum;

public class LogDao extends BaseDao {

	private static Logger logger = Logger.getLogger(LogDao.class);
	private static String SAVE_LOG = "insert into web_log values(?, TO_DATE(?, 'yyyy-MM-dd hh24:mi'), ?, ?)";
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public void saveLog(Log log) {
		logger.debug("saving log");
		Connection connection = null;
		try {
			connection = super.getConnection();
			PreparedStatement statement = connection.prepareStatement(SAVE_LOG);
			statement.setString(1, log.getSid());
			statement.setString(2, dateFormat.format(log.getTime()));
			statement.setString(3, log.getType().toString());
			statement.setString(4, log.getValue());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.debug("save log done");
	}

	public static void main(String[] args) {
		Log log = new Log();
		log.setSid("123");
		log.setTime(new Date());
		log.setType(LogTypeEnum.SEARCH);
		log.setValue("oracle");
		new LogDao().saveLog(log);

		// System.out.println(LogDao.dateFormat.format(new Date()));
	}

}

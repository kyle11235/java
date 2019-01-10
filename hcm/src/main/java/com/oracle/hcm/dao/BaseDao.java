package com.oracle.hcm.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.alibaba.druid.pool.DruidDataSource;
import com.oracle.hcm.util.Config;

public class BaseDao {
	
	private static DruidDataSource datasource;

	public static Connection getOaaConnection() throws SQLException {
		if (datasource == null) {
			datasource = new DruidDataSource();
			datasource.setUrl(Config.getValue("db.url"));
			datasource.setUsername(Config.getValue("db.username"));
			datasource.setPassword(Config.getValue("db.password"));
			datasource.setInitialSize(Integer.parseInt(Config.getValue("db.initSize")));
			datasource.setMaxActive(Integer.parseInt(Config.getValue("db.maxSize")));
		}
		return datasource.getConnection();
	}
}

package com.oracle.hcm.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.alibaba.druid.pool.DruidDataSource;
import com.oracle.hcm.util.Config;

public class BaseDao {
	
	// a pool
	private static DruidDataSource ds = null;

	public static synchronized void init() throws SQLException {
		if (ds == null) {
			ds = new DruidDataSource();
			ds.setUrl(Config.getValue("db.url"));
			ds.setUsername(Config.getValue("db.username"));
			ds.setPassword(Config.getValue("db.password"));
			ds.setInitialSize(Integer.parseInt(Config.getValue("db.initSize")));
			ds.setMinIdle(Integer.parseInt(Config.getValue("db.minSize")));
			ds.setMaxActive(Integer.parseInt(Config.getValue("db.maxSize")));
			// warm up
			ds.getConnection();
		}
	}

	public Connection getOaaConnection() throws SQLException {
		if (ds == null) {
			init();
		}
		return ds.getConnection();
	}

	
}

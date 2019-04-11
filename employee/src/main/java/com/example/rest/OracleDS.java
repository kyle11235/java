package com.example.rest;

import java.sql.Connection;
import java.sql.SQLException;

import com.example.util.Config;

import oracle.jdbc.pool.OracleDataSource;

public class OracleDS {

	// a pool
	private static OracleDataSource ds = null;

	public static synchronized void init() throws SQLException {
		if (ds == null) {
			ds = new OracleDataSource();
			ds.setURL(Config.getValue("db.url"));
			ds.setUser(Config.getValue("db.username"));
			ds.setPassword(Config.getValue("db.password"));

			java.util.Properties pps = new java.util.Properties();
			pps.setProperty("InitialLimit", Config.getValue("db.initSize"));    
			pps.setProperty("MinLimit", Config.getValue("db.minSize"));    
			pps.setProperty("MaxLimit", Config.getValue("db.maxSize"));    
			ds.setConnectionProperties(pps);
		    ds.setImplicitCachingEnabled(true);
		    // warm up
			ds.getConnection();
			
		}
	}

	public Connection getConnection() throws SQLException {
		if (ds == null) {
			init();
		}
		return ds.getConnection();
	}

	// public static void main(String[] args) throws SQLException {

	// 	// test connection
	// 	System.out.println(new OracleDS().getConnection());

	// }

}

package db;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

import util.Config;

public class DBCP implements DS {

	// a pool
	private static BasicDataSource ds = null;

	public static synchronized void init() throws SQLException {
		if (ds == null) {
			ds = new BasicDataSource();
			ds.setUrl(Config.getValue("db.url"));
			ds.setUsername(Config.getValue("db.username"));
			ds.setPassword(Config.getValue("db.password"));
			ds.setInitialSize(Integer.parseInt(Config.getValue("db.initSize")));
			ds.setMaxIdle(Integer.parseInt(Config.getValue("db.minSize")));
			ds.setMaxIdle(Integer.parseInt(Config.getValue("db.maxSize")));
			// warm up
			ds.getConnection().close();
		}
	}

	public Connection getConnection() throws SQLException {
		if (ds == null) {
			init();
		}
		return ds.getConnection();
	}

	public static void main(String[] args) throws SQLException {

		// test connection
		System.out.println(new OracleDS().getConnection());

	}

}

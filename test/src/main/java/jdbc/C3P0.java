package jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import util.Config;

public class C3P0 implements MyDS {

	// a pool
	private static ComboPooledDataSource ds = null;

	public static synchronized void init() throws SQLException {
		if (ds == null) {
			ds = new ComboPooledDataSource();
			ds.setJdbcUrl(Config.getValue("db.url"));
			ds.setUser(Config.getValue("db.username"));
			ds.setPassword(Config.getValue("db.password"));
			ds.setInitialPoolSize(Integer.parseInt(Config.getValue("db.initSize")));
			ds.setMinPoolSize(Integer.parseInt(Config.getValue("db.minSize")));
			ds.setMaxPoolSize(Integer.parseInt(Config.getValue("db.maxSize")));
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

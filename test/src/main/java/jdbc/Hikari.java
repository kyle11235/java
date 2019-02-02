package jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import util.Config;

public class Hikari implements MyDS {

	// a pool
	private static HikariDataSource ds = null;

	public static synchronized void init() throws SQLException {
		if (ds == null) {
			HikariConfig config = new HikariConfig();
			config.setJdbcUrl(Config.getValue("db.url"));
			config.setUsername(Config.getValue("db.username"));
			config.setPassword(Config.getValue("db.password"));
			config.setMinimumIdle(Integer.parseInt(Config.getValue("db.initSize")));
			// TODO min ?
			config.setMaximumPoolSize(Integer.parseInt(Config.getValue("db.maxSize")));
			config.setRegisterMbeans(true);
			ds = new HikariDataSource(config);
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

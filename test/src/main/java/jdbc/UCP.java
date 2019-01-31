package jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;
import util.Config;

public class UCP implements MyDS{

	// a pool
	private static PoolDataSource ds = null;

	public static synchronized void init() throws SQLException {
		if (ds == null) {
			ds = PoolDataSourceFactory.getPoolDataSource();
			ds.setConnectionFactoryClassName(Config.getValue("db.ucp.factoryName"));
			ds.setURL(Config.getValue("db.url"));
			ds.setUser(Config.getValue("db.username"));
			ds.setPassword(Config.getValue("db.password"));
			ds.setInitialPoolSize(Integer.parseInt(Config.getValue("db.initSize")));
			ds.setMinPoolSize(Integer.parseInt(Config.getValue("db.minSize")));
			ds.setMaxPoolSize(Integer.parseInt(Config.getValue("db.maxSize")));
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

	public static void main(String[] args) throws SQLException {

		// test connection
		System.out.println(new OracleDS().getConnection());

	}
}

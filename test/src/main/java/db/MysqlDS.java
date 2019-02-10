
package db;

import java.sql.Connection;
import java.sql.SQLException;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

import util.Config;

public class MysqlDS implements DS{

	// an interface
	private static MysqlDataSource ds = null;

	public static synchronized void init() throws SQLException {
		if (ds == null) {
			ds = new MysqlConnectionPoolDataSource();
			ds.setURL(Config.getValue("db.url"));
			ds.setUser(Config.getValue("db.username"));
			ds.setPassword(Config.getValue("db.password"));
			// TODO seems no init/min/max - -!
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
		System.out.println(new MysqlDS().getConnection());

	}

}

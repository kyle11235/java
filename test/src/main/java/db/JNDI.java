package db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import util.Config;

public class JNDI {

	// a pool
	private static DataSource ds = null;

	public static synchronized void init() throws SQLException, NamingException {
		if (ds == null) {
			InitialContext ctx = new InitialContext();
			ds = (DataSource) ctx.lookup(Config.getValue("db.jndi.name"));
			// warm up
			ds.getConnection().close();
		}
	}

	public Connection getConnection() throws SQLException, NamingException {
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

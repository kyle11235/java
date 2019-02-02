package jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;
import util.Config;
//import weblogic.jndi.Environment;

public class RemoteJNDI {

	// a pool
	private static DataSource ds = null;

	public static synchronized void init() throws SQLException, NamingException {
		if (ds == null) {
//			Environment environment = new Environment();
//			environment.setInitialContextFactory(environment.DEFAULT_INITIAL_CONTEXT_FACTORY);
//			environment.setProviderURL(Config.getValue("db.jndi.url"));
//			environment.setSecurityPrincipal(Config.getValue("db.jndi.username"));
//			environment.setSecurityCredentials(Config.getValue("db.jndi.password"));
//			Context ctx = environment.getInitialContext();
//			ds = (DataSource) ctx.lookup(Config.getValue("db.jndi.name"));
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

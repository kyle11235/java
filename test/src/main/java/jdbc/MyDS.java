package jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public interface MyDS {

	public Connection getConnection() throws SQLException;
	
}

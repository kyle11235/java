package db;

import java.sql.Connection;
import java.sql.SQLException;

public interface DS {

	public Connection getConnection() throws SQLException;
	
}

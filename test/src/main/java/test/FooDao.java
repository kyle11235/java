package test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.MysqlDS;
import util.Config;

// extends
public class FooDao extends MysqlDS {

	public void foo() {
		String sql = Config.getValue("db.testsql");
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = super.getConnection();
			statement = connection.prepareStatement(sql);
			statement.execute();
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString(1) + "," + rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(statement != null) {
				try {
					statement.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(connection != null) {
				try {
					// close/return to pool
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {

		new FooDao().foo();

	}

}

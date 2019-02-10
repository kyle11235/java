package jvm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.Druid;
import util.Config;

public class Statement extends Druid implements Leaker {

	public void leak() {
		String sql = Config.getValue("db.testsql");
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = super.getConnection();
			statement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					// close/return to pool
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}

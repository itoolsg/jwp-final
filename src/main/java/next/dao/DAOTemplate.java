package next.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import next.support.db.ConnectionManager;

public abstract class DAOTemplate {

	protected Connection con;
	protected String query;
	protected PreparedStatement statement;

	DAOTemplate(String query, Object... objects) {
		try {
			connect();
			this.query = query;
			statement = con.prepareStatement(query);
			for (int i = 0; i < objects.length; ++i) {
				statement.setObject(i + 1, objects[i]);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			close();
		}

	}

	public Connection connect() {
		con = ConnectionManager.getConnection();
		return con;
	}

	public void close() {
		try {
			if(con != null)
				con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

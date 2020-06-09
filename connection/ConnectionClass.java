package com.comtrade.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {
	
	private volatile static ConnectionClass connection;
	private Connection sqlConnection;
	private static Object mutex = new Object();

	public static ConnectionClass getConnection() {
		return connection;
	}

	public Connection getSqlConnection() {
		return sqlConnection;
	}

	private ConnectionClass() {
		
	}

	public static ConnectionClass getInstance() {
		if (connection == null) {
			synchronized (mutex) {
				if (connection == null) {
					connection = new ConnectionClass();
				}

			}

		}
		return connection;

	}
	
	public void startTransaction() {
		try {
			sqlConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant_manager?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root","");
			sqlConnection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void commitTransaction() {
		try {
			sqlConnection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void closeConnection() {
		try {
			sqlConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void rollbackTransaction() {
		try {
			sqlConnection.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}

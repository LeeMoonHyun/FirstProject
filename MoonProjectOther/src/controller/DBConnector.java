package controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnector {

	static final String driver = "oracle.jdbc.driver.OracleDriver";
	static final String url = "jdbc:oracle:thin:@localhost:1521:myoracle";

	public static Connection getConnection() throws Exception {

		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, "scott", "tiger");
		return conn;

	}

}

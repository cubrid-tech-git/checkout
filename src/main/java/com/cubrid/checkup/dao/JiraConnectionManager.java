package com.cubrid.checkup.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 
 * @author HUN
 *
 *	DB Connection Class - MySQL
 */
public class JiraConnectionManager {
	public Connection getConnection() {
		String tech_driver = null;
		String tech_url = null;
		String tech_dbuser = null;
		String tech_dbpass = null;
		
		Connection tech_conn = null;
		
		try {
			InputStream inputStream = this.getClass().getResourceAsStream("/db.properties");
			Properties properties = new Properties();
			properties.load(inputStream);

			tech_driver = (String) properties.get("tech_driver");
			tech_url = (String) properties.get("tech_url");
			tech_dbuser = (String) properties.get("tech_dbuser");
			tech_dbpass = (String) properties.get("tech_dbpass");
			
			Class.forName(tech_driver);
			tech_conn = DriverManager.getConnection(tech_url, tech_dbuser, tech_dbpass);
			tech_conn.setAutoCommit(false);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(tech_driver + "\n" + tech_url + "\n" + tech_dbuser + "\n" + tech_dbpass);
		System.out.println("JiraConnectionManager: " + tech_conn);
		
		return tech_conn;
	}
}

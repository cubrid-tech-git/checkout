package com.cubrid.checkup.dao;

import java.io.File;
import java.io.FileInputStream;
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
 *	DB Connection Class - CUBRID : REGCHEDB
 */
public class RegchedbConnectionManager {
	public Connection getConnection() {
		String checkout_driver = null;
		String checkout_url = null;
		String checkout_dbuser = null;
		String checkout_dbpass = null;
		
		Connection checkout_conn = null;
		
		try {
			InputStream inputStream = this.getClass().getResourceAsStream("/db.properties");
			Properties properties = new Properties();
			properties.load(inputStream);

			checkout_driver = (String) properties.get("checkout_driver");
			checkout_url = (String) properties.get("checkout_url");
			checkout_dbuser = (String) properties.get("checkout_dbuser");
			checkout_dbpass = (String) properties.get("checkout_dbpass");
			
			Class.forName(checkout_driver);
			checkout_conn = DriverManager.getConnection(checkout_url, checkout_dbuser, checkout_dbpass);
			checkout_conn.setAutoCommit(false);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(checkout_driver + "\n" + checkout_url + "\n" + checkout_dbuser + "\n" + checkout_dbpass);
		System.out.println("RegchedbConnectionManager: " + checkout_conn);
		
		return checkout_conn;
	}
	
	
	
	
	
	
	
	
}

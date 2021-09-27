package com.revature.connection;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;

// Class that represents a connection with the database
public class ConnectionFactory{
	
	private BasicDataSource ds;
	private static final ConnectionFactory connection_factory = new ConnectionFactory();
	
	static {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private ConnectionFactory() {
		
		try {
			Properties props = new Properties();
			props.load(new FileReader("src/main/resources/application.properties"));
			ds = new BasicDataSource();
			ds.setUrl(props.getProperty("url"));
			ds.setUsername(props.getProperty("username"));
			ds.setPassword(props.getProperty("password"));
			ds.setMinIdle(5);
			ds.setMaxIdle(10);
			ds.setMaxOpenPreparedStatements(100);
			
		} catch (IOException e) {
			// cannot find properties file
		}
	}
	
	// retrieve a current static instance of the ConnectionFactory class
	public static ConnectionFactory getInstance() {
		return connection_factory;
	}
	
	// retrieve a connection to the database
	public Connection getConnection() {
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
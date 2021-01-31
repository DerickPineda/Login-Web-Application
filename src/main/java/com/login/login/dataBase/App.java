package com.login.login.dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/* This class will be used to connect to the 
 * postgreSQL database
 * 
 * I'm assuming that it is also used to write queries
 */
public class App {
	
	//Class variables
	private final String url = "jdbc:postgresql://localhost:5432/login";
	private final String user = "postgres";
	private final String password = "1";
	
	//Method used to connect to PostgreSQL database
	public Connection connect() {
		Connection connection = null;
		try {
			
			//Here we establish a connection object (which I assume establishes the connection to the db)
			connection = DriverManager.getConnection(url, user, password);
			//Print out that we made the connection
			System.out.println("\nEstablished Connection to Database!");
			
			} catch(SQLException exception) {
			System.out.println(exception.getMessage());
		}
		
		return connection;
	}
}

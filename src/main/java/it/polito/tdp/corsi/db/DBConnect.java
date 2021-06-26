package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	//conterrà un unico metodo statico per ottenere connessioni al database
	
	public static Connection getConnection() throws SQLException {
		String jdbcURL = "jdbc:mysql://localhost/iscritticorsi?user=root&password=mari";
		return DriverManager.getConnection(jdbcURL);
	}

}

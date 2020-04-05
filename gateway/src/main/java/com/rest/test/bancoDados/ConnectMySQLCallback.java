package com.rest.test;

import java.sql.*;

public class ConnectMySQLCallback  {
    public Connection conn;
    public static ConnectMySQLCallback instance=null;

    // Initializes singleton: connect to MySQL database
    public ConnectMySQLCallback() {

	System.out.println("CRIOU A CONEXAO COM O BANCO DE DADOS DO MANIOT STORAGE NORMAL");

        // JDBC driver name and database URL
        String driverName = "com.mysql.jdbc.Driver";
        String serverName = "150.164.10.108:3306";
        String myDatabase = "maniot";
        String url = "jdbc:mysql://localhost:3306/maniot";
        // Database credentials
        String username = "root";
        String password = "maniot";

        try {
            // Register JDBC driver
            Class.forName(driverName);
            // Open a connection
            conn = DriverManager.getConnection(url, username, password);
            // Driver not found

        } catch (ClassNotFoundException cnfex) {
            System.out.println(cnfex.getMessage());
            // Not able to connect to the database
        } catch (SQLException sqlex) {
            System.out.println(sqlex.getMessage());
            // Other error
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ConnectMySQLCallback getInstance(){
	if(instance==null){
	    return new ConnectMySQLCallback();
	}
	return instance;
    }

    // Close the database connection
    public static int closeConnection(Connection connection) {
        try {
            connection.close();
            return 1;
        } catch (SQLException sqlex) {
            System.out.println(sqlex.getMessage());
            return 0;
        }
    }

    // Close the database connection
    public int close() {
        try {
            conn.close();
            return 1;
        } catch (SQLException sqlex) {
            System.out.println(sqlex.getMessage());
            return 0;
        }
    }
}

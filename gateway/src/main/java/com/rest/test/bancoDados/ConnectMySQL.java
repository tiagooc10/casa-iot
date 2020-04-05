package com.rest.test;

import java.sql.*;
import java.io.IOException;
import java.beans.PropertyVetoException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectMySQL  {

    private static ConnectMySQL instance;

    private ComboPooledDataSource cpds;

    // Initializes singleton: connect to MySQL database
    private ConnectMySQL() throws PropertyVetoException {

	System.out.println("VAI CRIAR A POOL DE CONEXÕES");

        // JDBC driver name and database URL
        String driverName = "com.mysql.cj.jdbc.Driver";
        String serverName = "150.164.10.108:3306";
        String myDatabase = "maniot";
        String url = "jdbc:mysql://localhost:3306/maniot";
        // Database credentials
        String username = "root";
        String password = "cruzeiro1312";

	cpds = new ComboPooledDataSource();
        cpds.setDriverClass(driverName); //loads the jdbc driver
        cpds.setJdbcUrl("jdbc:mysql://localhost:3306/maniot?autoReconnect=true&useSSL=false");
        cpds.setUser(username);
        cpds.setPassword(password);

        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(100);
        cpds.setMaxStatements(180);

    }

    public static ConnectMySQL getInstance() throws IOException, SQLException, PropertyVetoException {
        if (instance == null) {
            instance = new ConnectMySQL();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
	System.out.println("Retornando Conexao");
        return this.cpds.getConnection();
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

    /*// Close the database connection
    public int close() {
        try {
            conn.close();
            return 1;
        } catch (SQLException sqlex) {
            System.out.println(sqlex.getMessage());
            return 0;
        }
    }*/
}

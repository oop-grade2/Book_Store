package com.mycompany.bookstore.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// url for most ms sql server is "jdbc:sqlserver://hostname\\instance:port;databseName=dbName"
public class DBConnection { 
    private static final String url = "jdbc:sqlserver://REHAM-PC\\SQLEXPRESS"
            + ":1433;databaseName=Book_Store;encrypt=true;trustServerCertificate=true";
    private static final String user = "Book_StoreDB";
    private static final String password = "12345";

    public static Connection getConnection(){
        try {
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println("Connected to MS SQL Server");
        return connection;
     } catch (SQLException e) {
        System.out.println("There is an error: ");
        e.printStackTrace();
        return null;
     }
    }
}


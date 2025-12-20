package com.mycompany.bookstore.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// url for most ms sql server is "jdbc:sqlserver://hostname\\instance:port;databseName=dbName"
public class DBConnection { 
//    private static final String url = "jdbc:sqlserver://REHAM-PC\\MSSQLEXPRESS"
//            + ";databaseName=Book_Store;encrypt=true;trustServerCertificate=true";
    
    private static final String url =
    "jdbc:sqlserver://DESKTOP-TRUVG2B;databaseName=Book_Store;encrypt=true;trustServerCertificate=true";
    
    
    private static final String user = "sa";
    private static final String password = "sa123456";

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


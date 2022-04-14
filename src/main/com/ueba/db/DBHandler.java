package com.ueba.db;


import com.ueba.prerequisites.InputHandler;
import org.json.JSONObject;

import java.sql.*;
public class DBHandler {
    static JSONObject dbDetails= InputHandler.getDbDetails();
    public static Connection connectDB() throws SQLException, ClassNotFoundException {
        String connString="jdbc:sqlserver://"+dbDetails.getString("dbserver")+";databaseName="+dbDetails.getString("dbname")+";user="+dbDetails.getString("dbusername")+";password="+dbDetails.getString("dbpassword")+";;encrypt=true;trustServerCertificate=true;";
        //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection conn= DriverManager.getConnection(connString);
        return conn;
    }
}

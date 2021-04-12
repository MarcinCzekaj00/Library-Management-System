package org.library.DBConnect;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBConnection {


    public Connection getConnection(){

        Connection con =null;
        try{

            Properties prop = new Properties();
            Properties propToDB = new Properties();

            FileInputStream fis = new FileInputStream("Database//DBProperties");
            prop.load(fis);

            propToDB.put("user",prop.getProperty("user"));
            propToDB.put("password",prop.getProperty("password"));
            propToDB.put("useUnicode",prop.getProperty("useUnicode"));
            propToDB.put("useServerPrepStmts",prop.getProperty("useServerPrepStmts"));
            propToDB.put("characterEncoding",prop.getProperty("characterEncoding"));

            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/library?autoReconnect=true&useSSL=false",propToDB);

        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return con;
    }

}

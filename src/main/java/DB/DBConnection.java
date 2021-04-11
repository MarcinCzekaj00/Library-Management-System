package DB;

import java.sql.*;
import java.util.Properties;

public class DBConnection {


    public Connection getConnection(){

        Connection con =null;
        try{
            Properties props = new Properties();
            props.put("user", "root");
            props.put("password", "1234");
            props.put("useUnicode", "true");
            props.put("useServerPrepStmts", "false");
            props.put("characterEncoding", "UTF-8");
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/library?autoReconnect=true&useSSL=false",props);

        }catch(ClassNotFoundException | SQLException e)
        {
            System.out.println(e);
        }
        return con;
    }

}

package org.library.messages;

import org.library.DBConnect.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NextID {

    public static int getNextID(String query) {

        DBConnection connect = new DBConnection();
        int id=0;

        try (Connection connectDB = connect.getConnection()) {
            Statement stm = connectDB.createStatement();
            ResultSet queryResult = stm.executeQuery(query);

            if (queryResult.next()) {
                id=queryResult.getInt(1) + 1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}

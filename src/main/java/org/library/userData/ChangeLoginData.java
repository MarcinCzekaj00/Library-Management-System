package org.library.userData;

import org.library.DBConnect.DBConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ChangeLoginData {

    public static boolean saveNewLoginDataMethod(String username, String password) {

        DBConnection connect = new DBConnection();

        try (Connection connectDB = connect.getConnection()) {

            String newPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            String query = "update users set password='" + newPassword + "',username='" + username + "' where users_id=1";

            Statement stm = connectDB.createStatement();
            stm.executeUpdate(query);

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

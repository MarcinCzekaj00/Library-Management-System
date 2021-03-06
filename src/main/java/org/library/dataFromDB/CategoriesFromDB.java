package org.library.dataFromDB;

import javafx.scene.control.ChoiceBox;
import org.library.DBConnect.DBConnection;
import org.library.service.QueryHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CategoriesFromDB {


    public static void getCategoriesFromDB (ChoiceBox<String> addBookCategory) {

        String query = QueryHelper.getSelectFromCategories();

        DBConnection connect = new DBConnection();
        ArrayList<String> categories = new ArrayList<>();

        try (Connection connectDB = connect.getConnection()) {
            Statement stm = connectDB.createStatement();
            ResultSet queryResult = stm.executeQuery(query);

            while (queryResult.next()) {
                categories.add(queryResult.getString("category"));
            }

            addBookCategory.getItems().addAll(categories);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

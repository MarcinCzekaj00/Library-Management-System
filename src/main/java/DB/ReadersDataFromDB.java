package DB;

import data.ReaderData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ReadersDataFromDB {
    public static ObservableList<ReaderData> getDataUsers(){

        DBConnection connect = new DBConnection();
        ObservableList<ReaderData> list = FXCollections.observableArrayList();
        String query = "select * from readers";

        try(Connection connectDB = connect.getConnection()){

            Statement stm = connectDB.createStatement();
            ResultSet result = stm.executeQuery(query);

            while (result.next()){
                list.add(new ReaderData(Integer.parseInt(result.getString("readers_id")), result.getString("name"),
                        result.getString("surname"), result.getDate("date_of_birth")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}

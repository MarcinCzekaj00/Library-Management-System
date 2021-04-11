package DB;

import data.BookData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class BookDataFromDB {
    public static ObservableList<BookData> getDataBook(){

        DBConnection connect = new DBConnection();
        ObservableList<BookData> list = FXCollections.observableArrayList();
        String query = "select * from books";

        try(Connection connectDB = connect.getConnection()){

            Statement stm = connectDB.createStatement();
            ResultSet result = stm.executeQuery(query);

            while (result.next()){
                list.add(new BookData(Integer.parseInt(result.getString("books_id")), result.getString("title"),
                        result.getString("category"),result.getString("author"), result.getDate("release_date")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}

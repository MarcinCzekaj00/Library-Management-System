package DB;

import data.BorrowedBookData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class BorrowedBookDataFromDB {
    public static ObservableList<BorrowedBookData> getDataBorrowedBook(){

        DBConnection connect = new DBConnection();

        ObservableList<BorrowedBookData> list = FXCollections.observableArrayList();

        String query = "select * from rentals";

        try(Connection connectDB = connect.getConnection()){

            Statement stm = connectDB.createStatement();
            ResultSet result = stm.executeQuery(query);

            while (result.next()){

                String query1 = "select title from books where books_id=" + result.getString("book_id");
                Statement stm1 = connectDB.createStatement();
                ResultSet result1 = stm1.executeQuery(query1);

                String query2 = "select name from readers where readers_id=" + result.getString("reader_id");
                Statement stm2 = connectDB.createStatement();
                ResultSet result2 = stm2.executeQuery(query2);

                String query3 = "select surname from readers where readers_id="+ result.getString("reader_id");
                Statement stm3 = connectDB.createStatement();
                ResultSet result3 = stm3.executeQuery(query3);

                result1.next();
                result2.next();
                result3.next();

                list.add(new BorrowedBookData(Integer.parseInt(result.getString("rental_id")), Integer.parseInt(result.getString("book_id")),
                        result1.getString(1), Integer.parseInt(result.getString("reader_id")),result2.getString(1) ,
                        result3.getString(1), result.getDate("due_date")));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}

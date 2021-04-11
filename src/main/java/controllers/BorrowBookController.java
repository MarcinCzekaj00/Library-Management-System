package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import DB.DBConnection;
import data.User;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import messages.BadValuesMessage;
import messages.ClearMessage;
import messages.SuccessMessage;
import org.library.*;

public class BorrowBookController implements Initializable {

    @FXML
    private Label usernameLabel, borrowBookMessage;

    @FXML
    private TextField borrowBookBookID, borrowBookReaderID;

    @FXML
    private DatePicker borrowBookDueDate;

    //==============================================================================================================

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        usernameLabel.setText(User.getUsername());

        ClearMessage.clear(borrowBookMessage);
        clearBorrowBookFields();
    }

    @FXML
    public void addReaderButtonOnAction() throws IOException {
        App.setRoot("addReader");
    }

    @FXML
    public void addNewBookButtonOnAction() throws IOException {
        App.setRoot("addNewBook");
    }

    @FXML
    public void infoButtonOnAction() throws IOException {
        App.setRoot("info");
    }

    @FXML
    public void borrowBookButtonOnAction() throws IOException {
        App.setRoot("system");
    }

    @FXML
    public void returnBookButtonOnAction() throws IOException {
        App.setRoot("returnBook");
    }

    @FXML
    public void logoutButtonOnAction() throws IOException {
        App.setRoot("login");
    }

    //==============================================================================================================

    @FXML
    private void borrowBookAcceptButtonOnAction() {

        String query = "INSERT INTO rentals (book_id,reader_id,due_date) values (" + borrowBookBookID.getText() +
                                                                                ",'" + borrowBookReaderID.getText() +
                                                                                "','" + borrowBookDueDate.getValue() +
                                                                                "');";

        String test = "SELECT * FROM books,readers where books_id=" + borrowBookBookID.getText() +
                                                   " AND readers_id=" + borrowBookReaderID.getText() + ";";

        DBConnection connect = new DBConnection();

        try(Connection connectDB = connect.getConnection()){

            Statement stm = connectDB.createStatement();
            ResultSet result = stm.executeQuery(test);

                if (result.next()) {
                    Statement stm2 = connectDB.createStatement();
                    stm2.executeUpdate(query);
                    SuccessMessage.getSuccessMessage(borrowBookMessage);
                    clearBorrowBookFields();
                }
        } catch (SQLException e) {
            BadValuesMessage.getBadValuesMessage(borrowBookMessage);
            e.printStackTrace();
        }
    }

    //==============================================================================================================

    private void clearBorrowBookFields() {
        borrowBookBookID.setText("");
        borrowBookReaderID.setText("");
        borrowBookDueDate.setValue(null);
    }

}
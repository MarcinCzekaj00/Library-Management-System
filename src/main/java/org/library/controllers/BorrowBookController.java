package org.library.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import org.library.DBConnect.DBConnection;
import org.library.messages.MessagesHelper;
import org.library.service.QueryHelper;
import org.library.userData.User;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

        MessagesHelper.getMessage(borrowBookMessage,"","GREEN");
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

        String query = QueryHelper.getBorrowBook();

        String test = QueryHelper.getIfBookExist();

        DBConnection connect = new DBConnection();

        try(Connection connectDB = connect.getConnection()){

            PreparedStatement pstm = connectDB.prepareStatement(test);
            pstm.setString(1, borrowBookBookID.getText());
            pstm.setString(2, borrowBookReaderID.getText());

            ResultSet result = pstm.executeQuery();

                if (result.next()) {
                    PreparedStatement pstm1 = connectDB.prepareStatement(query);
                    pstm1.setString(1, borrowBookBookID.getText());
                    pstm1.setString(2, borrowBookReaderID.getText());
                    pstm1.setObject(3, borrowBookDueDate.getValue());

                    pstm1.executeUpdate();

                    MessagesHelper.getMessage(borrowBookMessage,"Success!","GREEN");
                    clearBorrowBookFields();
                }
                else throw new SQLException();
        } catch (SQLException e) {
            MessagesHelper.getMessage(borrowBookMessage,"Bad values!","RED");
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
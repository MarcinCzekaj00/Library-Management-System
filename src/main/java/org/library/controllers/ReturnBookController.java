package org.library.controllers;

import org.library.DBConnect.DBConnection;
import org.library.messages.MessagesHelper;
import org.library.service.QueryHelper;
import org.library.userData.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.library.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static java.time.temporal.ChronoUnit.DAYS;

public class ReturnBookController implements Initializable {

    @FXML
    private Label usernameLabel,returnBookMessage,delayTime;

    @FXML
    private TextField returnBookBookID,returnBookReaderID;

    //==============================================================================================================

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        usernameLabel.setText(User.getUsername());

        MessagesHelper.getMessage(returnBookMessage,"","RED");
        clearReturnBookFields();
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
        App.setRoot("borrowBook");
    }

    @FXML
    public void returnBookButtonOnAction() throws IOException {
        App.setRoot("system");
    }

    @FXML
    public void logoutButtonOnAction() throws IOException {
        App.setRoot("login");
    }

    //==============================================================================================================

    @FXML
    private void returnBookAcceptButtonOnAction() {

        String query = QueryHelper.getDeleteFromRentals();
        String queryDate = QueryHelper.getDueDate();

        DBConnection connect = new DBConnection();
        try (Connection connectDB = connect.getConnection()) {

            LocalDate today = LocalDate.now();
            String dueDateString = null;

            PreparedStatement pstm = connectDB.prepareStatement(queryDate);
            pstm.setString(1, returnBookBookID.getText());
            pstm.setString(2, returnBookReaderID.getText());

            ResultSet result = pstm.executeQuery();

            if (result.next()) {
                dueDateString = result.getString("due_date");
            }
            else {
                MessagesHelper.getMessage(returnBookMessage,"Bad values!","RED");
            }

            LocalDate dueDate = LocalDate.parse(dueDateString);

            if (today.compareTo(dueDate) > 0) {
                long daysBetween = DAYS.between(today, dueDate);
                daysBetween = daysBetween * (-1);
                delayTime.setText(Long.toString(daysBetween));
            } else {
                delayTime.setText("On Time!");
            }

            PreparedStatement pstm1 = connectDB.prepareStatement(query);
            pstm1.setString(1, returnBookBookID.getText());
            pstm1.setString(2, returnBookReaderID.getText());
            pstm1.executeUpdate();

            MessagesHelper.getMessage(returnBookMessage,"Success!","Green");
            clearReturnBookFields();

        } catch (SQLException e) {
            MessagesHelper.getMessage(returnBookMessage,"Bad values!","RED");
            e.printStackTrace();
        }
    }

    private void clearReturnBookFields() {
        returnBookBookID.setText("");
        returnBookReaderID.setText("");
    }
}
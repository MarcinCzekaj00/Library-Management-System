package controllers;

import DB.DBConnection;
import data.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import messages.BadValuesMessage;
import messages.ClearMessage;
import messages.SuccessMessage;
import org.library.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

        ClearMessage.clear(returnBookMessage);
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

        String query = "DELETE FROM rentals WHERE book_id=" + returnBookBookID.getText() +
                                            " AND reader_id=" + returnBookReaderID.getText() + ";";

        String queryDate = "select due_date from rentals where reader_id=" + returnBookReaderID.getText() +
                                                         " AND book_id=" + returnBookBookID.getText() + ";";


        DBConnection connect = new DBConnection();
        try (Connection connectDB = connect.getConnection()) {

            LocalDate today = LocalDate.now();
            String dueDateString = null;

            Statement stm = connectDB.createStatement();
            ResultSet result = stm.executeQuery(queryDate);

            if (result.next()) {
                dueDateString = result.getString("due_date");
            }
            else {
                BadValuesMessage.getBadValuesMessage(returnBookMessage);
            }

            LocalDate dueDate = LocalDate.parse(dueDateString);

            if (today.compareTo(dueDate) > 0) {
                long daysBetween = DAYS.between(today, dueDate);
                daysBetween = daysBetween * (-1);
                delayTime.setText(Long.toString(daysBetween));
            } else {
                delayTime.setText("On Time!");
            }

            Statement stm2 = connectDB.createStatement();
            stm2.executeUpdate(query);

            SuccessMessage.getSuccessMessage(returnBookMessage);
            clearReturnBookFields();

        } catch (SQLException e) {
            BadValuesMessage.getBadValuesMessage(returnBookMessage);
            e.printStackTrace();
        }
    }

    private void clearReturnBookFields() {
        returnBookBookID.setText("");
        returnBookReaderID.setText("");
    }
}
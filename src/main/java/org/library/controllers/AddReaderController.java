package org.library.controllers;

import org.library.DBConnect.DBConnection;
import org.library.messages.MessagesHelper;
import org.library.service.QueryHelper;
import org.library.userData.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.library.messages.NextID;
import org.library.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AddReaderController implements Initializable {

    @FXML
    private Label usernameLabel, nextReaderID, addReaderMessage;

    @FXML
    private TextField addReaderName, addReaderSurname;

    @FXML
    private DatePicker addReaderDateOfBirth;

    //==============================================================================================================

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        usernameLabel.setText(User.getUsername());

        MessagesHelper.getMessage(addReaderMessage,"","GREEN");
        clearAddNewReaderFields();
        nextReaderID();

    }

    @FXML
    public void addReaderButtonOnAction() throws IOException {
        App.setRoot("system");
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
        App.setRoot("returnBook");
    }

    @FXML
    public void logoutButtonOnAction() throws IOException {
        App.setRoot("login");
    }

    //==============================================================================================================

    @FXML
    private void addReaderAcceptButtonOnAction() {

        String query = QueryHelper.getAddReader();

        DBConnection connect = new DBConnection();

        try (Connection connectDB = connect.getConnection()) {

            PreparedStatement pstm = connectDB.prepareStatement(query);
            pstm.setString(1, addReaderName.getText());
            pstm.setString(2, addReaderSurname.getText());
            pstm.setObject(3, addReaderDateOfBirth.getValue());

            pstm.executeUpdate();

            MessagesHelper.getMessage(addReaderMessage,"Success!","GREEN");

            nextReaderID();
            clearAddNewReaderFields();

        } catch (SQLException e) {
            MessagesHelper.getMessage(addReaderMessage,"Bad values!","RED");
            e.printStackTrace();
        }
    }

    //==============================================================================================================

    private void nextReaderID() {
        String query = QueryHelper.getMaxReadersID();
        nextReaderID.setText(String.valueOf(NextID.getNextID(query)));
    }


    private void clearAddNewReaderFields() {
        addReaderName.setText("");
        addReaderSurname.setText("");
        addReaderDateOfBirth.setValue(null);
    }
}
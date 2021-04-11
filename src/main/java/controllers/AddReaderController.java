package controllers;

import DB.DBConnection;
import data.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import messages.BadValuesMessage;
import messages.ClearMessage;
import messages.NextID;
import messages.SuccessMessage;
import org.library.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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

        ClearMessage.clear(addReaderMessage);
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

        String query = "INSERT INTO readers (readers_id,name,surname,date_of_birth) values (" + NextID.getNextID("SELECT max(readers_id) FROM readers")+
                                                                            ",'" + addReaderName.getText() +
                                                                            "','" + addReaderSurname.getText() +
                                                                            "','" + addReaderDateOfBirth.getValue() + "');";

        DBConnection connect = new DBConnection();

        try (Connection connectDB = connect.getConnection()) {

            Statement stm = connectDB.createStatement();
            stm.executeUpdate(query);

            SuccessMessage.getSuccessMessage(addReaderMessage);

            nextReaderID();
            clearAddNewReaderFields();

        } catch (SQLException e) {
            BadValuesMessage.getBadValuesMessage(addReaderMessage);
            e.printStackTrace();
        }
    }

    //==============================================================================================================

    private void nextReaderID() {
        String query = "SELECT max(readers_id) FROM readers";
        nextReaderID.setText(String.valueOf(NextID.getNextID(query)));
    }


    private void clearAddNewReaderFields() {
        addReaderName.setText("");
        addReaderSurname.setText("");
        addReaderDateOfBirth.setValue(null);
    }
}
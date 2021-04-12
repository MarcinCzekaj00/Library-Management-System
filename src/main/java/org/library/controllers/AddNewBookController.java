package org.library.controllers;

import org.library.dataFromDB.CategoriesFromDB;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

    public class AddNewBookController implements Initializable {

        @FXML
        private Label usernameLabel, nextBookID, addBookMessage;

        @FXML
        private TextField addBookTitle, addBookAuthor;

        @FXML
        private DatePicker addBookReleaseDate;

        @FXML
        private ChoiceBox<String> addBookCategory;

        //==============================================================================================================

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

            usernameLabel.setText(User.getUsername());

            MessagesHelper.getMessage(addBookMessage,"", "RED");
            clearAddNewBookFields();
            nextBookID();

            CategoriesFromDB.getCategoriesFromDB(addBookCategory);
        }

        @FXML
        public void addReaderButtonOnAction() throws IOException {
            App.setRoot("addReader");
        }

        @FXML
        public void addNewBookButtonOnAction() throws IOException {
            App.setRoot("system");
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
        private void addBookAcceptButtonOnAction() {

            String query = QueryHelper.getAddBook();

            DBConnection connect = new DBConnection();

            try (Connection connectDB = connect.getConnection()) {

                PreparedStatement pstm = connectDB.prepareStatement(query);
                pstm.setString(1, addBookTitle.getText());
                pstm.setString(2, addBookCategory.getValue());
                pstm.setString(3, addBookAuthor.getText());
                pstm.setObject(4, addBookReleaseDate.getValue());

                pstm.executeUpdate();

                MessagesHelper.getMessage(addBookMessage,"Success!", "GREEN");
                nextBookID();
                clearAddNewBookFields();

            } catch (SQLException e) {
                MessagesHelper.getMessage(addBookMessage,"Bad values!", "RED");
                e.printStackTrace();
            }
        }

        //==============================================================================================================


        private void nextBookID() {
            String query = QueryHelper.getMaxBooksID();
            nextBookID.setText(String.valueOf(NextID.getNextID(query)));

        }


        private void clearAddNewBookFields() {
            addBookAuthor.setText("");
            addBookTitle.setText("");
            addBookCategory.setValue("");
            addBookReleaseDate.setValue(null);
        }
}

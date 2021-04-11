package controllers;

import DB.CategoriesFromDB;
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

            ClearMessage.clear(addBookMessage);
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

            String query = "INSERT INTO books (books_id,title,category,author,release_date) values (" + NextID.getNextID("SELECT max(books_id) FROM books")+
                                                                                            ",'" + addBookTitle.getText() +
                                                                                            "','" + addBookCategory.getSelectionModel().getSelectedItem() +
                                                                                            "','" + addBookAuthor.getText() +
                                                                                            "','" + addBookReleaseDate.getValue() +
                                                                                            "');";
            DBConnection connect = new DBConnection();

            try (Connection connectDB = connect.getConnection()) {

                Statement stm = connectDB.createStatement();
                stm.executeUpdate(query);
                SuccessMessage.getSuccessMessage(addBookMessage);
                nextBookID();
                clearAddNewBookFields();

            } catch (SQLException e) {
                BadValuesMessage.getBadValuesMessage(addBookMessage);
                e.printStackTrace();
            }
        }

        //==============================================================================================================


        private void nextBookID() {
            String query = "SELECT max(books_id) FROM books";
            nextBookID.setText(String.valueOf(NextID.getNextID(query)));

        }


        private void clearAddNewBookFields() {
            addBookAuthor.setText("");
            addBookTitle.setText("");
            addBookCategory.setValue("");
            addBookReleaseDate.setValue(null);
        }
}

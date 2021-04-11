package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.library.App;
import data.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SystemController implements Initializable {

    @FXML
    private Label usernameLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        User user = new User();
        this.usernameLabel.setText(user.getUsername());

    }

    @FXML
    public void addReaderButtonOnAction() throws IOException{
        App.setRoot("addReader");
    }

    @FXML
    public void addNewBookButtonOnAction() throws IOException{
        App.setRoot("addNewBook");
    }

    @FXML
    public void infoButtonOnAction() throws IOException{
        App.setRoot("info");
    }

    @FXML
    public void borrowBookButtonOnAction() throws IOException{
        App.setRoot("borrowBook");
    }

    @FXML
    public void returnBookButtonOnAction() throws IOException{
        App.setRoot("returnBook");
    }

    @FXML
    public void logoutButtonOnAction() throws IOException {
        App.setRoot("login");
    }

}
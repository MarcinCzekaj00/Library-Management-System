package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.library.App;
import DB.DBConnection;
import data.User;

import java.io.IOException;
import java.sql.*;


public class LoginController {


    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("system");
    }

    @FXML
    private Label loginMessageLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;

    @FXML
    public void loginButtonOnAction () throws IOException{
        if (!usernameTextField.getText().isBlank() && !passwordTextField.getText().isBlank()) {
            checkLoginData();
        } else {
            loginMessageLabel.setText("Enter username and password");
        }
    }

    //==============================================================================================================

    private void checkLoginData() throws IOException {

        DBConnection connect = new DBConnection();

        try (Connection connectDB = connect.getConnection()) {
            String queryLogin = "SELECT count(1) FROM users WHERE username='" + usernameTextField.getText() + "' AND password ='" + passwordTextField.getText() + "';";

            Statement stm = connectDB.createStatement();
            ResultSet queryResult = stm.executeQuery(queryLogin);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    setUserController();
                    switchToSecondary();
                } else {
                    loginMessageLabel.setText("Invalid login or password");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setUserController(){
        new User(usernameTextField.getText());
    }
}

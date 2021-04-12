package org.library.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.library.App;
import org.library.DBConnect.DBConnection;
import org.library.messages.MessagesHelper;
import org.library.service.QueryHelper;
import org.library.userData.User;
import org.mindrot.jbcrypt.BCrypt;

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
            MessagesHelper.getMessage(loginMessageLabel,"Enter username and password!","RED");
        }
    }

    @FXML
    public void changeLoginDataButtonOnAction() throws IOException{
        App.setRoot("changeLoginData");
    }

    //==============================================================================================================

    private void checkLoginData() throws IOException {

        DBConnection connect = new DBConnection();

        try (Connection connectDB = connect.getConnection()) {

            String queryLogin = QueryHelper.getIfUserExist();

            PreparedStatement pstm = connectDB.prepareStatement(queryLogin);
            pstm.setString(1, usernameTextField.getText());

            ResultSet queryResult = pstm.executeQuery();
            queryResult.next();

            boolean correctPassword = checkPass(passwordTextField.getText(), queryResult.getString("password"));

                if (correctPassword) {
                    setUserController();
                    switchToSecondary();
                } else {
                    MessagesHelper.getMessage(loginMessageLabel,"Invalid login or password!","RED");
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean checkPass(String plainPassword, String hashedPassword) {
        if (BCrypt.checkpw(plainPassword, hashedPassword))
            return true;
        else
            return false;
    }

    private void setUserController(){
        new User(usernameTextField.getText());
    }
}

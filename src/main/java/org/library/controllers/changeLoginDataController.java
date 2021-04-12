package org.library.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.library.App;
import org.library.messages.MessagesHelper;
import org.library.userData.ChangeLoginData;

import java.io.IOException;

public class changeLoginDataController {

    @FXML
    private TextField newUsernameTextField;

    @FXML
    private PasswordField newPasswordTextField;

    @FXML
    private Label changeLoginDataMessage;

    @FXML
    void saveButtonOnAction() {
       boolean result = ChangeLoginData.saveNewLoginDataMethod(newUsernameTextField.getText(),newPasswordTextField.getText());
       if (result){
           MessagesHelper.getMessage(changeLoginDataMessage,"Success!","GREEN");
       } else {
           MessagesHelper.getMessage(changeLoginDataMessage,"Query error!","RED");
       }
    }

    @FXML
    void toLoginButtonOnAction() throws IOException {
        App.setRoot("login");
    }
}

package Controllers;

import Controllers.ControllersInterfaces.IUpdateAccountController;
import Domain.User;
import NetworkService.INetworkService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

public class UpdateAccountController implements IUpdateAccountController {
    User connectedUser;
    INetworkService service;
//    Stage dialogStage;

    @FXML
    TextField newUsername;

    @FXML
    PasswordField newPassword;

    @FXML
    Text updated;

    public void setService(INetworkService givenService, User givenUser){
        this.service = givenService;
        this.connectedUser = givenUser;
    }

    @FXML
    public void initialize(){
    }

    @FXML
    public void updateButton(){
        try{
        String updatedUsername;
        if(newUsername.getText().equals(""))
            updatedUsername = connectedUser.getUsername();
        else
            updatedUsername = newUsername.getText();
        String updatedPassword;
        if(newPassword.getText().equals(""))
            updatedPassword = connectedUser.getPassword();
        else
            updatedPassword = newPassword.getText();
        this.service.updateUser(connectedUser.getId().toString(), updatedUsername, updatedPassword);
        updated.setText("Updated");
        updated.setVisible(true);
    }catch (RuntimeException e){
            updated.setText("Username already exists");
            updated.setVisible(true);
        }
}
}

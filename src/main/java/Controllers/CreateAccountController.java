package Controllers;

import Controllers.ControllersInterfaces.ICreateAccountController;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import NetworkService.INetworkService;
import java.sql.SQLException;

public class CreateAccountController implements ICreateAccountController {

    @FXML
    TextField newUsernameField;

    @FXML
    PasswordField newPasswordField;

    @FXML
    Text AlreadyExists;

    @FXML
    Text AccountCreated;

    Stage dialogStage;

    INetworkService service;


    public void setService(INetworkService givenService, Stage givenStage){
        this.dialogStage = givenStage;
        this.service = givenService;
    }

    @FXML
    public void initialize(){
//
    }

    @FXML
        public void CreateAccountButton(){
        try{
            AlreadyExists.setVisible(false);
            AccountCreated.setVisible(false);
            this.service.addUserToNetwork(newUsernameField.getText(), newPasswordField.getText());
            AccountCreated.setVisible(true);
//            dialogStage.close();
        }catch (SQLException e){
            AlreadyExists.setVisible(true);
        }

    }

}

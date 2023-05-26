package Controllers;

import Controllers.ControllersInterfaces.ICreateAccountController;
import Controllers.ControllersInterfaces.ILoggerController;
import Controllers.ControllersInterfaces.IMainController;
import Domain.User;
import NetworkService.INetworkService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class LoggerController implements ILoggerController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    Text userExists;

    INetworkService service;

    Stage stage;

    @FXML
    public void initialize(){
    }

    public void setService(INetworkService givenService, Stage givenStage){
        this.service = givenService;
        this.stage = givenStage;
    }
    @FXML
    public void showCreateAccountDialog(){
    try{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/maplab/com/lab7/Views/createaccount-view.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Create account");
        Scene scene = new Scene(root);
        dialogStage.setScene(scene);

        ICreateAccountController createAccountController = loader.getController();
        createAccountController.setService(service, dialogStage);

        dialogStage.show();
    }catch (IOException exception){
        exception.printStackTrace();
    }
    }

    @FXML
    public void logIn(){
        try {
            User user = service.readUser(usernameField.getText());
            if(user == null)
                userExists.setVisible(true);
            else {
                if (!user.getPassword().equals(passwordField.getText()))
                    userExists.setVisible(true);
                else {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/maplab/com/lab7/Views/MainController.fxml"));
                    Parent root = loader.load();
                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("DotDot");
                    Scene scene = new Scene(root);
                    dialogStage.setScene(scene);
                    IMainController loggedInController = loader.getController();
                    loggedInController.setService(service, user, dialogStage);
                    dialogStage.show();
                    this.stage.close();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }


}

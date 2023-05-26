package Controllers;

import Controllers.ControllersInterfaces.*;
import Domain.User;
import NetworkService.INetworkService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;


public class MainController implements IMainController {

    Stage currentStage;

    User loggedUser;

    INetworkService service;

    @FXML
    Label username;

    @FXML
    BorderPane mainPane;

    @FXML
    AnchorPane mainAchor;

    @FXML
    private VBox vbox;

    public void setService(INetworkService givenService, User givenLoggedUser, Stage givenStage) {
        this.loggedUser = givenLoggedUser;
        this.service = givenService;
        this.currentStage = givenStage;
        currentStage.setHeight(438);
        currentStage.setWidth(700);
        username.setText("Welcome " + loggedUser.getUsername());
    }

    @FXML
    public void onBtnAClick() {
        mainPane.getChildren().remove(mainAchor);
        AnchorPane root = new AnchorPane();
        root.getChildren().add(username);
        mainAchor = root;
//        mainPane.getChildren().add(root);
        currentStage.setHeight(438);
        currentStage.setWidth(700);
        mainPane.setCenter(root);
    }


    @FXML
    public void updateInfoButton() {
        try {
            mainPane.getChildren().remove(mainAchor);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/maplab/com/lab7/Views/updateinfocontroller-view.fxml"));
            mainAchor = loader.load();
            IUpdateAccountController accountController = loader.getController();
            accountController.setService(service, loggedUser);
            currentStage.setHeight(438);
            currentStage.setWidth(700);
            mainPane.setCenter(mainAchor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void checkFriendshipsButton() {

        mainPane.getChildren().remove(mainAchor);


//            loader.setController(friendshipsController);

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/maplab/com/lab7/Views/friendships.fxml"));
            mainAchor = loader.load();
            IFriendshipController friendshipsController = loader.getController();
            friendshipsController.setService(service, loggedUser);
//            mainPane.getChildren().add(mainAchor);
            currentStage.setHeight(438);
            currentStage.setWidth(700);
            mainPane.setCenter(mainAchor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void searchUsersButton() {
        try {

            mainPane.getChildren().remove(mainAchor);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/maplab/com/lab7/Views/searchuserview.fxml"));
            mainAchor = loader.load();
            ISearchUserController controller = loader.getController();
            controller.setService(service, loggedUser);
            currentStage.setWidth(485);
            currentStage.setHeight(488);
            mainPane.setCenter(mainAchor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void disconnect() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/maplab/com/lab7/Views/loggercontroller-view.fxml"));
            AnchorPane root = loader.load();
            Stage loggerStage = new Stage();
            loggerStage.setTitle("DotDot");
            Scene scene = new Scene(root);
            loggerStage.setScene(scene);
            ILoggerController loggerController = loader.getController();
            loggerController.setService(service, loggerStage);
            loggerStage.show();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


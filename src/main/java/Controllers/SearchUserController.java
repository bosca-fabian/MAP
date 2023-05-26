package Controllers;

import Controllers.ControllersInterfaces.ISearchUserController;
import Domain.DisplayFriendship;
import Domain.FriendRequest;
import Domain.Friendship;
import Domain.User;
import NetworkService.INetworkService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;
import java.util.ArrayList;

public class SearchUserController implements ISearchUserController {
    INetworkService service;

    ObservableList<User> users = FXCollections.observableArrayList();

    User loggedUser;

    @FXML
    TextField searchField;

    @FXML
    AnchorPane layout;

    @FXML
    Label placeholder;

    @FXML
    TableView<User> tableView;
    @FXML
    TableColumn<User, String> usersID;

    @FXML
    public void initialize(){
        usersID.setCellValueFactory(new PropertyValueFactory<>("username"));
        tableView.setItems(users);
    }

    public void setService(INetworkService givenService, User connectedUser){
        this.loggedUser = connectedUser;
        this.service = givenService;
    }



    private void initModel(ArrayList<User> usersList){
        users.clear();
        if(usersList.isEmpty())
            placeholder.setText("NO USERS FOUND");
        else
            users.setAll(usersList);
    }

    public void search(){
        layout.requestFocus();
        String lookUpUser = searchField.getText();
        ArrayList<User> neededUsers;
        if(!lookUpUser.isEmpty() || !lookUpUser.isBlank()) {
            neededUsers = service.getUsersBasedOnString(lookUpUser, loggedUser.getUsername());
            initModel(neededUsers);
        }
    }

    public void sendFriendRequest(){
        User selected = tableView.getSelectionModel().getSelectedItem();
        if(selected != null){
            service.createFriendRequest(loggedUser.getUsername(), selected.getUsername());
        }
    }
}

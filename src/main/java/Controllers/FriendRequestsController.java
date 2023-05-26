package Controllers;

import Domain.DisplayFriendship;
import Domain.FriendRequest;
import Domain.Friendship;
import Domain.User;
import NetworkService.INetworkService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ArrayList;

public class FriendRequestsController extends FriendshipsController {

    INetworkService service;

    User loggedUser;


    ObservableList<FriendRequest> requesters = FXCollections.observableArrayList();

    @FXML
    TableView<FriendRequest> tableView;

    @FXML
    TableColumn<FriendRequest, String> requestersID;

    @FXML
    public void initialize(){
        tableView.setItems(requesters);
    }

    public void setService(INetworkService givenService, User loggedUser, boolean receiver){
        if(receiver){
            requestersID.setCellValueFactory(new PropertyValueFactory<>("sender"));
        }else {requestersID.setCellValueFactory(new PropertyValueFactory<>("receiver"));}
        this.service = givenService;
        this.loggedUser = loggedUser;
        initModelRequests(receiver);
    }

    private void initModelRequests(boolean receiver){
        ArrayList<FriendRequest> requests = service.readAllFriendshipRequests(loggedUser, receiver);
        requesters.setAll(requests);
    }

    @FXML
    public void accept(){
        FriendRequest selected = tableView.getSelectionModel().getSelectedItem();
        User firstFriend = service.readUser(selected.getSender());
        User secondFriend = service.readUser(selected.getReceiver());
        this.service.createFriendship(firstFriend.getId().toString(), secondFriend.getId().toString());
        this.requesters.remove(selected);
        this.service.deleteFriendRequest(selected);
    }

    @FXML
    public void decline(){
        FriendRequest selected = tableView.getSelectionModel().getSelectedItem();
        this.requesters.remove(selected);
        this.service.deleteFriendRequest(selected);
    }

}

package Controllers;

import Controllers.ControllersInterfaces.IChatroomController;
import Controllers.ControllersInterfaces.IFriendshipController;
import Domain.DisplayFriendship;
import Domain.Friendship;
import Domain.User;
import NetworkService.INetworkService;

import Utils.ControllerObserver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class FriendshipsController implements IFriendshipController {
    User loggedUser;


    INetworkService service;

    ObservableList<DisplayFriendship> friendships = FXCollections.observableArrayList();

    @FXML
    Label shownUsername;

    @FXML
    AnchorPane layout;

    @FXML
    TableView<DisplayFriendship> tableView;
    @FXML
    TableColumn<DisplayFriendship, String> tableFriendName;
    @FXML
    TableColumn<DisplayFriendship, String> tableFriendsFrom;


    @FXML
    public void initialize(){
        tableFriendName.setCellValueFactory(new PropertyValueFactory<>("user"));
        tableFriendsFrom.setCellValueFactory(new PropertyValueFactory<>("friendship"));
        tableView.setItems(friendships);
    }

    public void setService(INetworkService givenService, User connectedUser){
        this.loggedUser = connectedUser;
//        this.dialogStage = givenStage;
        this.service = givenService;
        this.service.addObserver(this);
        shownUsername.setText("Username: " + connectedUser.getUsername());
        initModel();
    }

    protected void initModel(){
        ArrayList<Friendship> existingFriendships = service.readAllFriendshipsForUser(loggedUser);
        ArrayList<User> userFriends = new ArrayList<>();
        for (Friendship friend: existingFriendships
        ) {
            userFriends.add(service.readUser(friend.getSecondFriend()));
        }
        int i;
        friendships.clear();
        for(i = 0; i < userFriends.size(); i++)
            friendships.add(new DisplayFriendship(userFriends.get(i).getUsername(), existingFriendships.get(i).getFriendsFrom()));
    }

    @FXML
    public void deleteFriend(){
        DisplayFriendship selected = tableView.getSelectionModel().getSelectedItem();
        if(selected != null) {
            service.deleteFriendship(loggedUser.getUsername(), selected.getUser());
            friendships.remove(selected);
            layout.requestFocus();
//            initModel();
        }
    }

    @FXML
    public void cancelFriendRequests(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/maplab/com/lab7/Views/sentFriendRequests.fxml"));
            AnchorPane root = loader.load();
            Stage loggerStage = new Stage();
            loggerStage.setTitle("DotDot");
            Scene scene = new Scene(root);
            loggerStage.setScene(scene);
            FriendRequestsController friendRequestsController = loader.getController();
            friendRequestsController.setService(service, loggedUser, false);
            loggerStage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void checkFriendRequests(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/maplab/com/lab7/Views/friendrequests.fxml"));
            AnchorPane root = loader.load();
            Stage loggerStage = new Stage();
            loggerStage.setTitle("DotDot");
            Scene scene = new Scene(root);
            loggerStage.setScene(scene);
            FriendRequestsController friendRequestsController = loader.getController();
            friendRequestsController.setService(service, loggedUser, true);
            loggerStage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void chatroom(){
        try{
            DisplayFriendship selected = tableView.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/maplab/com/lab7/Views/chatroomview.fxml"));
            AnchorPane root = loader.load();
            Stage chat = new Stage();
            chat.setTitle("DotDot ~ Chatroom");
            chat.setScene(new Scene(root));
            IChatroomController controller = loader.getController();
            controller.setService(this.service, loggedUser.getUsername(),selected.getUser());
//            this.service.threadwork();
            chat.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        initModel();
    }


//    @Override
//    public void update(MessageTaskChangeEvent messageTaskChangeEvent) {
//        initModel();
//    }
}

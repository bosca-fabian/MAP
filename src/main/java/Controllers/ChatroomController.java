package Controllers;

import Controllers.ControllersInterfaces.IChatroomController;
import Domain.Message;
import Factories.TextFactory;
import NetworkService.INetworkService;
import Utils.ControllerObserver;
import Utils.Tuple;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ChatroomController implements IChatroomController {

    private TextFactory factory =TextFactory.getInstance();

    private String loggedUser;

    private String friend;

    private int noMessages = 0;

    @FXML
    public Text displayFriendName;

    private INetworkService service;

    @FXML
    TextField messageTextField;

    ObservableList<Message> messages = FXCollections.observableArrayList();
    @FXML
    AnchorPane pane;


    @FXML
    public void initialize(){

    }

    public void setService(INetworkService givenService, String logged, String friend){
        this.service = givenService;
        this.service.addObserver(this);
        this.loggedUser = logged;
        this.friend = friend;
        this.displayFriendName.setText(friend);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.setImplicitExit(false);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        update();
                    }
                });
            }
        }, 0, 2000);
    }


    @FXML
    public void sendMessage() {
        Message newSentMessage = new Message(loggedUser, friend, messageTextField.getText());
        try {
            this.service.addMessageToDB(loggedUser, friend, messageTextField.getText());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //apelezi mai intai service, ajunge in baza de date, se da update dupa timestamps si dupa se sterg mesajele dupa ce au fost afisate
//        messages.add(newSentMessage);
//        displayText(factory.createChat(newSentMessage, loggedUser));
        //Apel la service pentru baza de date sa fie adaugat mesajul acolo si sa apelezi in service notifyallobservers
    }

    private void displayText(Tuple<Rectangle, Text> toBeDisplayed){
        pane.getChildren().add(toBeDisplayed.x());
        pane.getChildren().add(toBeDisplayed.y());
    }


    @Override
    public void update() {
        ArrayList<Message> arrayList = service.readMessagesFromDB(loggedUser, friend);
//        noMessages += arrayList.size();
        int size = arrayList.size();
        for (int i = noMessages; i < size; i++
             ) {
            this.displayText(factory.createChat(arrayList.get(i), loggedUser));
        }
        noMessages = size;
    }
}


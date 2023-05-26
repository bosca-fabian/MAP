package maplab.com.lab7;

//import Controllers.Controller;
import Controllers.ControllersInterfaces.ILoggerController;
import Domain.Validators.FriendshipValidator;
import Domain.Validators.UserValidator;
import NetworkService.NetworkService;
import Repository.DatabaseRepo.FriendRequestsDBRepo;
import Repository.DatabaseRepo.FriendshipDBRepo;
import Repository.DatabaseRepo.MessageDBRepo;
import Repository.DatabaseRepo.UserDBRepo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    NetworkService service;
    Parent root;
    Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        UserDBRepo newUserDatabase = new UserDBRepo("jdbc:postgresql://localhost:5432/LabMAP", "postgres", "postgres",
                new UserValidator());
        FriendshipDBRepo newFriendshipDatabase = new FriendshipDBRepo("jdbc:postgresql://localhost:5432/LabMAP", "postgres", "postgres",
                new FriendshipValidator());

        FriendRequestsDBRepo requestsDBRepo = new FriendRequestsDBRepo("jdbc:postgresql://localhost:5432/LabMAP", "postgres", "postgres");
        MessageDBRepo messageDBRepo = new MessageDBRepo("jdbc:postgresql://localhost:5432/LabMAP", "postgres", "postgres");
        service = new NetworkService(newUserDatabase, newFriendshipDatabase, requestsDBRepo, messageDBRepo);

        try{
            initView(primaryStage);
//          primaryStage.setWidth(800);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
            e.getLocalizedMessage();
            System.out.println(e.getMessage());
        }
    }

    private void initView(Stage primaryStage) throws IOException {
        stage = primaryStage;
        FXMLLoader logger = new FXMLLoader();
        logger.setLocation(getClass().getResource("/maplab/com/lab7/Views/loggercontroller-view.fxml"));
        root = logger.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Welcome to DotDot!");
        ILoggerController loggerController = logger.getController();
        loggerController.setService(service, stage);
        stage.show();
    }
}
////        File userRepoFile = new File("userRepo");
////        userRepoFile.createNewFile();
////        File friendsRepoFile = new File("friendshipRepo");
////        friendsRepoFile.createNewFile();
////        UserValidator userValidator = new UserValidator();
////        UserInFileRepo userRepo = new UserInFileRepo("userRepo", userValidator);
////        FriendshipValidator friendshipValidator = new FriendshipValidator();
////        FriendshipInFileRepo friendsRepo = new FriendshipInFileRepo("friendshipRepo", friendshipValidator);
////        NetworkService service = new NetworkService(userRepo, friendsRepo);
////        UserInterface newUI = new UserInterface(service);
////        newUI.runUI();
//
//        UserDBRepo repodb = new UserDBRepo("jdbc:postgresql://localhost:5432/LabMAP", "postgres", "postgres",
//                new UserValidator());
//
////        User newUser = new User(UUID.fromString("6d7d1673-a58a-46ba-9608-a84c3d207436"), "user9", "pass9");
////        repodb.add(newUser);
////        repodb.readAll().forEach(System.out::println);
////        repodb.delete(newUser);
////        repodb.readAll().forEach(System.out::println);
////        User readUser = repodb.readByID(UUID.fromString("bbf8631e-438a-4013-9c1e-68a8bf08831b"));
////        System.out.println(readUser);
////        int usercount = repodb.size();
////        System.out.println(usercount);
////        repodb.update(UUID.fromString("bbf8631e-438a-4013-9c1e-68a8bf08831b"), "newusername",
////                "newpassword");
////        User readUser = repodb.getUser("newusername");
////        System.out.println(readUser);
//        FriendshipDBRepo frepodb = new FriendshipDBRepo("jdbc:postgresql://localhost:5432/LabMAP", "postgres", "postgres",
//                new FriendshipValidator());
////        Friendship newFriendship = new Friendship(UUID.fromString("bbf8631e-438a-4013-9c1e-68a8bf088f1b"), UUID.fromString("bbf8631e-438a-4013-9c1e-68a8bf08821b"));
////        Friendship newFriendship2 = new Friendship(UUID.fromString("bbf8631e-438a-4013-9c1e-68a8bf08821b"), UUID.fromString("bbf8631e-438a-4013-9c1e-68a8bf088f1b"));
////
////        frepodb.add(newFriendship);
////        Friendship read = frepodb.readByID(UUID.fromString("75da33b2-371e-41c9-a546-9bc0d545698e"));
////        System.out.println(read);
////        frepodb.readAll().forEach(System.out::println);
////        frepodb.delete(newFriendship2);
////        LocalDate newdate = LocalDate.of(2023, 11, 20);
////        frepodb.update(UUID.fromString("75da33b2-371e-41c9-a546-9bc0d545698e"), newdate);
////        User newUser1 = repodb.readByID(UUID.fromString("bbf8631e-438a-4013-9c1e-68a8bf088f1b"));
////        User newUser2 = repodb.readByID(UUID.fromString("bbf8631e-438a-4013-9c1e-68a8bf08821b"));
////
////        Friendship readFriendship = frepodb.readFriendship(newUser1, newUser2);
//////        Friendship readFriendship2 = frepodb.readByID(UUID.fromString("75da33b2-371e-41c9-a546-9bc0d545698e"));
////        System.out.println(readFriendship);

//        UserDBRepo newUserDatabase = new UserDBRepo("jdbc:postgresql://localhost:5432/LabMAP", "postgres", "postgres",
//                new UserValidator());
//        FriendshipDBRepo newFriendshipDatabase = new FriendshipDBRepo("jdbc:postgresql://localhost:5432/LabMAP", "postgres", "postgres",
//                new FriendshipValidator());
//        NetworkService newService = new NetworkService(newUserDatabase, newFriendshipDatabase);



//        UserInterface newUI = new UserInterface(newService);
//        newUI.runUI();
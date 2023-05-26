package maplab.com.lab7;

public class Main {
    public static void main(String[] args) {
//        UserInterface ui = new UserInterface(new NetworkService(new UserDBRepo("jdbc:postgresql://localhost:5432/LabMAP", "postgres", "postgres", new UserValidator()), new FriendshipDBRepo("jdbc:postgresql://localhost:5432/LabMAP", "postgres", "postgres", new FriendshipValidator())));
//        ui.runUI();
        MainApp.main(args);

    }
}

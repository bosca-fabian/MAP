package UserInterface;

import Domain.Friendship;
import Domain.User;
import NetworkService.NetworkService;
import Utils.Tuple;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * User Interface class that implements a console type interface to communicate with the user
 */
public class UserInterface implements IUserInterface {

    private NetworkService UINetworkService = new NetworkService();

    /**
     * UserInterface class constructor with no parameters
     */
    public UserInterface(){}

    public UserInterface(NetworkService service){
        this.UINetworkService = service;
    }
//    private static volatile UserInterface UserInterfaceInstance = null;
//
//    public static UserInterface getInstance(){
//        if (UserInterfaceInstance == null) {
//            synchronized(UserInterface.class) {
//                if (UserInterfaceInstance == null) {
//                    UserInterfaceInstance = new UserInterface();
//                }
//            }
//        }
//
//        return UserInterfaceInstance;
//    }

    /**
     * UserInterface method that shows the first menu
     */
    private void showMenu(){
        System.out.println("""
                1. Create account
                2. LogIn
                3. Exit
                4. Admin""");
    }

//    private void showLoggedInMenu(){
//        System.out.println("""
//                1. Add friend
//                2. Show friends
//                3. Remove friend
//                4. Exit""");
//    }

    /**
     * Scanner method to get console input from user for username and password
     * @return Tuple<String, String> data containing console inputted username and password
     */
    private Tuple<String, String> ScannerUIUsernamePassword(){
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = keyboard.nextLine();
        System.out.println("Enter password: ");
        String password = keyboard.nextLine();
        return new Tuple<>(username, password);
    }

    /**
     * Scanner method to get console input from user for usernames
     * @return Tuple<String, String> data containing console inputted usernames
     */

    private Tuple<String, String> ScannerUIUsernameTuple(){
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter first username: ");
        String firstUsername = keyboard.nextLine();
        System.out.println("Enter second username: ");
        String secondUsername = keyboard.nextLine();
        return new Tuple<>(firstUsername, secondUsername);
    }

    /**
     * UserInterface method that calls the service in order to create an account
     */
    private void handleUICreateAccount(){
        Tuple<String, String> userAndPassword = ScannerUIUsernamePassword();
        try {
            this.UINetworkService.addUserToNetwork(userAndPassword.x(), userAndPassword.y());
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

//    private User handleLogIN(){
//            Scanner keyboard = new Scanner(System.in);
//            System.out.println("Enter username: ");
//            String username = keyboard.nextLine();
//            System.out.println("Enter password: ");
//            String password = keyboard.nextLine();
//            return this.UINetworkService.checkForUser(username, password);
//    }
//    //TODO finish method
//    private void handleAddFriendUI(){
//        Scanner keyboard = new Scanner(System.in);
//        System.out.println("Enter the name of the friend: ");
//        String givenName = keyboard.nextLine();
//
//
//    }

//    private void handleLoggedIN(User connectedUser){
//        boolean run = true;
//        while(run){
//            showLoggedInMenu();
//            Scanner keyboard = new Scanner(System.in);
//            System.out.println("Choose option: ");
//            int option = keyboard.nextInt();
//            switch (option){
//                case 1:
//                    handleAddFriendUI();
//                case 2:
//
//                case 3:
//
//                case 4:
//                    run = false;
//            }
//        }
//    }

    /**
     * UserInterface method that shows admin console interface
     */

    private void showAdminCommands(){
        boolean run = true;
        while(run) {
            try {
                handleAdminUI();
                Scanner keyboard = new Scanner(System.in);
                System.out.println("Choose an option: ");
                int option = keyboard.nextInt();
                switch (option) {
                    //TODO: change switch statements from : to ->
                    case 1:
                        handleAddUserUI();
                        break;
                    case 2:
                        handleDeleteUserUI();
                        break;
                    case 3:
                        handleUpdateUserUI();
                        break;
                    case 4:
                        handleCreateFriendShipUI();
                        break;
                    case 5:
                        handleDeleteFriendshipUI();
                        break;
                    case 6:
                        handleUpdateFriendshipUI();
                        break;
                    case 7:
                        showAllUsers();
                        break;
                    case 8:
                        showAllFriendships();
                        break;
                    case 9:
                        run = false;
                    default:
                        System.out.println("Option doesn't exit");
                }
            }catch (Exception e){
                System.out.println("Invalid input");
            }
        }
    }

    /**
     * UserInterface method for adding a new user
     */

    private void handleAddUserUI() {
        try {
            Tuple<String, String> UserAndPassword = ScannerUIUsernamePassword();
            this.UINetworkService.addUserToNetwork(UserAndPassword.x(), UserAndPassword.y());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * UserInterface method for updating an existing user
     */
    private void handleUpdateUserUI(){
        try{
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Enter ID of user you want to change: ");
            String id = keyboard.nextLine();
            Tuple<String, String> userAndPass = ScannerUIUsernamePassword();

            this.UINetworkService.updateUser(id, userAndPass.x(), userAndPass.y());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * UserInterface method for updating a friendship between users
     */

    private void handleUpdateFriendshipUI(){
        try{
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Enter the ID of the friendship: ");
            String id = keyboard.nextLine();
            System.out.println("Enter the new date(use DD/MM/YYYY format: ");
            String date = keyboard.nextLine();
            this.UINetworkService.updateFriendship(id, date);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * UserInterface method that calls the service to delete a user
     */

    private void handleDeleteUserUI() {
        try {
            Scanner keyboard = new Scanner(System.in);
            String username = keyboard.nextLine();
            this.UINetworkService.deleteUserFromNetwork(username);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * UserInterface method that calls the service to deal with the creation of a friendship between two users
     */
    private void handleCreateFriendShipUI() {
        try {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Enter ID of first friend: ");
            String firstID = keyboard.nextLine();
            System.out.println("Enter the ID of second friend: ");
            String secondID = keyboard.nextLine();
            this.UINetworkService.createFriendship(firstID, secondID);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * UserInterface method that calls the service to deal with the deletion of a friendship
     */
    private void handleDeleteFriendshipUI() {
        try {
            Tuple<String, String> Friends = ScannerUIUsernameTuple();
            this.UINetworkService.deleteFriendship(Friends.x(), Friends.y());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * UserInterface method that shows all the existing users
     */
    private void showAllUsers(){
        List<User> users = this.UINetworkService.returnAllUsers();
        if(users.isEmpty())
            System.out.println("No users");
        else
            for (User user : users) {
                System.out.println(user);
                System.out.println('\n');
            }
    }

    /**
     * UserInterface method that shows all the existing friendships
     */
    private void showAllFriendships(){
        List<Friendship> friendships = this.UINetworkService.returnAllFriendship();
        if(friendships.isEmpty())
            System.out.println("No friendships");
        else
            for (Friendship friendship : friendships){
                System.out.println(friendship);
                System.out.println('\n');
            }
    }


    /**
     * UserInterface method that shows the admin menu
     */
    private void handleAdminUI(){
        System.out.println("""
                1. Add user
                2. Remove user
                3. Update user
                4. Add friendship
                5. Remove friendship
                6. Update friendship
                7. Show all users
                8. Show all friendships
                9. Exit""");
    }


    /**
     * maplab.com.lab7.Main UserInterface method that runs the UserInterface
     */

    @Override
    public void runUI(){
        boolean run = true;
        while(run){
            showMenu();
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Choose an option");
            int option = keyboard.nextInt();
            switch (option){
                case 1:
                    handleUICreateAccount();
                    break;
                case 2:
//                    User connectedUser = handleLogIN();
//                    if(connectedUser != null)
//                        handleLoggedIN(connectedUser);
                    break;
                case 3 :
                    run = false;
                case 4:
                    showAdminCommands();
                default:
                    System.out.println("Invalid input!");
            }
        }
    }

}

package NetworkService;

import Domain.FriendRequest;
import Domain.Friendship;
import Domain.Message;
import Domain.User;
import Domain.Validators.*;
import Repository.DatabaseRepo.FriendRequestsDBRepo;
import Repository.DatabaseRepo.FriendshipDBRepo;
import Repository.DatabaseRepo.MessageDBRepo;
import Repository.DatabaseRepo.UserDBRepo;

import Utils.ControllerObserver;
import Utils.ServiceObserver;

import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;

/**
 * Service class that deals with all the operations between users, friendship; operates on the repository
 */
public class NetworkService implements INetworkService {
    UserDBRepo repository;
    FriendshipDBRepo friendshipsRepository;

    FriendRequestsDBRepo requestsDBRepo;

    MessageDBRepo messageDBRepo;

    public NetworkService(UserDBRepo givenUserRepo, FriendshipDBRepo givenFriendshipRepo, FriendRequestsDBRepo reqDBRepo, MessageDBRepo MessageDbREpo){
        this.repository = givenUserRepo;
        this.friendshipsRepository = givenFriendshipRepo;
        this.requestsDBRepo = reqDBRepo;
        this.messageDBRepo = MessageDbREpo;
    }

    /**
     * network service constructor with no parameters
     */
    public NetworkService(){}

    /**
     * Method that adds a user to the user repository
     * @param userName the name of the user
     * @param password the password of the user
     */
    public void addUserToNetwork(String userName, String password) throws SQLException {
//        User possibleDuplicate = repository.getUser(userName);
//        if(possibleDuplicate != null)
//            throw new EntityAlreadyExistsException("The user already exists");
        User newUser = new User(userName, password);

        try{
            repository.add(newUser);
        }catch (SQLException e){
            throw new SQLException();
        }
    }

    /**
     * method that deletes a user from the userrepository
     * @param givenUserName the name of the user to be deleted
     */
    public void deleteUserFromNetwork(String givenUserName){
        User neededUser = this.readUser(givenUserName);
        if(neededUser == null)
            throw new NoSuchElementException("No such user exists");
        deleteFriendshipCascade(neededUser);
        repository.delete(neededUser);
    }

    /**
     * method that returns all the users of the user repository
     * @return list of all users
     */
    public List<User> returnAllUsers(){
        return this.repository.readAll();
    }

    /**
     * method that returns all the friendships of the friendship repository
     * @return list of all the friendships
     */
    public List<Friendship> returnAllFriendship(){
        return this.friendshipsRepository.readAll();
    }

    /**
     * method that creates and adds a friendship to the friendship repository
     * @param firstUser the name of the first user of the friendship
     * @param secondUser the name of the second user of the friendship
     */
    public void createFriendship(String firstUser, String secondUser){
        User firstFriend = readUser(UUID.fromString(firstUser));

        if(firstFriend == null)
            throw new NoSuchElementException("No such user exists");

        User secondFriend = readUser(UUID.fromString(secondUser));
        if(secondFriend == null)
            throw new NoSuchElementException("No such user exists");

        Friendship newFriendship = new Friendship(firstFriend.getId(), secondFriend.getId(), LocalDate.now());
        this.friendshipsRepository.add(newFriendship);
        this.notifyObservers();
    }

    /**
     * private method that deals with the deletion of the friendship of n user that has been deleted
     * @param givenUser the user
     */
    private void deleteFriendshipCascade(User givenUser) {
        List<Friendship> friendships = new ArrayList<>(this.friendshipsRepository.readAll());
        for (Friendship friendshipIn : friendships) {
            UUID firstFriendID = friendshipIn.getFirstFriend();
            UUID secondFriendID = friendshipIn.getSecondFriend();
            User firstFriendUser = readUser(firstFriendID);
            User secondFriendUser = readUser(secondFriendID);

            if (firstFriendUser.getUsername().equals(givenUser.getUsername())) {
//                secondFriendUser.removeFriend(firstFriendUser.getUsername());
                this.friendshipsRepository.delete(friendshipIn);
            }
            if (secondFriendUser.getUsername().equals(givenUser.getUsername())){
//                firstFriendUser.removeFriend(secondFriendUser.getUsername());
                this.friendshipsRepository.delete(friendshipIn);
            }
        }
    }

    public ArrayList<User> getUsersBasedOnString(String givenString, String givenUser){
        return this.repository.getUsers(givenString, givenUser);
    }

    public void updateUser(String ID, String username, String password){
        if(!repository.update(UUID.fromString(ID), username, password))
            throw new NoSuchElementException("The user doesn't exist");
    }

    public void deleteFriendRequest(FriendRequest givenRequest){
        this.requestsDBRepo.delete(givenRequest);
    }

    public void updateFriendship(String ID, String newDate) throws DateTimeException {
        DateFormatValidator dateFormatValidator = new DateFormatValidator();
        dateFormatValidator.validate(newDate);
        String[] attr = newDate.split("/");
        LocalDate tempDate = LocalDate.of(
                Integer.parseInt(attr[2]), Integer.parseInt(attr[1]), Integer.parseInt(attr[0]));
        if(!this.friendshipsRepository.update(UUID.fromString(ID), tempDate))
            throw new NoSuchElementException("The friendship doesn't exit");
    }

    /**
     * method that deletes a friendship from the friendship repository
     * @param firstUser the first user of the friendship
     * @param secondUser the second user of the friendship
     */
    public void deleteFriendship(String firstUser, String secondUser){
        User firstFriend = readUser(firstUser);
        if (firstFriend == null)
            throw new NoSuchElementException("Friendship doesn't exist");

        User secondFriend = readUser(secondUser);
        if (secondFriend == null)
            throw new NoSuchElementException("Friendship doesn't exist");


        Friendship tempFriendship = new Friendship(firstFriend.getId(), secondFriend.getId());
        this.friendshipsRepository.delete(tempFriendship);
    }

    public ArrayList<FriendRequest> readAllFriendshipRequests(User givenUser, boolean receiver){
        return requestsDBRepo.readFriendRequests(givenUser, receiver);

    }

    public void createFriendRequest(String sender, String receiver){
        try {
            if(!this.friendshipsRepository.readFriendship(readUser(sender), readUser(receiver)))
                this.requestsDBRepo.add(new FriendRequest(sender, receiver));
        } catch (SQLException e) {
            System.out.println("Friendship exists!");
        }
    }


    public ArrayList<Friendship> readAllFriendshipsForUser(User givenUser){
        ArrayList<Friendship> friendships = friendshipsRepository.readForCertainUser(givenUser);
        for (Friendship friendship: friendships) {
            if(friendship.getSecondFriend().equals(givenUser.getId())){
                friendship.setSecondFriend(friendship.getFirstFriend());
                friendship.setFirstFriend(givenUser.getId());
            }
        }
        return friendships;
    }

    /**
     * method that returns n user from the userrepository based on the given username
     * @param userName the username of the user
     * @return the user or null if there are no users
     */
    public User readUser(String userName){
        return repository.getUser(userName);
    }

    public User readUser(UUID userID){
        return repository.readByID(userID);
    }

    private List<ControllerObserver> observers = new ArrayList<>();

    public void addObserver(ControllerObserver e){
            observers.add(e);
    }

    public void notifyObservers(){
        observers.forEach(ControllerObserver::update);
    }

    public void addMessageToDB(String sender, String receiver, String Message) throws SQLException {
        this.messageDBRepo.add(new Message(sender, receiver, Message));
        notifyObservers();
    }


    public ArrayList<Message> readMessagesFromDB(String user, String friend){
        return this.messageDBRepo.readMessagesBetweenUsers(user, friend);
    }


}
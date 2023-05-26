package Domain;

import Repository.Friendships;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * User class that extends Entity
 */
public class User extends Entity {
    private String username;
    private String password;

    private List<String> friends = new ArrayList<>();

    /**
     * Constructor for the User class
     *
     * @param username the username of the user
     * @param password the password of the user
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(UUID ID, String username, String password){
        super(ID);
        this.username = username;
        this.password = password;
    }

    /**
     * constructor for user
     */
    public User() {

    }

    /**
     * Overridden equals method that checks whether 2 users are the same
     *
     * @param o the seocnd user
     * @return boolean true if the users are the same, false othewise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username) && password.equals(user.password);
    }

    /**
     * overridden haschode method, returns the haschode of the user
     *
     * @return the hashcode of the user
     */

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    /**
     * getter for the username of a user
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * getter for the password of a user
     *
     * @return the password of a user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Adds a friend to the friend list of a user
     *
     * @param friendName the name of the friend to be added to the friends list
     */
    public void addFriend(String friendName) {
        this.friends.add(friendName);
    }

    /**
     * removes a friend from the friendst
     *
     * @param friendName the name of the friend to be removed
     */
    public void removeFriend(String friendName) {
        this.friends.remove(friendName);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' + ',' +
                "password='" + password + '\'' +
                '}';
    }
}
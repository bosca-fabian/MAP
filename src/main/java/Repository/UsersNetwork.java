package Repository;

import Domain.Entity;
import Domain.User;
import Domain.Validators.SuperValidator;
import Domain.Validators.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * User repo class that extends the supermemoryrepository
 */
public class UsersNetwork extends SuperMemoryRepository<User> {
    /**
     * UserNetwork constructor with an usesrvalidator given as a parameter
     * @param userValidator the given uservalidator
     */
    public UsersNetwork(SuperValidator<User> userValidator){
        super(userValidator);
    }

    public boolean update(UUID userID, String newUsername, String newPassword) {
        for (User user : repository)
            if (user.getId() == userID) {
                user.setUsername(newUsername);
                user.setPassword(newPassword);
                return true;
            }
        return false;
    }

    /**
     * method that returns the user having the given username
     * @param username the given username
     * @return the needed username, null if no such user exists
     */
    public User getUser(String username){
        for(User userIn : repository){
            if(Objects.equals(userIn.getUsername(), username)){
                return userIn;
            }
        }
        return null;
    }
}

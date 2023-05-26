package NetworkService.EntitiesServiceInterfaces;

import Domain.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface IUserNetworkService {
    void addUserToNetwork(String userName, String password) throws SQLException;
    void deleteUserFromNetwork(String givenUserName);
    List<User> returnAllUsers();
    ArrayList<User> getUsersBasedOnString(String givenString, String givenUser);
    void updateUser(String ID, String username, String password);
    User readUser(String userName);
    User readUser(UUID userID);
}

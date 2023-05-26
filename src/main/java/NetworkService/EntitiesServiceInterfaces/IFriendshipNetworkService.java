package NetworkService.EntitiesServiceInterfaces;

import Domain.Friendship;
import Domain.User;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;

public interface IFriendshipNetworkService {
    void createFriendship(String firstUser, String secondUser);
    void updateFriendship(String ID, String newDate) throws DateTimeException;
    void deleteFriendship(String firstUser, String secondUser);
    List<Friendship> returnAllFriendship();
    ArrayList<Friendship> readAllFriendshipsForUser(User givenUser);
}

package Repository.DatabaseRepo;

import Domain.FriendRequest;
import Domain.Friendship;
import Domain.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public interface IFriendRequestsDBRepo {
    void add(FriendRequest request) throws SQLException;

    void delete(FriendRequest request);

    ArrayList<FriendRequest> readFriendRequests(User givenUser, boolean senderReceiver);
}

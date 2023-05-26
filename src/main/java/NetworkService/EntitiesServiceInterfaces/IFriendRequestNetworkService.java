package NetworkService.EntitiesServiceInterfaces;

import Domain.FriendRequest;
import Domain.User;

import java.util.ArrayList;

public interface IFriendRequestNetworkService {
    void deleteFriendRequest(FriendRequest givenRequest);
    ArrayList<FriendRequest> readAllFriendshipRequests(User givenUser, boolean receiver);
    void createFriendRequest(String sender, String receiver);
}

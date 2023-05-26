package Controllers.ControllersInterfaces;

import Domain.User;
import NetworkService.INetworkService;

public interface ISearchUserController {
    void setService(INetworkService givenService, User connectedUser);
    void search();
    void sendFriendRequest();
}

package Controllers.ControllersInterfaces;

import Domain.User;
import NetworkService.INetworkService;
import Utils.ControllerObserver;

public interface IFriendshipController extends ControllerObserver {
    void setService(INetworkService givenService, User connectedUser);
    void deleteFriend();
    void cancelFriendRequests();
    void checkFriendRequests();
    void chatroom();
}


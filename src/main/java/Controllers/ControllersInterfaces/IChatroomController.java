package Controllers.ControllersInterfaces;

import NetworkService.INetworkService;
import Utils.ControllerObserver;

public interface IChatroomController extends ControllerObserver {
    void setService(INetworkService givenService, String logged, String friend);
    void sendMessage();
    void update();
}

package Controllers.ControllersInterfaces;

import Domain.User;
import NetworkService.INetworkService;

public interface IUpdateAccountController {
    void setService(INetworkService givenService, User givenUser);
    void updateButton();
}

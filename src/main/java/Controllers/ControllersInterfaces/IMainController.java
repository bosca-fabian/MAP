package Controllers.ControllersInterfaces;

import Domain.User;
import NetworkService.INetworkService;
import javafx.stage.Stage;

public interface IMainController {
    void setService(INetworkService givenService, User givenLoggedUser, Stage givenStage);
    void onBtnAClick();
    void updateInfoButton();
    void checkFriendshipsButton();
    void searchUsersButton();
    void disconnect();
}

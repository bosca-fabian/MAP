package Controllers.ControllersInterfaces;

import NetworkService.INetworkService;
import javafx.stage.Stage;

public interface ICreateAccountController {
    void CreateAccountButton();
    void setService(INetworkService givenService, Stage givenStage);
}

package Controllers.ControllersInterfaces;

import NetworkService.INetworkService;
import javafx.stage.Stage;

public interface ILoggerController {
    void setService(INetworkService givenService, Stage givenStage);
    void showCreateAccountDialog();
    void logIn();
}

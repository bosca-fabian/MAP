package NetworkService.EntitiesServiceInterfaces;

import Domain.Message;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IMessagesNetworkService {
    void addMessageToDB(String sender, String receiver, String Message) throws SQLException;
    ArrayList<Message> readMessagesFromDB(String user, String friend);
}

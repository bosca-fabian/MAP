package Repository.DatabaseRepo;

import Domain.Message;
import Domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class MessageDBRepo {

    private String url;
    private String logInusername;
    private String logInpassword;


    public MessageDBRepo(String url, String logInusername, String logInpassword) {
        this.url = url;
        this.logInusername = logInusername;
        this.logInpassword = logInpassword;
    }

    public MessageDBRepo() {

    }

    public void add(Message entity) throws SQLException {
        String sql = "insert into \"Message\" (sender, receiver, message, timestamp) values( ?, ?, ?, current_timestamp)";

        try (Connection connection = DriverManager.getConnection(url, logInusername, logInpassword);
             PreparedStatement ps = connection.prepareStatement(sql)) {

//            ps.setString(1, entity.getId().toString());
            ps.setString(1, entity.getSender());
            ps.setString(2, entity.getReceiver());
            ps.setString(3, entity.getMessage());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException();
        }
    }

    public void deleteAll() {
        String sql = "TRUNCATE TABLE \"Message\" RESTART IDENTITY;";
        try (Connection connection = DriverManager.getConnection(url, logInusername, logInpassword);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Message> read(){
        String sql = "SELECT * FROM \"Message\" ORDER BY timestamp ASC";
        ArrayList<Message> messages = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, logInusername, logInpassword);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String sender = resultSet.getString("sender");
                String receiver = resultSet.getString("receiver");
                String message = resultSet.getString("message");

                Message newMessage = new Message(sender, receiver, message);
                messages.add(newMessage);
            }
//            deleteAll();
            return messages;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Message> readMessagesBetweenUsers(String firstUser, String secondUser){
        String sql = "SELECT * FROM \"Message\" WHERE (\"sender\" = ? AND \"receiver\" = ?) OR (\"sender\" = ? AND \"receiver\" = ?) ORDER BY timestamp ASC;";
        ArrayList<Message> messages = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, logInusername, logInpassword);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, firstUser);
            ps.setString(2, secondUser);
            ps.setString(3, secondUser);
            ps.setString(4, firstUser);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String sender = resultSet.getString("sender");
                String receiver = resultSet.getString("receiver");
                String message = resultSet.getString("message");

                Message newMessage = new Message(sender, receiver, message);
                messages.add(newMessage);
            }
//            deleteAll();
            return messages;
    } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
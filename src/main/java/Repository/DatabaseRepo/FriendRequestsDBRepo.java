package Repository.DatabaseRepo;

import Domain.FriendRequest;

import Domain.User;

import java.sql.*;
import java.util.ArrayList;


public class FriendRequestsDBRepo implements IFriendRequestsDBRepo{
    private String url;
    private String logInusername;
    private String logInpassword;


    public FriendRequestsDBRepo(String url, String logInusername, String logInpassword) {
        this.url = url;
        this.logInusername = logInusername;
        this.logInpassword = logInpassword;
    }


    public void add(FriendRequest request) throws SQLException {
        String sql = "insert into \"FriendRequests\" (sender, receiver) values (?, ?)";

        try (Connection connection = DriverManager.getConnection(url, logInusername, logInpassword);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, request.getSender());
            ps.setString(2, request.getReceiver());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException();
        }

    }

    public void delete(FriendRequest request){
        String sql = "DELETE FROM \"FriendRequests\" WHERE sender = '" + request.getSender() + "' AND " +
                "receiver = '" + request.getReceiver() + "';";

        try (Connection connection = DriverManager.getConnection(url, logInusername, logInpassword);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<FriendRequest> readFriendRequests(User givenUser, boolean senderReceiver){
        String sql;
        if(senderReceiver) {
             sql = "SELECT * FROM \"FriendRequests\" WHERE receiver ='" + givenUser.getUsername() + "';";
        }
        else {
             sql = "SELECT * FROM \"FriendRequests\" WHERE sender ='" + givenUser.getUsername() + "';";
        }
        ArrayList<FriendRequest> requests = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, logInusername, logInpassword);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while(resultSet.next()){
                String sender = resultSet.getString("sender");
                String receiver = resultSet.getString("receiver");
                requests.add(new FriendRequest(sender, receiver));}
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }
}

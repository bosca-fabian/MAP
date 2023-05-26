package Repository.DatabaseRepo;

import Domain.Friendship;
import Domain.User;
import Domain.Validators.Validator;
import Repository.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FriendshipDBRepo implements Repository<Friendship> {

    private String url;
    private String logInusername;
    private String logInpassword;

    private Validator<Friendship> friendshipValidator;

    public FriendshipDBRepo(String url, String logInusername, String logInpassword, Validator<Friendship> friendshipValidator) {
        this.url = url;
        this.logInusername = logInusername;
        this.logInpassword = logInpassword;
        this.friendshipValidator = friendshipValidator;
    }

    public FriendshipDBRepo(){

    }

    @Override
    public void add(Friendship entity) {
        this.friendshipValidator.validate(entity);

        String sql = "insert into \"Friendships\" (id, firstuserid, seconduserid, friendsfrom) values (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, logInusername, logInpassword);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, entity.getId().toString());
            ps.setString(2, entity.getFirstFriend().toString());
            ps.setString(3, entity.getSecondFriend().toString());
            java.sql.Date sqlDate = java.sql.Date.valueOf(entity.getFriendsFrom());
            ps.setDate(4, sqlDate);
//

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean update(UUID friendshipID, LocalDate newDate){
        java.sql.Date sqlDate = java.sql.Date.valueOf(newDate);
        String sql = "UPDATE \"Friendships\" SET friendsfrom = '" + sqlDate + "' WHERE id = '" + friendshipID.toString() + "';";
        try (Connection connection = DriverManager.getConnection(url, logInusername, logInpassword);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Friendship entity) {


        String sql = "DELETE FROM \"Friendships\" WHERE firstuserid = '" + entity.getFirstFriend().toString() + "' OR " +
                "firstuserid = '" + entity.getSecondFriend().toString() + "';";

        try (Connection connection = DriverManager.getConnection(url, logInusername, logInpassword);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Friendship> readForCertainUser(User givenUser){
        String sql = "SELECT * FROM \"Friendships\" WHERE (firstuserid  = '" + givenUser.getId() + "'" + "OR " +
                "seconduserid = '" + givenUser.getId() + "')";
        return readFriendship(sql);
    }

    private ArrayList<Friendship> readFriendship(String sql){

        ArrayList<Friendship> friendships = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, logInusername, logInpassword);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while(resultSet.next()){
            UUID id = UUID.fromString(resultSet.getString("id"));
            UUID firstUserId = UUID.fromString(resultSet.getString("firstuserid"));
            UUID secondUserID = UUID.fromString(resultSet.getString("seconduserid"));
            LocalDate friendsFrom = resultSet.getTimestamp("friendsfrom").toLocalDateTime().toLocalDate();
            friendships.add(new Friendship(id, firstUserId, secondUserID, friendsFrom));}
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendships;
    }

    @Override
    public Friendship readByID(UUID entity) {
        String sql = "SELECT * FROM \"Friendships\" WHERE id = '" + entity.toString() + "';";

        return read(sql);
    }

    private Friendship read(String sql){
        try (Connection connection = DriverManager.getConnection(url, logInusername, logInpassword);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            UUID id = UUID.fromString(resultSet.getString("id"));
            UUID firstUserId = UUID.fromString(resultSet.getString("firstuserid"));
            UUID secondUserID = UUID.fromString(resultSet.getString("seconduserid"));
            LocalDate friendsFrom = resultSet.getTimestamp("friendsfrom").toLocalDateTime().toLocalDate();
            return new Friendship(id, firstUserId, secondUserID, friendsFrom);
        } catch (SQLException e) {
            return null;
        }

    }

    public boolean readFriendship(User firstFriend, User secondFriend){
        String sql = "SELECT * FROM \"Friendships\" WHERE seconduserid = LEAST('" + firstFriend.getId() + "', '" + secondFriend.getId() +"' ) " +
                "AND firstuserid = GREATEST('" + firstFriend.getId() + "', '" +secondFriend.getId() + "' );";
            return read(sql) != null;
    }

    @Override
    public List<Friendship> readAll() {
        List<Friendship> friendships = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, logInusername, logInpassword);
             PreparedStatement statement = connection.prepareStatement("SELECT * from \"Friendships\"");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                UUID firstfriend = UUID.fromString(resultSet.getString("firstuserid"));
                UUID secondfriend = UUID.fromString(resultSet.getString("seconduserid"));
                LocalDate friendsFrom = resultSet.getTimestamp("friendsfrom").toLocalDateTime().toLocalDate();
                Friendship newFriendship = new Friendship(id, firstfriend, secondfriend, friendsFrom);
                friendships.add(newFriendship);
            }
            return friendships;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendships;
    }

    @Override
    public int size() {
        String sql = "SELECT COUNT(*) as totalFriendships FROM \"Friendships\"";
        try (Connection connection = DriverManager.getConnection(url, logInusername, logInpassword);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            return resultSet.getInt("totalFriendships");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

package Repository.DatabaseRepo;

import Domain.User;
import Domain.Validators.Validator;
import Repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.sql.*;

public class UserDBRepo implements Repository<Domain.User>{
    private String url;
    private String logInusername;
    private String logInpassword;
    private Validator<User> userValidator;

    public UserDBRepo(String url, String username, String password, Validator<User> userValidator) {
        this.url = url;
        this.logInusername = username;
        this.logInpassword = password;
        this.userValidator = userValidator;
    }

    public UserDBRepo(){

    }

    @Override
    public void add(User entity) throws SQLException{

        this.userValidator.validate(entity);

        String sql = "insert into \"Users\" (id, username, password) values (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, logInusername, logInpassword);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, entity.getId().toString());
            ps.setString(2, entity.getUsername());
            ps.setString(3, entity.getPassword());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException();
        }
    }


    public boolean update(UUID userID, String newUsername, String newPassword){

        String sql = "UPDATE \"Users\" SET username = '" + newUsername + "', " + "password = '" + newPassword +"' " +
                "WHERE id = '" + userID.toString() + "';";
        try (Connection connection = DriverManager.getConnection(url, logInusername, logInpassword);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(User entity) {
        String sql = "DELETE FROM \"Users\" WHERE id = '" + entity.getId().toString() + "';";

        try (Connection connection = DriverManager.getConnection(url, logInusername, logInpassword);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public User readByID(UUID entity) {

        String sql = "SELECT * FROM \"Users\" WHERE id = '" + entity.toString() + "';";

        try (Connection connection = DriverManager.getConnection(url, logInusername, logInpassword);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            UUID id = UUID.fromString(resultSet.getString("id"));
            String readUsername = resultSet.getString("username");
            String readPassword = resultSet.getString("password");
            return new User(id, readUsername, readPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<User> getUsers(String givenString, String givenUsername){
        String sql = "SELECT * FROM \"Users\" WHERE username LIKE '" + givenString + "%' AND username != '" +
                givenUsername + "';";
        ArrayList<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, logInusername, logInpassword);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                String firstName = resultSet.getString("username");
                String lastName = resultSet.getString("password");

                User readUser = new User(id, firstName, lastName);
                users.add(readUser);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User getUser(String givenUsername) {

        String sql = "SELECT * FROM \"Users\" WHERE username = '" + givenUsername + "';";

        try (Connection connection = DriverManager.getConnection(url, logInusername, logInpassword);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            UUID id = UUID.fromString(resultSet.getString("id"));
            String readUsername = resultSet.getString("username");
            String readPassword = resultSet.getString("password");
            return new User(id, readUsername, readPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> readAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, logInusername, logInpassword);
             PreparedStatement statement = connection.prepareStatement("SELECT * from \"Users\"");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                String firstName = resultSet.getString("username");
                String lastName = resultSet.getString("password");

                User readUser = new User(id, firstName, lastName);
                users.add(readUser);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public int size() {
        String sql = "SELECT COUNT(*) as totalUsers FROM \"Users\"";
        try (Connection connection = DriverManager.getConnection(url, logInusername, logInpassword);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            return resultSet.getInt("totalUsers");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


}


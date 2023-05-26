package Repository.FileRepository;

import Domain.User;
import Domain.Validators.Validator;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UserInFileRepo extends InFileRepo<User>{

    public UserInFileRepo(String fileName, Validator<User> givenValidator) {
        super(fileName, givenValidator);
    }

    @Override
    protected String createEntityAsString(User givenEntity) {
        return givenEntity.getId() + ";" + givenEntity.getUsername() + ";" + givenEntity.getPassword();
    }

    @Override
    protected User extractEntity(List<String> attributes) {
        return new User(UUID.fromString(attributes.get(0)), attributes.get(1), attributes.get(2));
    }

    public User getUser(String username){
        List<User> tempUsers = readAll();
        for(User userIn : tempUsers){
            if(Objects.equals(userIn.getUsername(), username)){
                return userIn;
            }
        }
        return null;
    }

    public boolean update(UUID userID, String newUsername, String newPassword) throws FileNotFoundException {
        List<User> repository = readAll();
        for (User user : repository)
            if (Objects.equals(userID, user.getId())) {
                user.setUsername(newUsername);
                user.setPassword(newPassword);
                emptyFile();
                writeAllDataToFile(repository);
                return true;
            }
        return false;
    }

}

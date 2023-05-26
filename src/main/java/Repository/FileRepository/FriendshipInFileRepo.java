package Repository.FileRepository;

import Domain.Friendship;
import Domain.User;
import Domain.Validators.Validator;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class FriendshipInFileRepo extends InFileRepo<Friendship> {

    public FriendshipInFileRepo(String fileName, Validator<Friendship> givenValidator) {
        super(fileName, givenValidator);
    }

    @Override
    protected String createEntityAsString(Friendship givenEntity) {
        return givenEntity.getId() + ";" + givenEntity.getFirstFriend() + ";" + givenEntity.getSecondFriend() + ";"
                + givenEntity.getFriendsFrom();
    }

    @Override
    protected Friendship extractEntity(List<String> attributes) {
        return new Friendship(UUID.fromString(
                attributes.get(0)),
                UUID.fromString(attributes.get(1)),
                UUID.fromString(attributes.get(2)),
                LocalDate.parse(attributes.get(3)));
    }

    public boolean update(UUID friendshipID, LocalDate newDate) throws FileNotFoundException {
        List<Friendship> repository = readAll();
        for(Friendship friendship : repository)
            if (Objects.equals(friendshipID, friendship.getId())){
                friendship.setFriendsFrom(newDate);
                emptyFile();
                writeAllDataToFile(repository);
                return true;
            }
        return false;
    }

    public Friendship readFriendship(User firstFriend, User secondFriend){
        List<Friendship> repository = readAll();
        int size = size();
        Friendship possibleFriendship = new Friendship(firstFriend.getId(), secondFriend.getId());
        for(int i = 0; i < size; i++){
            if(repository.get(i).equals(possibleFriendship))
                return repository.get(i);
        }
        return null;
    }
}

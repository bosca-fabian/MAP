package Repository;

import Domain.Friendship;
import Domain.User;
import Domain.Validators.FriendshipValidator;
import Domain.Validators.SuperValidator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Friendship class that holds the in memory repository; extends the SuperMemoryRepository
 */
public class Friendships extends SuperMemoryRepository<Friendship> {

    /**
     * constructor of the friendship class with validator of friendship data types as parameter
     * @param friendshipSuperValidator the validator
     */
    public Friendships(SuperValidator<Friendship> friendshipSuperValidator){
        super(friendshipSuperValidator);
    }

    public boolean update(UUID friendshipID, LocalDate newDate){
        for(Friendship friendship : repository)
            if (friendship.getId() == friendshipID){
                friendship.setFriendsFrom(newDate);
                return true;
            }
        return false;
    }


    /**
     * methods that returns from the repo a friendship
     * @param firstFriend first name of the friendship
     * @param secondFriend second name of the friendship
     * @return the needed friendship
     */
    public Friendship readFriendship(User firstFriend, User secondFriend){
        int size = size();
        Friendship possibleFriendship = new Friendship(firstFriend.getId(), secondFriend.getId());
        for(int i = 0; i < size; i++){
            if(this.repository.get(i).equals(possibleFriendship))
                return this.repository.get(i);
        }
        return null;
    }



}

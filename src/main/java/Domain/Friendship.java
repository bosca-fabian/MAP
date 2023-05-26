package Domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Friendship class that extends entity
 */
public class Friendship extends Entity {
    private UUID firstFriend;
    private UUID secondFriend;

//    private boolean status = false;

    private LocalDate friendsFrom;

    /**
     * Friendsship class constructor
     * @param firstFriend - first friends of the friendship
     * @param secondFriend - second friend of the friendship
     */
    public Friendship(UUID firstFriend, UUID secondFriend) {
        this.firstFriend = firstFriend;
        this.secondFriend = secondFriend;
        this.friendsFrom = LocalDate.now();
    }

    public Friendship(UUID givenID, UUID firstFriend, UUID secondFriend, LocalDate givenDateTime){
        super(givenID);
        this.firstFriend = firstFriend;
        this.secondFriend = secondFriend;
        this.friendsFrom = givenDateTime;
    }

    public Friendship(UUID firstFriend, UUID secondFriend, LocalDate givenDate){
        this.firstFriend = firstFriend;
        this.secondFriend= secondFriend;
        this.friendsFrom = givenDate;
    }

    public Friendship(UUID givenID, UUID firstFriend, UUID secondFriend){
        super(givenID);
        this.firstFriend = firstFriend;
        this.secondFriend = secondFriend;
    }



    /**
     * first friend getter
     * @return returns the first friend of a friendship
     */
    public UUID getFirstFriend() {
        return firstFriend;
    }

    /**
     * getter for the second friend
     * @return returs the second friend of a friendship
     */
    public UUID getSecondFriend() {
        return secondFriend;
    }

    public LocalDate getFriendsFrom() {
        return friendsFrom;
    }

    public void setFirstFriend(UUID firstFriend) {
        this.firstFriend = firstFriend;
    }

    public void setSecondFriend(UUID secondFriend) {
        this.secondFriend = secondFriend;
    }

    public void setFriendsFrom(LocalDate friendsFrom) {
        this.friendsFrom = friendsFrom;
    }

    /**
     * Overridden equals method that checks whether 2 friendships are the same
     * @param o the second friendship
     * @return retuns true if they are equal, false if they are not
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        boolean pos = Objects.equals(firstFriend, that.firstFriend) && Objects.equals(secondFriend, that.secondFriend);
        boolean spos = Objects.equals(firstFriend, that.secondFriend) && Objects.equals(secondFriend, that.firstFriend);
        return pos || spos;
    }

    /**
     * overridden hashcode method that returns the hashcode of a friendship entity
     * @return the hashcode of the friendship
     */
    @Override
    public int hashCode() {
        return Objects.hash(firstFriend, secondFriend);
    }




    @Override
    public String toString() {
        return "Friendship{" +
                "firstFriend=" + firstFriend +
                ", secondFriend=" + secondFriend +
                ", friendsFrom = " + friendsFrom +
                '}';
    }
}

package Domain.Validators;

import Domain.Friendship;

/**
 * validator class for the friendship entity
 */
public class FriendshipValidator extends SuperValidator<Friendship> {
    /**
     * validates a friendship
     * @param friendship the given friendship to be validated
     * @throws ValidationException the thrown exception
     */
    @Override
    public void validate(Friendship friendship) throws ValidationException{
        if(friendship.getFirstFriend().equals(friendship.getSecondFriend()))
            throw new ValidationException("You can't be friends with yourself");
    }
}

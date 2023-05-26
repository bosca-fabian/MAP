package Domain.Validators;

import Domain.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator extends SuperValidator<User> {
    /**
     * validator for the users
     * @param user user to be validated
     * @throws ValidationException the thrown exception
     */
    @Override
    public void validate(User user) throws ValidationException{
        Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
        Matcher userMatcher = pattern.matcher(user.getUsername());
        Matcher passwordMatcher = pattern.matcher(user.getPassword());
        boolean existSpecialCharUser = userMatcher.find();
        boolean existSpecialCharPass = passwordMatcher.find();
        if (existSpecialCharPass || existSpecialCharUser)
            throw new ValidationException("There are special characters in the username or the password");
    }
}

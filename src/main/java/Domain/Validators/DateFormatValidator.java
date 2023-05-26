package Domain.Validators;

public class DateFormatValidator implements Validator<String>{

    @Override
    public void validate(String entity) throws ValidationException {
        String[] attr = entity.split("/");
        if(attr.length != 3)
            throw new WrongDateFormatException("Wrong format");

    }
}

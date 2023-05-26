package Domain.Validators;

/**
 * Interface of the validator for any given datatype
 * @param <T> the datatype
 */
public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}

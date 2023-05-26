package Domain.Validators;

import Domain.Entity;

/**
 * Super validator of a given entity that throws validationexception
 * @param <Entity> the entity
 */
public abstract class SuperValidator<Entity> implements Validator<Entity> {
    /**
     * validates the given entity
     * @param entity the entity
     * @throws ValidationException the exception to be thrown
     */
    public abstract void validate(Entity entity) throws ValidationException;
}

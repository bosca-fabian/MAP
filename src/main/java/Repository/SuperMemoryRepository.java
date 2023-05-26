package Repository;

import Domain.Entity;
import Domain.Validators.EntityAlreadyExistsException;
import Domain.Validators.SuperValidator;
import Domain.Validators.UserValidator;
import Domain.Validators.Validator;

import java.util.*;

/**
 * generic repository that implements the Repository interface for any entity
 * @param <Entity> the entity to be worked with in the repository
 */
public class SuperMemoryRepository<Entity extends Domain.Entity> implements Repository<Entity> {

    List<Entity> repository = new ArrayList<>();

//    Map<UUID, Entity> repoMap = new HashMap<>();


    protected SuperValidator<Entity> validator;

    /**
     * constructor for the supermemoryreposiory(generic repository) that gets as a paramets a generic validator
     * @param validator the validator
     */
    public SuperMemoryRepository(SuperValidator<Entity> validator){
        this.validator = validator;
    }


    /**
     * overriden method that adds an entity to the repo
     * @param entity the entity to be added
     */
    @Override
    public void add(Entity entity) {
        this.validator.validate(entity);
        if (findByID(entity.getId()) != null) {
            this.repository.add(entity);
        }
    }

    /**
     * implemented method that deletes an entity from the repository
     * @param entity the entity to be deleted
     */
    @Override
    public void delete(Entity entity) {
        this.repository.remove(entity);
    }


    @Override
    public Entity readByID(UUID entityID) {
        for(Entity entity : repository)
            if(entity.getId() == entityID)
                return entity;
        return null;
    }

    /**
     * implemented method that returns the size of the repository
     * @return size of the repository
     */
    @Override
    public int size() {
        return repository.size();
    }

    /**
     * implemented method that returns a list of all entities of the repository
     * @return list of all entities
     */
    @Override
    public List<Entity> readAll() {
        return repository;
    }

    public Entity findByID(UUID givenID){
        for (Entity entity: repository) {
            if (entity.getId() == givenID)
                return entity;
        }
        return null;
    }

}
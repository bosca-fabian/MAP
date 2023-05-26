package Domain;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * generic class that will be used for any entity that will be created, serializable
 *
 */
public class Entity{
    private UUID id = UUID.randomUUID();

    protected Entity(){}

    protected Entity(UUID id){
        this.id = id;
    }

    /**
     * getter for id
     * @return returns the id of the entity
     */
    public UUID getId() {
        return id;
    }

    /**
     * setter for id
     * @param id - sets the id of the entity with the given one
     */
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                '}';
    }
}

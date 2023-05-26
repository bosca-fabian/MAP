package Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * Interface for the repository for any given datatype
 * @param <E> the given datatype
 */
public interface Repository<E> {
    void add(E entity) throws SQLException;
    void delete(E entity);


    E readByID(UUID entity);

    List<E> readAll();
    int size();


}

package repositories.interfaces;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    void save(Object entity);
    Optional<T> findById(Long id);
    void update(Object entity);
    void delete(Long id);
    List<?> findAll();
}

package repositories.interfaces;

public interface CrudRepository<T> {
    void save(Object entity);
//    Optional<T> findById(Long id);
//    void update(Object entity);
//    void delete(Long id);
//    List<?> findAll();
}

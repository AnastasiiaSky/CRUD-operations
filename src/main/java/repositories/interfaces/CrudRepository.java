/**
 * @autor Анастасия Гапонова
 * @version 1.0
 */
package repositories.interfaces;

import java.util.List;
import java.util.Optional;


/**
 * Интерфейс для операций CRUD (создание, чтение, обновление, удаление).
 *
 * @param <T> тип сущности, с которой работает репозиторий.
 */

public interface CrudRepository<T> {
    /**
     * Сохраняет сущность в базе данных.
     *
     * @param entity сущность для сохранения.
     */
    void save(Object entity);

    /**
     * Находит сущность по ее ID.
     *
     * @param id ID сущности.
     * @return сущность, если она найдена, или пустой Optional, если сущность не найдена.
     */
    Optional<T> findById(Long id);

    /**
     * Обновляет сущность в базе данных.
     *
     * @param entity обновленная сущность.
     */
    void update(Object entity);

    /**
     * Удаляет сущность из базы данных по ее ID.
     *
     * @param id ID сущности.
     */
    void delete(Long id);

    /**
     * Находит все сущности в базе данных.
     *
     * @return список всех сущностей.
     */
    List<?> findAll();
}

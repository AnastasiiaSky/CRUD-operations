package repositories.interfaces;

import models.Book;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для операций CRUD для молели Book (поиск по названию книги, поиск по имени автора).
 * Расширяет интерфейс CrudRepository
 */
public interface BookRepository extends CrudRepository<Book> {
    /**
     * Поиск книги в базе данных по ее названию.
     *
     * @param bookName название книги.
     * @return найденная книга.
     */
    Optional<Book> findByBookName(String bookName);

    /**
     * Поиск книг в базе данных автору.
     *
     * @param author атор книг.
     * @return найденные книги.
     */
    List<Book> findByAuthor(String author);
}

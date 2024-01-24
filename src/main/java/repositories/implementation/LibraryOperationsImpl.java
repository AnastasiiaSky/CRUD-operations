/**
 * @autor Анастасия Гапонова
 * @version 1.0
 */
package repositories.implementation;


import models.Book;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import repositories.interfaces.CrudRepository;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static application.Program.logger;

/**
 * Реализация интерфейса CrudRepository для работы с книгами в базе данных.
 * <p>
 * Предоставляет методы для создания, чтения, обновления и удаления книг.
 */
public class LibraryOperationsImpl implements CrudRepository<Book> {
    /**
     * шаблон JdbcTemplate.
     */
    private final JdbcTemplate jdbcTemplate;

    /**
     * Конструктор класса LibraryOperationsImpl.
     *
     * @param jdbcTemplate объект JdbcTemplate для выполнения SQL-запросов.
     */
    public LibraryOperationsImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        initTables();

    }

    /**
     * Удаляет книгу из базы данных по ее ID.
     *
     * @param id ID книги.
     * @see CrudRepository#delete(Long)
     */
    @Override
    public void delete(Long id) {
        String query = "DELETE FROM book WHERE id = ?";
        int result = this.jdbcTemplate.update(query, id);
        if (result == 1) {
            logger.info("The delete operation was executed");
        } else logger.error("The delete operation hasn't executed, this id = {} is incorrect", id);

    }

    /**
     * Находит все книги в базе данных.
     *
     * @return список всех книг.
     * @see CrudRepository#findAll()
     */
    @Override
    public List<?> findAll() {
        String query = "SELECT * FROM book";
        List<Book> books = jdbcTemplate.query(query, new RowMapper<Book>() {
            @Override
            public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
                Book entity = new Book();
                entity.setId(rs.getLong("id"));
                entity.setBookName(rs.getString("bookName"));
                entity.setAuthor(rs.getString("author"));
                return entity;
            }
        });
        logger.info("The list of entities was presented");
        return books;
    }


    /**
     * Обновляет информацию о книге в базе данных.
     *
     * @param entity обновленная информация о книге.
     * @see CrudRepository#update(Object)
     */
    @Override
    public void update(Object entity) {
        if (entity.getClass().equals(Book.class)) {
            Book currentBook = (Book) entity;
            String tableName = entity.getClass().getSimpleName();
            String query = String.format("UPDATE %s set %s WHERE id = ?",
                    tableName, getColumns(entity, " = ?, "));
            int result = this.jdbcTemplate.update(query, currentBook.getBookName(),
                    currentBook.getAuthor(), currentBook.getId());
            if (result == 1) {
                logger.info("The update operation was executed");
            } else logger.error("The update operation hasn't executed, id = {} is incorrect!", ((Book) entity).getId());
        }
    }


    /**
     * Находит книгу в базе данных по ее ID.
     *
     * @param id ID книги.
     * @return книгу, если она найдена, или пустой Optional, если книга не найдена.
     * @see CrudRepository#findById(Long)
     */
    @Override
    public Optional findById(Long id) {
        String query = "SELECT * FROM book WHERE id = ?";
        try {
            Book book = jdbcTemplate.queryForObject(query, new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
            logger.info("Found a book with id " + id);
            return Optional.ofNullable(book);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            logger.error("No book found with id = {}, id is incorrect" + id);
        }
        return Optional.empty();
    }


    /**
     * Сохраняет новую книгу в базе данных.
     *
     * @param entity новая книга.
     * @see CrudRepository#save(Object)
     */
    @Override
    public void save(Object entity) {
        if (entity.getClass().equals(Book.class)) {
            Book currentBook = (Book) entity;
            String tableName = entity.getClass().getSimpleName();
            String query = String.format("INSERT INTO %s (%s) VALUES(?, ?)", tableName, getColumns(entity, ", "));
            int result = this.jdbcTemplate.update(query, currentBook.getBookName(), currentBook.getAuthor());
            if (result == 1) {
                logger.info("The update operation was executed, object was added.");
                String idQuery = String.format("SELECT MAX(id) FROM %s", tableName);
                long id = this.jdbcTemplate.queryForObject(idQuery, Long.class);
                currentBook.setId(id);
            } else logger.error("The update operation hasn't executed");
        } else logger.error("The update operation hasn't executed");
    }


    /**
     * Получает столбцы для таблицы на основе полей указанного объекта.
     *
     * @param entity     объект.
     * @param stringPart строка, которая будет добавлена после каждого столбца.
     * @return строку, содержащую столбцы для таблицы.
     */
    private StringBuilder getColumns(Object entity, String stringPart) {
        StringBuilder columns = new StringBuilder();
        for (Field field : entity.getClass().getDeclaredFields()) {
            if (!field.getName().equals("id")) {
                columns.append(field.getName()).append(stringPart);
            }
        }
        columns.deleteCharAt(columns.lastIndexOf(", "));
        return columns;
    }

    /**
     * Инициализирует таблицы в базе данных.
     */
    private void initTables() {
        DataBaseInitializing initializer = new DataBaseInitializing(this.jdbcTemplate);
        initializer.createTables();
        logger.info("The table was created");
    }
}

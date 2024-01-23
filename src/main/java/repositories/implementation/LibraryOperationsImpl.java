package repositories.implementation;


import models.Book;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import repositories.interfaces.CrudRepository;
import java.lang.reflect.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LibraryOperationsImpl implements CrudRepository {
    private JdbcTemplate jdbcTemplate;
    private Logger logger = LoggerFactory.getLogger(LibraryOperationsImpl.class);

    public LibraryOperationsImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        initTables();

    }

    @Override
    public void save(Object entity) {
        if (entity.getClass().equals(Book.class)) {
            Field[] fields = entity.getClass().getDeclaredFields();

            int result = this.jdbcTemplate.update(
                    "INSERT INTO " + entity.getClass().getSimpleName() +
                            " (" + fields[1].getName() + ", " + fields[2].getName() + ") VALUES(?, ?)",
                    ((Book) entity).getBookName(), ((Book) entity).getAuthor());

            if (result == 1) {
                logger.info("The update operation was executed");
                long id = this.jdbcTemplate.queryForObject("SELECT MAX(id) FROM "
                        + entity.getClass().getSimpleName(), Long.class);
                ((Book) entity).setId(id);
            } else logger.error("The update operation hasn't executed");
        }
    }


    private void initTables() {
        DataBaseInitializing initializer = new DataBaseInitializing(this.jdbcTemplate);
        initializer.createTables();
        logger.info("The table was created");
    }
}

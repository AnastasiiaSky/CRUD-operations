/**
 * @autor Анастасия Гапонова
 * @version 1.0
 */
package repositories.implementation;

import configuration.ApplicationConfig;
import org.springframework.jdbc.core.JdbcTemplate;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * Класс для инициализации базы данных.
 *
 * Создает таблицы в базе данных на основе классов моделей в указанном пакете.
 */
public class DataBaseInitializing {
    private static final String PACKAGE = "models";
    private final JdbcTemplate jdbcTemplate;

    /**
     * Конструктор класса DataBaseInitializing.
     *
     * @param jdbcTemplate объект JdbcTemplate для выполнения SQL-запросов.
     * @see ApplicationConfig#createJdbcTemplate()
     */
    public DataBaseInitializing(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Создает таблицы в базе данных на основе классов моделей в указанном пакете.
     */
    protected void createTables() {
        Reflections reflections = new Reflections(PACKAGE, Scanners.SubTypes.filterResultsBy(s -> true));
        Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);
        if (!classes.isEmpty()) {
            for (Class<?> aClass : classes) {
                creatingCurrentTables(aClass);
            }
        }
    }

    /**
     * Создает таблицу для указанного класса модели.
     *
     * @param clazz класс модели.
     */
    private void creatingCurrentTables(Class<?> clazz) {
        String tableName = clazz.getSimpleName();
        this.jdbcTemplate.execute(String.format("DROP TABLE IF EXISTS %s  CASCADE", tableName));
        StringBuilder columns = getColumnsForTheTable(clazz);
        String query = String.format("CREATE TABLE %s (id bigserial primary key, %s)", tableName, columns);
        this.jdbcTemplate.execute(query);
    }

    /**
     * Получает столбцы для таблицы на основе полей указанного класса модели.
     *
     * @param clazz класс модели.
     * @return строку, содержащую столбцы для таблицы.
     */
    private StringBuilder getColumnsForTheTable(Class<?> clazz) {
        StringBuilder columns = new StringBuilder();
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.getName().equals("id")) {
                columns.append(field.getName()).append(" text not null, ");
            }
        }
        columns.deleteCharAt(columns.lastIndexOf(", "));
        return columns;
    }
}

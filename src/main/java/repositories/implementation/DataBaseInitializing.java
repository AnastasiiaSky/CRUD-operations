package repositories.implementation;

import org.springframework.jdbc.core.JdbcTemplate;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.lang.reflect.Field;
import java.util.Set;

public class DataBaseInitializing {
    private static final String PACKAGE = "models";
    private JdbcTemplate jdbcTemplate;

    public DataBaseInitializing(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected void createTables() {
        Reflections reflections = new Reflections(PACKAGE, Scanners.SubTypes.filterResultsBy(s -> true));
        Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);
        if (!classes.isEmpty()) {
            for (Class<?> aClass : classes) {
                creatingCurrentTables(aClass);
            }
        }
    }

    private void creatingCurrentTables(Class<?> clazz) {
        String tableName = clazz.getSimpleName();
        this.jdbcTemplate.execute(String.format("DROP TABLE IF EXISTS %s  CASCADE", tableName));
        StringBuilder columns = getColumnsForTheTable(clazz);
        String query = String.format("CREATE TABLE %s (id bigserial primary key, %s)", tableName, columns.toString());
        this.jdbcTemplate.execute(query);
    }

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

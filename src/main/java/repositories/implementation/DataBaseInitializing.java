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
        if(!classes.isEmpty()) {
            for (Class<?> aClass : classes) {
                creatingCurrentTables(aClass);
            }
        }
    }

    private void creatingCurrentTables(Class<?> clazz) {
        this.jdbcTemplate.execute(String.format("DROP TABLE IF EXISTS %s  CASCADE",
                clazz.getSimpleName()));
        Field[] fields = clazz.getDeclaredFields();
        this.jdbcTemplate.execute(String.format("CREATE TABLE %s (%s bigserial primary key," +
                "%s text not null, %s text not null)", clazz.getSimpleName(), fields[0].getName(),
                fields[1].getName(), fields[2].getName()));
    }
}

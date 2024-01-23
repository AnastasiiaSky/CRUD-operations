package application;


import configuration.ApplicationConfig;
import models.Book;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import repositories.implementation.LibraryOperationsImpl;


public class Program {
    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(ApplicationConfig.class);
        JdbcTemplate jdbcTemplate = context.getBean("jdbcTemplate", JdbcTemplate.class);
        LibraryOperationsImpl operations = new LibraryOperationsImpl(jdbcTemplate);
        operations.save(new Book("Преступление и наказание", "Ф.М. Достоевский"));
        operations.save(new Book("Война и мир", "Л.Н Толстой"));
        operations.save(new Book("Руслан и Людмила", "А.С Пушкин"));
        operations.save(new Book("Финансист", "Т. Драйзер"));
        operations.save(new Book("Идиот", "Ф.М. Достоевский"));

    }
}

/**
 * @autor Анастасия Гапонова
 * @version 1.0
 */
package application;

import configuration.ApplicationConfig;
import models.Book;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import repositories.implementation.LibraryOperationsImpl;

import java.util.List;
import java.util.Optional;

/**
 * Класс Program представляет собой основной класс приложения, который используется для демонстрации работы библиотеки операций.
 * В этом классе создается контекст приложения, получаются библиотечные операции и выполняются различные операции над книгами.
 */
public class Program {
    /**
     * Главный метод приложения.
     * Создает контекст приложения, получает библиотечные операции и выполняет различные операции над книгами.
     * @param args аргументы командной строки (не используются в этом примере).
     */
    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(ApplicationConfig.class);
        LibraryOperationsImpl operations = context.getBean("libraryOperationsImpl", LibraryOperationsImpl.class);


        operations.save(new Book("Преступление и наказание", "Ф.М. Достоевский"));
        operations.save(new Book("Война и мир", "Л.Н Толстой"));
        operations.save(new Book("Руслан и Людмила", "А.С Пушкин"));
        operations.save(new Book("Финансист", "Т. Драйзер"));
        operations.save(new Book("Идиот", "Ф.М. Достоевский"));

        Optional<Object> book = operations.findById(3L);
        Optional<Object> book1 = operations.findById(510L);
        System.out.println("book from main " + book1.toString());

        operations.update(new Book(1L, "Преступление за наказание", "Ф.М. Достоевский"));
        operations.update(new Book(-1L, "Преступление за наказание", "Ф.М. Достоевский"));

        operations.delete(3L);
        operations.delete(25L);

        List<?> books = operations.findAll();

        for(Object element : books) {
            System.out.println(element.toString());
        }

    }
}

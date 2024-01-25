package application;

import configuration.ApplicationConfig;
import models.Book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
     * Создание объекта логирования
     */
    public static final Logger logger = LoggerFactory.getLogger(LibraryOperationsImpl.class);

    /**
     * Главный метод приложения.
     * Создает контекст приложения, получает библиотечные операции и выполняет различные операции над книгами.
     *
     * @param args аргументы командной строки (не используются в этом примере).
     */
    public static void main(String[] args) {
        logger.info("The application started working!");
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(ApplicationConfig.class)) {
            LibraryOperationsImpl operations = context.getBean("libraryOperationsImpl", LibraryOperationsImpl.class);
            logger.info("LibraryOperations bean was created");

            addingDataTest(operations);
            findByIdTest(operations);
            updateTest(operations);
            deleteTest(operations);
            findAllTest(operations);
            findByBookName(operations);
            findByBookAuthor(operations);
        } catch (Exception e) {
            logger.error("Data Base connection has not created!");
        }

        logger.info("The application end working!");
    }


    /**
     * Метод для тестирования поиска книги по названию книги.
     *
     * @param operations Объект класса LibraryOperationsImpl.
     */
    private static void findByBookName(LibraryOperationsImpl operations) {
        Optional<Book> book = operations.findByBookName("Война и мир");
        logger.info(book.toString());
        Optional<Book> book1 = operations.findByBookName("Руслан и Людмила");
        logger.info(book1.toString());

    }

    /**
     * Метод для тестирования поиска книги по имени автора.
     *
     * @param operations Объект класса LibraryOperationsImpl.
     */
    private static void findByBookAuthor(LibraryOperationsImpl operations) {
        List<Book> book = operations.findByAuthor("Ф.М. Достоевский");
        logger.info(book.toString());
        List<Book> book1 = operations.findByAuthor("И.И. Иванов");
        logger.info(book1.toString());
    }

    /**
     * Метод для добавления тестовых данных.
     *
     * @param operations Объект класса LibraryOperationsImpl.
     */
    private static void addingDataTest(LibraryOperationsImpl operations) {
        operations.save(new Book("Преступление и наказание", "Ф.М. Достоевский"));
        operations.save(new Book("Война и мир", "Л.Н Толстой"));
        operations.save(new Book("Руслан и Людмила", "А.С Пушкин"));
        operations.save(new Book("Финансист", "Т. Драйзер"));
        operations.save(new Book("Идиот", "Ф.М. Достоевский"));
        operations.save(new Book("Азазель", "Б. Акунин"));
    }

    /**
     * Метод для тестирования поиска книги по идентификатору.
     *
     * @param operations Объект класса LibraryOperationsImpl.
     */
    private static void findByIdTest(LibraryOperationsImpl operations) {
        Optional<Object> book = operations.findById(3L);
        logger.info(book.toString());
        Optional<Object> book1 = operations.findById(510L);
        logger.info(book1.toString());
        Optional<Object> book2 = operations.findById(-1L);
        logger.info(book2.toString());
    }

    /**
     * Метод для тестирования обновления книги.
     *
     * @param operations Объект класса LibraryOperationsImpl.
     */
    private static void updateTest(LibraryOperationsImpl operations) {
        operations.update(new Book(1L, "Преступление за наказание", "Ф.М. Достоевский"));
        operations.update(new Book(-1L, "Преступление за наказание", "Ф.М. Достоевский"));
        operations.update(new Book(-1L, "Преступление и наказание", "FM Достоевский"));
    }

    /**
     * Метод для тестирования удаления книги.
     *
     * @param operations Объект класса LibraryOperationsImpl.
     */

    private static void deleteTest(LibraryOperationsImpl operations) {
        operations.delete(-1L);
        operations.delete(3L);
        operations.delete(25L);
    }

    /**
     * Метод для тестирования получения списка всех книг.
     *
     * @param operations Объект класса LibraryOperationsImpl.
     */
    private static void findAllTest(LibraryOperationsImpl operations) {
        List<?> books = operations.findAll();
        logger.info(books.toString());
    }
}

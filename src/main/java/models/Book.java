/**
 * @autor Анастасия Гапонова
 * @version 1.0
 */
package models;

import java.util.StringJoiner;

/**
 * Класс модель - Book с полями <b>id</b>, <b>bookName</b> и <b>author</b>.
 */
public class Book {
    private Long id;
    private String bookName;
    private String author;


    /** Дефолтный конструктор класса Book. */
    public Book() {
    }

    /**
     * Конструктор класса Book.
     *
     * @param bookName название книги
     * @param author автор книги
     */
    public Book(String bookName, String author) {
        this.bookName = bookName;
        this.author = author;
    }

    /**
     * Конструктор класса Book с указанием ID.
     *
     * @param id уникальный идентификатор книги
     * @param bookName название книги
     * @param author автор книги
     */
    public Book(Long id, String bookName, String author) {
        this.id = id;
        this.bookName = bookName;
        this.author = author;
    }

    /**
     * Getter для поля id
     * @return текущее значение id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter для поля id.
     * @param id Новое значение для id.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter для поля bookName.
     * @return текущее значение поля bookName.
     */
    public String getBookName() {
        return bookName;
    }


    /**
     * Setter  для поля bookName.
     * @param bookName Новое значение для bookName.
     */
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }


    /**
     * Getter для поля  author.
     * @return Tекущее значение поля author.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Setter для поля author.
     * @param author Новое значение для author.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Book.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("bookName='" + bookName + "'")
                .add("author='" + author + "'")
                .toString();
    }
}

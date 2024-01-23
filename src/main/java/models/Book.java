package models;

import java.util.StringJoiner;

public class Book {
    private Long id;
    private String bookName;
    private String author;


    public Book(String bookName, String author) {
        this.bookName = bookName;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

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

package org.evgenii.project.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

public class Book {
    private int book_id;
    @NotEmpty(message = "Название книги не может быть пустым")
    private String name;
    @NotEmpty(message = "Имя автора не может быть пустым")
    private String author;
    @Max(value = 2022, message = "Год выпуска книги не может более 2022 года")
    private int year;

    public Book(){
    }

    public Book(int book_id, String name, String author, int year) {
        this.book_id = book_id;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}

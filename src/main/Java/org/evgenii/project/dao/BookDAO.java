package org.evgenii.project.dao;

import org.evgenii.project.models.Book;
import org.evgenii.project.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index(){
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int book_id){
        return  jdbcTemplate.query("SELECT * FROM Book WHERE book_id = ?", new BeanPropertyRowMapper<>(Book.class), book_id)
                .stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(name, author, year) VALUES (?, ?, ?)", book.getName(), book.getAuthor(), book.getYear());
    }

    public void update(int book_id, Book book) {
        jdbcTemplate.update("UPDATE Book SET name = ?, author = ?, year = ? WHERE book_id = ?", book.getName(), book.getAuthor(), book.getYear(), book_id);
    }

    public void delete(int book_id) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id = ?", book_id);
    }

    public List<Book> usersBooks(int person_id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id = ?", new BeanPropertyRowMapper<>(Book.class), person_id);
    }

    public Person userBook(int book_id) {
        return jdbcTemplate.query("SELECT full_name FROM Book JOIN Person ON book.person_id = person.person_id WHERE book_id = ?", new BeanPropertyRowMapper<>(Person.class), book_id)
                .stream().findAny().orElse(null);
    }

    public void bookFree(int book_id) {
        jdbcTemplate.update("UPDATE Book SET person_id = null WHERE book_id = ?", book_id);
    }

    public void assignBook(Person person, int book_id) {
        jdbcTemplate.update("UPDATE Book SET person_id = ? WHERE book_id = ?", person.getPerson_id(), book_id);
    }
}

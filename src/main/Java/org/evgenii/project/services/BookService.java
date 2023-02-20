package org.evgenii.project.services;

import org.evgenii.project.models.Book;
import org.evgenii.project.models.Person;
import org.evgenii.project.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> index() {
        return bookRepository.findAll();
    }

    public List<Book> index(int page, int booksPerPage){
        Pageable pageNumberAndCountBooks = PageRequest.of(page, booksPerPage);
        return bookRepository.findAll(pageNumberAndCountBooks).toList();
    }

    public Book show(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updateBook) {
        Book book = show(id);
        book.setName(updateBook.getName());
        book.setAuthor(updateBook.getAuthor());
        book.setYear(updateBook.getYear());

        bookRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void releaseBook(int id) {
        Book book = show(id);
        book.setOwner(null);
        bookRepository.save(book);
    }

    @Transactional
    public void assignBook(Person person, int id) {
        Book book = show(id);
        book.setOwner(person);
        bookRepository.save(book);
    }

    public Person usersBook(int id) {
        return show(id).getOwner();
    }
}

package org.evgenii.project.services;

import org.evgenii.project.models.Book;
import org.evgenii.project.models.Person;
import org.evgenii.project.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> index(boolean sortByYear) {
        return sortByYear ? bookRepository.findAll(Sort.by("year")) : bookRepository.findAll();

    }

    public List<Book> findWithPagination(int page, int booksPerPage, boolean sortByYear){
        if(sortByYear) {
            return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        } else {
            return bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
        }
    }

    public Book show(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        Book bookToBeUpdated = bookRepository.findById(id).get();
        updatedBook.setBook_id(id);
        updatedBook.setOwner(bookToBeUpdated.getOwner());
        updatedBook.setBookTimer(bookToBeUpdated.getBookTimer());

        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void releaseBook(int id) {
        bookRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(null);
                    book.setBookTimer(null);
                }
        );
    }

    @Transactional
    public void assignBook(Person person, int id) {
        bookRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(person);
                    book.setBookTimer(LocalDateTime.now());
        });
    }

    public Person usersBook(int id) {
        return bookRepository.findById(id).map(Book::getOwner).orElse(null);
    }

    public List<Book> searchBook(String searchQuery) {
        return bookRepository.findByNameStartingWith(searchQuery.substring(0,1).toUpperCase() + searchQuery.substring(1));
    }
}

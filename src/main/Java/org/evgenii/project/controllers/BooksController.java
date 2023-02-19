package org.evgenii.project.controllers;

import org.evgenii.project.dao.BookDAO;
import org.evgenii.project.dao.PersonDAO;
import org.evgenii.project.models.Book;
import org.evgenii.project.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());

        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int book_id, Model model,
                       @ModelAttribute("nullPerson") Person person) {
        model.addAttribute("book", bookDAO.show(book_id));
        model.addAttribute("person", bookDAO.userBook(book_id));
        model.addAttribute("people", personDAO.index());

        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book")Book book){
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "books/new";
        }
        bookDAO.save(book);

        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int book_id,
                       Model model) {
        model.addAttribute("book", bookDAO.show(book_id));

        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id")  int book_id,
                         @ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "books/edit";
        }
        bookDAO.update(book_id, book);
        return "redirect:/books";
    }

    @PatchMapping("/free/{id}")
    public String bookFree(@PathVariable("id")  int book_id) {
        bookDAO.bookFree(book_id);
        return "redirect:/books/" + book_id;
    }

    @PatchMapping("assignBook/{id}")
    public String assignBook(@PathVariable("id") int book_id,
                             @ModelAttribute("person") Person person) {

        bookDAO.assignBook(person, book_id);

        return "redirect:/books/" + book_id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int book_id) {
        bookDAO.delete(book_id);

        return "redirect:/books";
    }
}

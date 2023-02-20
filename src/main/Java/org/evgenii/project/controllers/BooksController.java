package org.evgenii.project.controllers;

import org.evgenii.project.models.Book;
import org.evgenii.project.models.Person;
import org.evgenii.project.services.BookService;
import org.evgenii.project.services.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookService bookService;
    private final PersonService personService;

    public BooksController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
    }

    @GetMapping
    public String index(Model model,
                        @RequestParam(value = "page", required = false) int page,
                        @RequestParam(value = "books_per_page", required = false) int booksPerPage) {
        if (page >=0 && booksPerPage >= 0) {
            model.addAttribute("books", bookService.index(page, booksPerPage));
        } else {
            model.addAttribute("books", bookService.index());
        }

        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int book_id, Model model,
                       @ModelAttribute("nullPerson") Person person) {
        model.addAttribute("book", bookService.show(book_id));
        model.addAttribute("person", bookService.usersBook(book_id));
        model.addAttribute("people", personService.index());

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
        bookService.save(book);

        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int book_id,
                       Model model) {
        model.addAttribute("book", bookService.show(book_id));

        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id")  int book_id,
                         @ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "books/edit";
        }
        bookService.update(book_id, book);
        return "redirect:/books";
    }

    @PatchMapping("/free/{id}")
    public String bookFree(@PathVariable("id")  int book_id) {
        bookService.releaseBook(book_id);
        return "redirect:/books/" + book_id;
    }

    @PatchMapping("assignBook/{id}")
    public String assignBook(@PathVariable("id") int book_id,
                             @ModelAttribute("person") Person person) {

        bookService.assignBook(person, book_id);

        return "redirect:/books/" + book_id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int book_id) {
        bookService.delete(book_id);

        return "redirect:/books";
    }
}

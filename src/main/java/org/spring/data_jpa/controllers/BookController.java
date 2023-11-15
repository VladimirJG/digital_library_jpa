package org.spring.data_jpa.controllers;

import jakarta.validation.Valid;
import org.spring.data_jpa.models.Book;
import org.spring.data_jpa.models.Reader;
import org.spring.data_jpa.services.BookService;
import org.spring.data_jpa.services.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final ReaderService readerService;

    @Autowired
    public BookController(BookService bookService, ReaderService readerService) {
        this.bookService = bookService;
        this.readerService = readerService;
    }

    @GetMapping
    public String showAllBooks(Model model) {
        model.addAttribute("allBooks", bookService.findAllBooks());
        return "books/allBooks";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model, @ModelAttribute("reader") Reader reader) {
        model.addAttribute("book", bookService.findBookById(id));
        Reader bookOwnerById = bookService.getBookOwnerById(id);
        if (bookOwnerById != null) {
            model.addAttribute("bookOwner", bookOwnerById);
        } else {
            model.addAttribute("allReaders", readerService.findAllReaders());
        }
        return "books/oneBook";
    }

    @GetMapping("/newBook")
    public String newBook(@ModelAttribute("newBook") Book newBook) {
        return "books/newBook";
    }

    @PostMapping
    public String createBook(@ModelAttribute("createNewBook") @Valid Book newBook, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/newBook";
        }
        bookService.saveBook(newBook);
        return "redirect:/books";
    }

    @GetMapping("/{id}/editBook")
    public String editBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("editBook", bookService.findBookById(id));
        return "books/editBook";
    }

    @PatchMapping("/{id}")
    public String updateBook(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/editBook";
        }
        bookService.updateBook(book, id);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/freeTheBook")
    public String freeBook(@PathVariable("id") int id) {
        bookService.freeTheBook(id);
        return "redirect:/books" + id;
    }

    @PatchMapping("/{id}/assignBook")
    public String assign(@PathVariable("id") int id, @ModelAttribute("reader") Reader selectedReader) {
        bookService.assignBook(id, selectedReader);
        return "redirect:/books" + id;
    }
}







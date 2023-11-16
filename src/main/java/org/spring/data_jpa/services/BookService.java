package org.spring.data_jpa.services;

import org.spring.data_jpa.models.Book;
import org.spring.data_jpa.models.Reader;
import org.spring.data_jpa.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAllBooks(boolean sortByYear) {
        if (sortByYear)
            return bookRepository.findAll(Sort.by("yearReleased"));
        else
            return bookRepository.findAll();
    }

    public List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sortByYear) {
        if (sortByYear)
            return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("yearReleased"))).getContent();
        else
            return bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public Book findBookById(int id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.orElse(null);
    }

    public List<Book> searchByBookName(String query) {
        return bookRepository.findByBookNameStartingWith(query);
    }

    @Transactional
    public void updateBook(Book updateBook, int id) {
        Book bookToBeUpdated = bookRepository.findById(id).get();
        updateBook.setId(id);
        updateBook.setOwner(bookToBeUpdated.getOwner());
        bookRepository.save(updateBook);
    }

    @Transactional
    public void saveBook(Book newBook) {
        newBook.setTakenAt(new Date());
        bookRepository.save(newBook);
    }

    @Transactional
    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }

    public Reader getBookOwnerById(int id) {
        return bookRepository.findById(id).map(Book::getOwner).orElse(null);
    }

    @Transactional
    public void freeTheBook(int id) {
        bookRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(null);
                    book.setTakenAt(null);
                });
    }

    @Transactional
    public void assignBook(int id, Reader newOwner) {
        bookRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(newOwner);
                    book.setTakenAt(new Date());
                }
        );
    }
}

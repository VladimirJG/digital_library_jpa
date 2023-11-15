package org.spring.data_jpa.services;

import org.spring.data_jpa.models.Book;
import org.spring.data_jpa.models.Reader;
import org.spring.data_jpa.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book findBookById(int id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.orElse(null);
    }

    @Transactional
    public void updateBook(Book updateBook, int id) {
        updateBook.setId(id);
        bookRepository.save(updateBook);
    }

    @Transactional
    public void saveBook(Book newBook) {
        bookRepository.save(newBook);
    }

    @Transactional
    public void deleteBook(Book book) {
        bookRepository.delete(book);
    }

    public Reader getBookOwnerById(int id) {
        return findBookById(id).getOwner();
    }

    @Transactional
    public void freeTheBook(int id) {
        findBookById(id).setOwner(null);
    }

    @Transactional
    public void assignBook(int id, Reader newOwner) {
        findBookById(id).setOwner(newOwner);
    }
}

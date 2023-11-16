package org.spring.data_jpa.repositories;

import org.spring.data_jpa.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByBookNameStartingWith(String bookName);
}

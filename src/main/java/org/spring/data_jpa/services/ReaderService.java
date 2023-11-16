package org.spring.data_jpa.services;

import org.hibernate.Hibernate;
import org.spring.data_jpa.models.Book;
import org.spring.data_jpa.models.Reader;
import org.spring.data_jpa.repositories.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ReaderService {
    private final ReaderRepository readerRepository;

    @Autowired
    public ReaderService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    public List<Reader> findAllReaders() {
        return readerRepository.findAll();
    }

    public Reader findReaderById(int id) {
        Optional<Reader> reader = readerRepository.findById(id);
        return reader.orElse(null);
    }

    @Transactional
    public void updateReader(Reader updateReader, int id) {
        updateReader.setId(id);
        readerRepository.save(updateReader);
    }

    @Transactional
    public void saveReader(Reader newReader) {
        readerRepository.save(newReader);
    }

    @Transactional
    public void deleteReader(int id) {
        readerRepository.deleteById(id);
    }

    public List<Book> getBooksByReaderId(int id) {
        Optional<Reader> reader = readerRepository.findById(id);
        if (reader.isPresent()){
            Hibernate.initialize(reader.get().getBookList());
            reader.get().getBookList().forEach(book -> {
                long diffInMillies = Math.abs(book.getTakenAt().getTime() - new Date().getTime());
                if (diffInMillies>864000000)
                    book.setExpired(true);
            });
            return reader.get().getBookList();
        }
        else {
            return Collections.emptyList();
        }
    }

    public Optional<Reader> findByName(String name) {
        return readerRepository.findByName(name);
    }
}

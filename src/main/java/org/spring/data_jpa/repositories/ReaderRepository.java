package org.spring.data_jpa.repositories;

import org.spring.data_jpa.models.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Integer> {
    Optional<Reader> findByName(String name);
}

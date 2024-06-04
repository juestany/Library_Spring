package com.example.library.infrastructure.repository;

import com.example.library.infrastructure.entity.BookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, Long> {
    Optional<BookEntity> findBookById(long id);
}

package com.example.library.service;

import com.example.library.infrastructure.entity.BookEntity;
import com.example.library.infrastructure.repository.BookRepository;
import com.example.library.service.exception.BookAlreadyExistsException;
import com.example.library.service.exception.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for handling operations related to books.
 */
@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Retrieves all books.
     *
     * @return List of all books.
     */
    public List<BookEntity> getAll() {
        return (List<BookEntity>) bookRepository.findAll();
    }

    /**
     * Retrieves a book by its ID.
     *
     * @param id The ID of the book to retrieve.
     * @return The book entity.
     * @throws BookNotFoundException if the book with the specified ID is not found.
     */
    public BookEntity getOne(long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(String.format("Book with ID '%s' not found.", id)));
    }

    /**
     * Creates a new book.
     *
     * @param book The book to create.
     * @return The created book entity.
     * @throws BookAlreadyExistsException if a book with the same ID already exists.
     */
    public BookEntity create(BookEntity book) {
        long id = book.getId();
        Optional<BookEntity> existingBook = bookRepository.findById(id);
        if (existingBook.isPresent()) {
            throw new BookAlreadyExistsException(String.format("Book with ID '%s' already exists.", id));
        } else {
            return bookRepository.save(book);
        }
    }

    /**
     * Deletes a book by its ID.
     *
     * @param id The ID of the book to delete.
     * @throws BookNotFoundException if the book with the specified ID is not found.
     */
    public void delete(long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(String.format("Book with ID '%s' not found.", id));
        }
        bookRepository.deleteById(id);
    }
}

package com.example.library.controller;

import com.example.library.infrastructure.entity.BookEntity;
import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing book-related operations.
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Retrieves all books from the database.
     * @return List of all books.
     */
    @GetMapping
    public List<BookEntity> getAllBooks() {
        return bookService.getAll();
    }

    /**
     * Retrieves a single book by its ID.
     * @param id The ID of the book to retrieve.
     * @return The book with the specified ID.
     */
    @GetMapping("/{id}")
    public BookEntity getOne(@PathVariable long id) {
        return bookService.getOne(id);
    }

    /**
     * Creates a new book.
     * @param book The book entity to create.
     * @return The newly created book.
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public BookEntity create(@RequestBody BookEntity book){
        return bookService.create(book);
    }

    /**
     * Deletes a book by its ID.
     * @param id The ID of the book to delete.
     * @return ResponseEntity with a status of 204 if successful.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

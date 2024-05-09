package com.example.library.controller;

import com.example.library.infrastructure.entity.LoanEntity;
import com.example.library.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling Loan related endpoints.
 */
@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    /**
     * Get all loans.
     * @return List of LoanEntity objects.
     */
    @GetMapping
    public List<LoanEntity> getAllLoans() {
        return loanService.getAll();
    }

    /**
     * Get a single loan by its ID.
     * @param id The ID of the loan to retrieve.
     * @return The LoanEntity object with the specified ID.
     */
    @GetMapping("/{id}")
    public LoanEntity getOne(@PathVariable long id) {
        return loanService.getOne(id);
    }

    /**
     * Create a new loan.
     * @param loan The loan to create.
     * @return ResponseEntity indicating success or failure of the operation.
     */
    @PostMapping
    public ResponseEntity<LoanEntity> create(@RequestBody LoanEntity loan) {
        var newLoan = loanService.create(loan);
        return ResponseEntity.noContent().build();
    }

    /**
     * Delete a loan by its ID.
     * @param id The ID of the loan to delete.
     * @return ResponseEntity indicating success or failure of the operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        loanService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

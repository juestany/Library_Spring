package com.example.library.controller;

import com.example.library.infrastructure.dtos.LoanDTO;
import com.example.library.infrastructure.entity.BookEntity;
import com.example.library.infrastructure.entity.LoanEntity;
import com.example.library.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<LoanDTO>> getAllLoans() {
        List<LoanDTO> loans = loanService.getAll();
        return ResponseEntity.ok(loans);
    }

    /**
     * Get a single loan by its ID.
     * @param id The ID of the loan to retrieve.
     * @return The LoanEntity object with the specified ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LoanDTO> getOne(@PathVariable long id) {
        LoanDTO loanDTO = loanService.getOne(id);
        return ResponseEntity.ok(loanDTO);
    }

    /**
     * Create a new loan.
     * @param loanDTO The loan to create.
     * @return ResponseEntity indicating success or failure of the operation.
     */
    @PostMapping
    public ResponseEntity<LoanDTO> createLoan(@RequestBody LoanDTO loanDTO) {
        LoanDTO createdLoan = loanService.create(loanDTO);
        return ResponseEntity.ok(createdLoan);
    }

    /**
     * Delete a loan by its ID.
     * @param id The ID of the loan to delete.
     * @return ResponseEntity indicating success or failure of the operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable long id) {
        loanService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

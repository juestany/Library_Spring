package com.example.library.service;

import com.example.library.infrastructure.entity.LoanEntity;
import com.example.library.infrastructure.repository.LoanRepository;
import com.example.library.service.exception.LoanAlreadyExistsException;
import com.example.library.service.exception.LoanNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for handling operations related to loans.
 */
@Service
public class LoanService {

    private final LoanRepository loanRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    /**
     * Retrieves all loans.
     *
     * @return List of all loans.
     */
    public List<LoanEntity> getAll() {
        return loanRepository.findAll();
    }

    /**
     * Retrieves a loan by its ID.
     *
     * @param id The ID of the loan to retrieve.
     * @return The loan entity.
     * @throws LoanNotFoundException if the loan with the specified ID is not found.
     */
    public LoanEntity getOne(long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException(String.format("Loan with ID '%s' not found.", id)));
    }

    /**
     * Creates a new loan.
     *
     * @param loan The loan to create.
     * @return The created loan entity.
     * @throws LoanAlreadyExistsException if a loan with the same ID already exists.
     */
    public LoanEntity create(LoanEntity loan) {
        long id = loan.getId();
        Optional<LoanEntity> existingLoan = loanRepository.findById(id);
        if (existingLoan.isPresent()) {
            throw new LoanAlreadyExistsException(String.format("Loan with ID '%s' already exists.", id));
        } else {
            return loanRepository.save(loan);
        }
    }

    /**
     * Deletes a loan by its ID.
     *
     * @param id The ID of the loan to delete.
     * @throws RuntimeException if the loan with the specified ID is not found.
     */
    public void delete(long id) {
        if (!loanRepository.existsById(id)) {
            throw new RuntimeException();
        }
        loanRepository.deleteById(id);
    }
}

package com.example.library.service;

import com.example.library.infrastructure.dtos.LoanDTO;
import com.example.library.infrastructure.entity.BookEntity;
import com.example.library.infrastructure.entity.LoanEntity;
import com.example.library.infrastructure.entity.UserEntity;
import com.example.library.infrastructure.repository.BookRepository;
import com.example.library.infrastructure.repository.LoanRepository;
import com.example.library.infrastructure.repository.UserRepository;
import com.example.library.service.exception.LoanAlreadyExistsException;
import com.example.library.service.exception.LoanNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for handling operations related to loans.
 */
@Service
public class LoanService {

    private final LoanRepository loanRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    /**
     * Retrieves all loans.
     *
     * @return List of all loans.
     */
    public List<LoanDTO> getAll() {
        List<LoanEntity> loanEntities = loanRepository.findAll();
        return loanEntities.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private LoanDTO convertToDTO(LoanEntity loanEntity) {
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setId(loanEntity.getId());
        loanDTO.setBookId(loanEntity.getBookId());
        loanDTO.setUserId(loanEntity.getUserId());
        loanDTO.setLoanDate(loanEntity.getLoanDate());
        loanDTO.setLoanPeriod(loanEntity.getLoanPeriod());
        loanDTO.setReturnDate(loanEntity.getReturnDate());
        return loanDTO;
    }

    /**
     * Retrieves a loan by its ID.
     *
     * @param id The ID of the loan to retrieve.
     * @return The loan entity.
     * @throws LoanNotFoundException if the loan with the specified ID is not found.
     */
    public LoanDTO getOne(long id) {
        LoanEntity loanEntity = loanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Loan not found"));
        return convertToDTO(loanEntity);
    }

    /**
     * Creates a new loan.
     *
     * @param loanDTO The loan to create.
     * @return The created loan entity.
     * @throws LoanAlreadyExistsException if a loan with the same ID already exists.
     */
    public LoanDTO create(LoanDTO loanDTO) {
        if (loanRepository.existsById(loanDTO.getId())) {
            throw new LoanAlreadyExistsException(String.format("Loan with ID '%s' already exists.", loanDTO.getId()));
        } else {
            LoanEntity loanEntity = convertToEntity(loanDTO);
            LoanEntity savedEntity = loanRepository.save(loanEntity);
            return convertToDTO(savedEntity);
        }
    }

    private LoanEntity convertToEntity(LoanDTO loanDTO) {
        LoanEntity loanEntity = new LoanEntity();
        loanEntity.setId(loanDTO.getId());

        // Fetch and set the related book and user entities
        BookEntity bookEntity = bookRepository.findById(loanDTO.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
        UserEntity userEntity = userRepository.findById(loanDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        loanEntity.setBookId(bookEntity);
        loanEntity.setUserId(userEntity);
        loanEntity.setLoanDate(loanDTO.getLoanDate());
        loanEntity.setLoanPeriod(loanDTO.getLoanPeriod());
        loanEntity.setReturnDate(loanDTO.getReturnDate());

        return loanEntity;
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

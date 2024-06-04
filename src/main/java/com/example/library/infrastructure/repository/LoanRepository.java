package com.example.library.infrastructure.repository;

import com.example.library.infrastructure.entity.LoanEntity;
import com.example.library.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, Long> {
    Optional<UserEntity> findLoanById(long id);
}

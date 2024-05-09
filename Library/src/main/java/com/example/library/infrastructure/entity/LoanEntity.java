package com.example.library.infrastructure.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="loans", schema = "library")
public class LoanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "bookId")
    private BookEntity bookId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userId;

    @Basic
    @Column(name="loanDate")
    private LocalDate loanDate;

    @Basic
    @Column(name = "loanPeriod")
    private LocalDate loanPeriod;

    @Basic
    @Column(name = "returnDate")
    private LocalDate returnDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BookEntity getBookId() {
        return bookId;
    }

    public void setBookId(BookEntity bookId) {
        this.bookId = bookId;
    }

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(LocalDate loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}

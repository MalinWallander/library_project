package com.library.model.administration;

import java.time.LocalDate;
import java.util.UUID;

public class Loan {

    private UUID loanId;
    private String copyId;
    private String userId;
    private LocalDate loanDate;
    private LocalDate returnDate;

    public Loan(UUID loanId, String copyId, String userId, LocalDate loanDate, LocalDate returnDate) {
        this.loanId = loanId;
        this.copyId = copyId;
        this.userId = userId;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    public UUID getLoanId() {
        return loanId;
    }

    public String getCopyId() {
        return copyId;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setLoanId(UUID loanId) {
        this.loanId = loanId;
    }

    public void setCopyId(String copyId) {
        this.copyId = copyId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

}
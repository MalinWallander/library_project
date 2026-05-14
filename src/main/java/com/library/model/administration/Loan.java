package com.library.model.administration;

import java.time.LocalDate;

public class Loan {

    private String loanId;
    private String copyId;
    private String userId;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private LocalDate dueDate;

    public Loan(String loanId, String copyId, String userId, LocalDate borrowDate, LocalDate dueDate,
            LocalDate returnDate) {
        this.loanId = loanId;
        this.copyId = copyId;
        this.userId = userId;
        this.loanDate = borrowDate;
        this.returnDate = returnDate;
        this.dueDate = dueDate;
    }

    public String getLoanId() {
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
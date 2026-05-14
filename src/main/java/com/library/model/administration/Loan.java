package com.library.model.administration;

import java.time.LocalDate;
import java.util.UUID;

public class Loan {

    private UUID loanId;
    private String copyId;
    private String userId;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private LocalDate dueDate;

    public Loan(UUID loanId, String copyId, String userId, LocalDate borrowDate, LocalDate dueDate,LocalDate returnDate) {
        this.loanId = loanId;
        this.copyId = copyId;
        this.userId = userId;
        this.loanDate = borrowDate;
        this.returnDate = returnDate;
        this.dueDate = dueDate;
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

    // TODO: Never used
    public void setLoanId(UUID loanId) {
        this.loanId = loanId;
    }

    // TODO: Never used
    public void setCopyId(String copyId) {
        this.copyId = copyId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // TODO: Never used
    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    // TODO: Never used
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public LocalDate getDueDate() {
    return dueDate;
}

    // TODO: Never used
public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
}

}
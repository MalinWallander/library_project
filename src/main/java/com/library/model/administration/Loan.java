package com.library.model.administration;

import java.util.Date;

public class Loan {
    private int loanId;
    private int copyId;
    private int userId;
    private Date borrowDate;
    private Date returnDate;
    private int libraryId;

    public Loan(int loanId, int copyId, int userId) {
        this.loanId = loanId;
        this.copyId = copyId;
        this.userId = userId;
        this.borrowDate = new Date(); // Sätts till "nu"
    }
}
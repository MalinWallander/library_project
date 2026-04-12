package com.library.model.administration;

import java.time.LocalDate;

public class Receipt {
    private String receiptId;
    private String loanId;
    private LocalDate checkoutDate;

    public Receipt(String receiptId, String loanId, LocalDate checkoutDate) {
        this.receiptId = receiptId;
        this.loanId = loanId;
        this.checkoutDate = checkoutDate;
    }
}
package com.library.model.administration;

import java.time.LocalDate;

public class Purchase {
    private String purchaseId;
    private String itemId;
    private String employeeId;
    private LocalDate purchaseDate;
    private String status;
    private int quantity;

    public Purchase(String purchaseId, String itemId, String employeeId, LocalDate purchaseDate, String status,
            int quantity) {
        this.purchaseId = purchaseId;
        this.itemId = itemId;
        this.employeeId = employeeId;
        this.purchaseDate = purchaseDate;
        this.status = status;
        this.quantity = quantity;
    }
}
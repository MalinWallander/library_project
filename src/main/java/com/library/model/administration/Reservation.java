package com.library.model.administration;

import java.time.LocalDate;

public class Reservation {
    private String reservationId;
    private String userId;
    private String itemId;
    private LocalDate reservationDate;
    private String status;

    public Reservation(String reservationId, String userId, String itemId, LocalDate reservationDate, String status) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.itemId = itemId;
        this.reservationDate = reservationDate;
        this.status = status;
    }
}
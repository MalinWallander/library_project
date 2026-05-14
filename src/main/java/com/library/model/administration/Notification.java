package com.library.model.administration;

import java.time.LocalDate;

// TODO: Class never used
public class Notification {
    private String notificationId;
    private String loanId;
    private String notificationType;
    private LocalDate notificationDate;
    private String message;
    private String status;

    public Notification(String notificationId, String loanId, String notificationType, LocalDate notificationDate,
            String message, String status) {
        this.notificationId = notificationId;
        this.loanId = loanId;
        this.notificationType = notificationType;
        this.notificationDate = notificationDate;
        this.message = message;
        this.status = status;
    }
}
package com.library.model;

import java.time.LocalDate;
import java.util.UUID;

public class User {
    private UUID userId;
    private String fName;
    private String lName;
    private String email;
    private LocalDate dateOfBirth;
    private String categoryId;
    private String phoneNumber;

    public User(
            UUID userId,
            String fName,
            String lName,
            String email,
            LocalDate dateOfBirth,
            String categoryId,
            String phoneNumber) {
        this.userId = userId;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.categoryId = categoryId;
        this.phoneNumber = phoneNumber;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

}
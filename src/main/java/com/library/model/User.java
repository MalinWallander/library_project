package com.library.model;

import java.time.LocalDate;

public class User {
    private String userId;
    private String fName;
    private String lName;
    private String email;
    private LocalDate dateOfBirth;
    private String categoryId;
    private String phoneNumber;

    public User(String userId, String fName, String lName, String email,
            LocalDate dateOfBirth, String categoryId, String phoneNumber) {
        this.userId = userId;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.categoryId = categoryId;
        this.phoneNumber = phoneNumber;
    }

    public String getUserId() {
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

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
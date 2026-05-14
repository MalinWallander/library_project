package com.library.model;

import java.util.UUID;

public class Employee {

    private final UUID employeeId;
    private final String fName;
    private final String lName;
    private final String email;
    private final String phoneNumber;

    public Employee(UUID employeeId, String fName, String lName, String email, String phoneNumber) {
        this.employeeId = employeeId;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public UUID getEmployeeId() {
        return employeeId;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
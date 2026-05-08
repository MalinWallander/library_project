package com.library.service;

import com.library.db.EmployeeDao;
import com.library.model.Employee;

import java.util.UUID;

public class EmployeeService {

    private final EmployeeDao employeeDao;
    private final AuthService authService;

    public EmployeeService(EmployeeDao employeeDao, AuthService authService) {
        this.employeeDao = employeeDao;
        this.authService = authService;
    }

    public void createEmployee(String fName,
                               String lName,
                               String email,
                               String phoneNumber,
                               String plainPassword) {
        validateEmployeeInput(fName, lName, email, phoneNumber, plainPassword);

        UUID employeeId = UUID.randomUUID();
        Employee employee = new Employee(employeeId, fName.trim(), lName.trim(), email.trim(), phoneNumber.trim());

        employeeDao.createEmployee(employee);
        authService.createEmployeeLogin(email, plainPassword, employeeId);
    }

    private void validateEmployeeInput(String fName, String lName, String email, String phoneNumber, String plainPassword) {
        if (fName == null || fName.isBlank()) {
            throw new IllegalArgumentException("First name is required.");
        }
        if (lName == null || lName.isBlank()) {
            throw new IllegalArgumentException("Last name is required.");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required.");
        }
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Phone number is required.");
        }
        if (plainPassword == null || plainPassword.isBlank()) {
            throw new IllegalArgumentException("Password is required.");
        }
    }
}

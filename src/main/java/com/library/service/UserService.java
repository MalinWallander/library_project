package com.library.service;

import com.library.model.User;
import com.library.db.UserDao;

import java.time.LocalDate;
import java.util.UUID;

public class UserService {

    private final UserDao dao;
    private final AuthService authService;

    public UserService(UserDao dao, AuthService authService) {
        this.dao = dao;
        this.authService = authService;
    }

    public void createUser(String fName,
            String lName,
            String email,
            LocalDate dateOfBirth,
            String categoryId,
            String phoneNumber,
            String plainPassword) {

        validateUserInput(fName, lName, email, dateOfBirth, categoryId, phoneNumber, plainPassword);

        UUID userId = UUID.randomUUID();

        User newUser = new User(
                userId.toString(),
                fName.trim(),
                lName.trim(),
                email.trim(),
                dateOfBirth,
                categoryId,
                phoneNumber.trim());

        dao.createUser(newUser);

        authService.createBorrowerLogin(email, plainPassword, userId.toString());

        System.out.println("User created: " + newUser.getEmail());
    }

    private void validateUserInput(String fName,
            String lName,
            String email,
            LocalDate dateOfBirth,
            String categoryId,
            String phoneNumber,
            String plainPassword) {
        // TODO: Do you want to throw exceptions if user does not fill in correctly?
        // TODO: Setup validation class instead and validate input more thoroughly
        if (fName == null || fName.isBlank())
            throw new IllegalArgumentException("First name is required.");
        if (lName == null || lName.isBlank())
            throw new IllegalArgumentException("Last name is required.");
        if (email == null || email.isBlank())
            throw new IllegalArgumentException("Email is required.");
        if (dateOfBirth == null)
            throw new IllegalArgumentException("Date of birth is required.");
        if (categoryId == null || categoryId.isBlank())
            throw new IllegalArgumentException("Borrower category is required.");
        if (phoneNumber == null || phoneNumber.isBlank())
            throw new IllegalArgumentException("Phone number is required.");
        if (plainPassword == null || plainPassword.isBlank())
            throw new IllegalArgumentException("Password is required.");
    }
}

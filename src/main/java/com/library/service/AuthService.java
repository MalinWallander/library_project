package com.library.service;

import com.library.db.AuthDao;
import com.library.model.auth.AuthAccount;
import com.library.model.auth.AuthRole;
import com.library.model.auth.CurrentSession;
import com.library.model.auth.LoginResult;

import java.util.Optional;
import java.util.UUID;

public class AuthService {

    private final AuthDao authDao;
    private final PasswordService passwordService;
    private CurrentSession currentSession;

    public AuthService(AuthDao authDao, PasswordService passwordService) {
        this.authDao = authDao;
        this.passwordService = passwordService;
    }

    public LoginResult login(String email, String plainPassword) {
        // TODO: Setup helper validation class?
        if (email == null || email.isBlank() || plainPassword == null || plainPassword.isBlank()) {
            return LoginResult.failure("Please enter both email and password.");
        }

        Optional<AuthAccount> optionalAccount = authDao.findByEmail(email.trim());
        if (optionalAccount.isEmpty()) {
            return LoginResult.failure("No account was found with that email.");
        }

        AuthAccount account = optionalAccount.get();
        boolean passwordMatches = passwordService.verifyPassword(plainPassword, account.getPasswordHash());
        if (!passwordMatches) {
            return LoginResult.failure("Wrong password.");
        }

        currentSession = new CurrentSession(
                account.getAccountId(),
                account.getEmail(),
                account.getRole(),
                account.getUserId(),
                account.getEmployeeId());

        return LoginResult.success(currentSession);
    }

    public void createBorrowerLogin(String email, String plainPassword, String userId) {
        validateAccountCreation(email, plainPassword);
        if (userId == null) {
            throw new IllegalArgumentException("Borrower account must be linked to a user.");
        }

        AuthAccount account = new AuthAccount(
                UUID.randomUUID().toString(),
                email.trim(),
                passwordService.hashPassword(plainPassword),
                AuthRole.BORROWER,
                userId,
                null);

        authDao.createAccount(account);
    }

    public void createEmployeeLogin(String email, String plainPassword, String employeeId) {
        validateAccountCreation(email, plainPassword);
        if (employeeId == null) {
            throw new IllegalArgumentException("Employee account must be linked to an employee.");
        }

        AuthAccount account = new AuthAccount(
                UUID.randomUUID().toString(),
                email.trim(),
                passwordService.hashPassword(plainPassword),
                AuthRole.EMPLOYEE,
                null,
                employeeId);

        authDao.createAccount(account);
    }

    private void validateAccountCreation(String email, String plainPassword) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required.");
        }
        if (plainPassword == null || plainPassword.isBlank()) {
            throw new IllegalArgumentException("Password is required.");
        }
        if (plainPassword.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long.");
        }
        if (authDao.emailExists(email.trim())) {
            throw new IllegalArgumentException("That email address is already in use.");
        }
    }

    public CurrentSession getCurrentSession() {
        return currentSession;
    }

    public boolean isLoggedIn() {
        return currentSession != null;
    }

    public AuthRole getCurrentRole() {
        return currentSession == null ? null : currentSession.getRole();
    }

    public void logout() {
        currentSession = null;
    }
}
package com.library.model.auth;

public class AuthAccount {
    private String accountId;
    private String email;
    private String passwordHash;
    private AuthRole role;
    private String userId;
    private String employeeId;

    public AuthAccount(String accountId, String email, String passwordHash, AuthRole role, String userId,
            String employeeId) {
        this.accountId = accountId;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.userId = userId;
        this.employeeId = employeeId;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public AuthRole getRole() {
        return role;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmployeeId() {
        return employeeId;
    }
}
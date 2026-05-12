package com.library.model.auth;

public class CurrentSession {
    private final String accountId;
    private final String email;
    private final AuthRole role;
    private final String userId;
    private final String employeeId;

    public CurrentSession(String accountId, String email, AuthRole role, String userId, String employeeId) {
        this.accountId = accountId;
        this.email = email;
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
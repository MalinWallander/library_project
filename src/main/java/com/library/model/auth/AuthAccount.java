package com.library.model.auth;

import java.util.UUID;

public class AuthAccount {
    private UUID accountId;
    private String email;
    private String passwordHash;
    private AuthRole role;
    private UUID userId;
    private UUID employeeId;

    public AuthAccount(UUID accountId, String email, String passwordHash, AuthRole role, UUID userId, UUID employeeId) {
        this.accountId = accountId;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.userId = userId;
        this.employeeId = employeeId;
    }

    public UUID getAccountId() { return accountId; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public AuthRole getRole() { return role; }
    public UUID getUserId() { return userId; }
    public UUID getEmployeeId() { return employeeId; }
}

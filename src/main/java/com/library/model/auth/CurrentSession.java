package com.library.model.auth;

import java.util.UUID;

public class CurrentSession {
    private final UUID accountId;
    private final String email;
    private final AuthRole role;
    private final UUID userId;
    private final UUID employeeId;

    public CurrentSession(UUID accountId, String email, AuthRole role, UUID userId, UUID employeeId) {
        this.accountId = accountId;
        this.email = email;
        this.role = role;
        this.userId = userId;
        this.employeeId = employeeId;
    }

    public UUID getAccountId() { return accountId; }
    public String getEmail() { return email; }
    public AuthRole getRole() { return role; }
    public UUID getUserId() { return userId; }
    public UUID getEmployeeId() { return employeeId; }
}

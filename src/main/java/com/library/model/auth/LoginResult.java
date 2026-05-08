package com.library.model.auth;

public class LoginResult {
    private final boolean success;
    private final String message;
    private final CurrentSession session;

    private LoginResult(boolean success, String message, CurrentSession session) {
        this.success = success;
        this.message = message;
        this.session = session;
    }

    public static LoginResult success(CurrentSession session) {
        return new LoginResult(true, "Login successful.", session);
    }

    public static LoginResult failure(String message) {
        return new LoginResult(false, message, null);
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public CurrentSession getSession() { return session; }
}

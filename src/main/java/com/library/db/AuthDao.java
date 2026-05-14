package com.library.db;

import com.library.model.auth.AuthAccount;

import java.util.Optional;

public interface AuthDao {
    // TODO: return value is never used, return void instead?
    AuthAccount createAccount(AuthAccount account);
    Optional<AuthAccount> findByEmail(String email);
    boolean emailExists(String email);
}

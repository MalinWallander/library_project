package com.library.db;

import com.library.model.auth.AuthAccount;

import java.util.Optional;

public interface AuthDao {
    AuthAccount createAccount(AuthAccount account);
    Optional<AuthAccount> findByEmail(String email);
    boolean emailExists(String email);
}

package com.library.db.impl;

import com.library.db.AuthDao;
import com.library.model.auth.AuthAccount;
import com.library.model.auth.AuthRole;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class AuthDaoImpl implements AuthDao {

    private final NamedParameterJdbcTemplate jdbc;

    private static final String CREATE_SQL = """
            INSERT INTO auth_account (account_id, email, password_hash, role, user_id, employee_id)
            VALUES (:accountId, :email, :passwordHash, :role, :userId, :employeeId)
            """;

    private static final String FIND_BY_EMAIL_SQL = """
            SELECT account_id, email, password_hash, role, user_id, employee_id
            FROM auth_account
            WHERE LOWER(email) = LOWER(:email)
            """;

    private static final String EXISTS_BY_EMAIL_SQL = """
            SELECT COUNT(*)
            FROM auth_account
            WHERE LOWER(email) = LOWER(:email)
            """;

    public AuthDaoImpl(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public AuthAccount createAccount(AuthAccount account) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("accountId", account.getAccountId())
                .addValue("email", account.getEmail())
                .addValue("passwordHash", account.getPasswordHash())
                .addValue("role", account.getRole().name())
                .addValue("userId", account.getUserId())
                .addValue("employeeId", account.getEmployeeId());

        jdbc.update(CREATE_SQL, params);
        return account;
    }

    @Override
    public Optional<AuthAccount> findByEmail(String email) {
        MapSqlParameterSource params = new MapSqlParameterSource("email", email);
        return jdbc.query(FIND_BY_EMAIL_SQL, params, this::mapRow).stream().findFirst();
    }

    @Override
    public boolean emailExists(String email) {
        MapSqlParameterSource params = new MapSqlParameterSource("email", email);
        Integer count = jdbc.queryForObject(EXISTS_BY_EMAIL_SQL, params, Integer.class);
        return count != null && count > 0;
    }

    private AuthAccount mapRow(ResultSet rs, int rowNum) throws SQLException { // TODO: Dont throw exception where method is called. Add a try / catch where exception can occur.
        return new AuthAccount(
                rs.getString("account_id"),
                rs.getString("email"),
                rs.getString("password_hash"),
                AuthRole.valueOf(rs.getString("role")),
                rs.getString("user_id"),
                rs.getString("employee_id"));
    }
}

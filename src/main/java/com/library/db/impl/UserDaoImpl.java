package com.library.db.impl;

import com.library.db.UserDao;
import com.library.model.User;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public class UserDaoImpl implements UserDao {

    private final NamedParameterJdbcTemplate jdbc;

    public UserDaoImpl(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final static String CREATE_SQL = """
            INSERT INTO "User" (user_id, f_name, l_name, email, date_of_birth, category_id, phone_number)
            VALUES (:userId, :fName, :lName, :email, :dateOfBirth, :categoryId, :phoneNumber)
            """;

    private final static String FIND_BY_USERID_SQL = """
            SELECT user_id, f_name, l_name, email, date_of_birth, category_id, phone_number
            FROM "User"
            WHERE user_id = :userId
            """;

    @Override
    public User createUser(User user) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", user.getUserId())
                .addValue("fName", user.getfName())
                .addValue("lName", user.getlName())
                .addValue("email", user.getEmail())
                .addValue("dateOfBirth", user.getDateOfBirth())
                .addValue("categoryId", user.getCategoryId())
                .addValue("phoneNumber", user.getPhoneNumber());

        jdbc.update(CREATE_SQL, params);
        return user;
    }

    public Optional<User> findById(UUID userId) {
        MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
        return jdbc.query(FIND_BY_USERID_SQL, params, this::mapRow)
                .stream()
                .findFirst();
    }

    private User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                UUID.fromString(rs.getString("user_id")),
                rs.getString("f_name"),
                rs.getString("l_name"),
                rs.getString("email"),
                rs.getObject("date_of_birth", LocalDate.class),
                rs.getString("category_id"),
                rs.getString("phone_number"));
    }
}

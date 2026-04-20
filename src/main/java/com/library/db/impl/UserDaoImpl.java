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
            	INSERT INTO \"User\" (user_id, f_name, l_name, email, date_of_birth, category_id, phone_number)
            	VALUES (:userId, :fName, :lName, :email, :dateOfBirth, :categoryId, :phoneNumber)
            """;

    private final static String FIND_BY_USERID_SQL = """
            SELECT * FROM \"User\" WHERE user_id = :userId
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

    public Optional<User> findById(Long id) {
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        return jdbc.query(FIND_BY_USERID_SQL, params, this::mapRow)
                .stream()
                .findFirst();
    }

    private User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                UUID.fromString(rs.getString("userId")),
                rs.getString("fName"),
                rs.getString("lName"),
                rs.getString("email"),
                LocalDate.parse(rs.getString("dateOfBirth")), // Se till att typerna blir rätt här, vad står det för typ
                                                              // i databasen jämfört vad ni vill ha på javasidan
                rs.getString("categoryId"),
                rs.getString("phoneNumber"));
    }

}
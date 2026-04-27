package com.library.db.impl;

import com.library.db.ReservationDao;
import com.library.model.items.Book;
import com.library.model.items.Dvd;
import com.library.model.items.Item;
import com.library.model.items.Periodical;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;




public class ReservationDaoImpl implements ReservationDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ReservationDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
@Override
public void createReservation(String itemId, String userId) {
    // Generera ett enkelt unikt ID (eller låt databasen sköta det om möjligt)
    String resId = "RES" + System.currentTimeMillis(); 

    String sql = """
        INSERT INTO "Reservation" ("reservationId", "userId", "itemId", "status", "reservationDate")
        VALUES (:resId, :userId, :itemId, 'Active', CURRENT_DATE)
    """;

    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("resId", resId)
        .addValue("itemId", itemId)
        .addValue("userId", userId);

    jdbcTemplate.update(sql, params);
}

@Override
public boolean isAlreadyReserved(String itemId) {
    String sql = """
        SELECT COUNT(*)
        FROM "Reservation"
        WHERE "itemId" = :itemId AND "status" = 'Active'
    """;

    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("itemId", itemId);

    Integer count = (Integer) jdbcTemplate.queryForObject(sql, params, Integer.class);
    return count != null && count > 0;
}
@Override
public List<Item> findByUser(String userId) {
    String sql = """
    SELECT i.*, 

           -- BOOK
           b.isbn,
           b.genre,
           b."publisherId",
           b."mainAuthorName",
           b."isCourseLiterature",

           -- DVD
           d."productionYear",
           d."mainDirectorName",

           -- PERIODICAL
           p."publisher",
           p."issn",

           -- Reservation status
           r."status" as reservationStatus

    FROM "Item" i
    LEFT JOIN "Book" b ON i."itemId" = b."itemId"
    LEFT JOIN "Dvd" d ON i."itemId" = d."itemId"
    LEFT JOIN "Periodical" p ON i."itemId" = p."itemId"
    JOIN "Reservation" r ON i."itemId" = r."itemId"

    WHERE r."userId" = :userId
""";

    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("userId", userId);

    return jdbcTemplate.query(sql, params, this::mapRow);
}

private Item mapRow(ResultSet rs, int rowNum) throws SQLException {

    String type = rs.getString("itemType");
    String status = rs.getString("reservationStatus");

    if ("Book".equalsIgnoreCase(type)) {
        return new Book(
            rs.getString("itemId"),
            type,
            rs.getString("itemTitle"),
            rs.getString("categoryId"),
            status,
            rs.getString("isbn"),
            rs.getString("publisherId"),
            rs.getString("genre"),
            rs.getString("mainAuthorName"),
            rs.getBoolean("isCourseLiterature") // 🔥 NY
        );

    } else if ("Dvd".equalsIgnoreCase(type)) {
        return new Dvd(
            rs.getString("itemId"),
            type,
            rs.getString("itemTitle"),
            rs.getString("categoryId"),
            status,
            rs.getInt("productionYear"),
            rs.getString("mainDirectorName")
        );

    } else if ("Periodical".equalsIgnoreCase(type)) {
        return new Periodical(
            rs.getString("itemId"),
            type,
            rs.getString("itemTitle"),
            rs.getString("categoryId"),
            status,
            rs.getString("publisher"),
            rs.getString("issn")
        );
    }

    throw new RuntimeException("Unknown type: " + type);
}
}
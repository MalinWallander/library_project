package com.library.db.impl;

import com.library.db.ReservationDao;
import com.library.model.items.Book;
import com.library.model.items.Dvd;
import com.library.model.items.Item;

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
    // SQL matchar nu din bild: Tabell "Reservation", kolumner userId, itemId, status, reservationDate
    String sql = """
        INSERT INTO "Reservation" ("userId", "itemId", "status", "reservationDate")
        VALUES (:userId, :itemId, 'Active', CURRENT_DATE)
    """;

    MapSqlParameterSource params = new MapSqlParameterSource()
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
           b."mainAuthorName",
           d."mainDirectorName",

           CASE
               WHEN EXISTS (
                   SELECT 1 FROM "Copy" c 
                   WHERE c."itemId" = i."itemId" 
                   AND c.status = 'Available'
               ) THEN 'Available'
               WHEN EXISTS (
                   SELECT 1 FROM "Copy" c 
                   WHERE c."itemId" = i."itemId"
               ) THEN 'Loaned'
               ELSE 'No copies'
           END as status

    FROM "Item" i
    LEFT JOIN "Book" b ON i."itemId" = b."itemId"
    LEFT JOIN "Dvd" d ON i."itemId" = d."itemId"
    JOIN reservations r ON i."itemId" = r.item_id
    WHERE r.user_id = :userId
""";

    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("userId", userId);

    return jdbcTemplate.query(sql, params, this::mapRow);
}
private Item mapRow(ResultSet rs, int rowNum) throws SQLException {


    String type = rs.getString("itemType");
    String status = rs.getString("status");

    if ("Book".equalsIgnoreCase(type)) {
        return new Book(
            rs.getString("itemId"),
            type,
            rs.getString("itemTitle"),
            rs.getString("categoryId"),
            status,
            null, null, null,
            rs.getString("mainAuthorName")
        );
    } else if ("Dvd".equalsIgnoreCase(type)) {
        return new Dvd(
            rs.getString("itemId"),
            type,
            rs.getString("itemTitle"),
            rs.getString("categoryId"),
            status,
            0,
            rs.getString("mainDirectorName")
        );
    }

    throw new RuntimeException("Unknown type: " + type);

}

}
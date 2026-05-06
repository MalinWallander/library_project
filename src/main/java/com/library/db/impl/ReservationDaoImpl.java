package com.library.db.impl;

import com.library.db.ReservationDao;
import com.library.model.items.Book;
import com.library.model.items.Dvd;
import com.library.model.items.Periodical;
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

    // ✅ Skapa reservation (3 dagar)
    @Override
    public void createReservation(String itemId, String userId) {


        
        String resId = "RES" + System.currentTimeMillis();

    String sql = """
    INSERT INTO "Reservation"
    ("reservationId", "userId", "itemId", "status")
    VALUES (:resId, :userId, :itemId, 'Active')
""";

        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("resId", resId)
            .addValue("itemId", itemId)
            .addValue("userId", userId);

        jdbcTemplate.update(sql, params);
    }

    // ✅ Kolla om redan reserverad
    @Override
    public boolean isAlreadyReserved(String itemId) {
        String sql = """
            SELECT COUNT(*)
            FROM "Reservation"
            WHERE "itemId" = :itemId AND "status" = 'Active'
        """;

        Integer count = jdbcTemplate.queryForObject(
            sql,
            new MapSqlParameterSource("itemId", itemId),
            Integer.class
        );

        return count != null && count > 0;
    }

    // ✅ Hämta typ (för Service-lager regler)
    @Override
    public String findItemType(String itemId) {
        String sql = """
            SELECT "itemType"
            FROM "Item"
            WHERE "itemId" = :itemId
        """;

        return jdbcTemplate.queryForObject(
            sql,
            new MapSqlParameterSource("itemId", itemId),
            String.class
        );
    }

    @Override
public boolean hasAvailableCopy(String itemId) {
    String sql = """
        SELECT COUNT(*)
        FROM "Copy"
        WHERE "itemId" = :itemId AND "status" = 'Available'
    """;

    Integer count = jdbcTemplate.queryForObject(
        sql,
        new MapSqlParameterSource("itemId", itemId),
        Integer.class
    );

    return count != null && count > 0;
}

    // ✅ Hämta användarens reservationer
    @Override
    public List<Item> findByUser(String userId) {

        String sql = """
            SELECT i.*, 
                   b."mainAuthorName",
                   b."isbn",
                   b."publisherId",
                   b."genre",
                   b."isCourseLiterature",
                   d."mainDirectorName",
                   p."issueNumber",
                   p."publisher",
                   p."editorName",
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

    // ✅ MAPROW (nu korrekt för ALLA typer)
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
                rs.getBoolean("isCourseLiterature")
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

        } else if ("Periodical".equalsIgnoreCase(type)) {
            return new Periodical(
                rs.getString("itemId"),
                type,
                rs.getString("itemTitle"),
                rs.getString("categoryId"),
                status,
                rs.getString("issueNumber"),
                rs.getString("publisher"),
                rs.getString("editorName")
            );
        }

        throw new RuntimeException("Unknown type: " + type);
    }
}
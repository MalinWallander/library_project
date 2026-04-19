package com.library.db.impl;

import com.library.db.ItemDao;
import com.library.model.items.Item;
import com.library.model.items.Book;
import com.library.model.items.Dvd;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ItemDAOImpl implements ItemDao {

    private final NamedParameterJdbcTemplate jdbc;

    public ItemDAOImpl(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

  private static final String ADVANCED_SEARCH_SQL = """
    SELECT i.*,
           b.isbn,
           b."mainAuthorName" AS author,
           b."publisherId" AS publisher_id,
           b.genre,
           d."productionYear" AS production_year,
           d."mainDirectorName" AS director,
           CASE
               WHEN EXISTS (SELECT 1 FROM "Copy" c WHERE c."itemId" = i."itemId" AND c.status = 'Available') THEN 'Available'
               WHEN EXISTS (SELECT 1 FROM "Copy" c WHERE c."itemId" = i."itemId") THEN 'Loaned'
               ELSE 'No copies'
           END as current_status
    FROM "Item" i
    LEFT JOIN "Book" b ON i."itemId" = b."itemId"
    LEFT JOIN "Dvd" d ON i."itemId" = d."itemId"
    WHERE (:title IS NULL OR i."itemTitle" ILIKE :title)
    AND (
        :creator IS NULL 
        OR (i."itemType" = 'Book' AND b."mainAuthorName" ILIKE :creator)
        OR (i."itemType" = 'Dvd' AND d."mainDirectorName" ILIKE :creator)
    )
    AND (:category IS NULL OR i."categoryId" = :category)
""";
    @Override
    public List<Item> search(String title, String creator, String categoryId) {

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("title",
                (title == null || title.isBlank()) ? null : "%" + title + "%",
                java.sql.Types.VARCHAR);

        params.addValue("creator",
                (creator == null || creator.isBlank()) ? null : "%" + creator + "%",
                java.sql.Types.VARCHAR);

        params.addValue("category",
                (categoryId == null || categoryId.isBlank()) ? null : categoryId,
                java.sql.Types.VARCHAR);

        return jdbc.query(ADVANCED_SEARCH_SQL, params, this::mapRow);
    }

    private Item mapRow(ResultSet rs, int rowNum) throws SQLException {

        String type = rs.getString("itemType");
        String status = rs.getString("current_status");

        if ("Book".equalsIgnoreCase(type)) {
            return new Book(
                    rs.getString("itemId"),
                    type,
                    rs.getString("itemTitle"),
                    rs.getString("categoryId"),
                    status,
                    rs.getString("isbn"),
                    rs.getString("publisher_id"),
                    rs.getString("genre"),
                    rs.getString("author")
            );

        } else if ("Dvd".equalsIgnoreCase(type)) {
            return new Dvd(
                    rs.getString("itemId"),
                    type,
                    rs.getString("itemTitle"),
                    rs.getString("categoryId"),
                    status,
                    rs.getInt("production_year"),
                    rs.getString("director")
            );
        }

        throw new RuntimeException("Unknown item type: " + type);
    }
}
package com.library.db.impl;

import com.library.db.SearchItemDao;
import com.library.model.items.Item;
import com.library.model.items.Periodical;
import com.library.model.items.Book;
import com.library.model.items.Dvd;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SearchItemDaoImpl implements SearchItemDao {

	private final NamedParameterJdbcTemplate jdbc;

	public SearchItemDaoImpl(NamedParameterJdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	private static final String ADVANCED_SEARCH_SQL = """
    SELECT i.*,

           -- BOOK
           b.isbn,
           b.genre,
           b."publisherId",
           b."mainAuthorName" AS author,
           b."isCourseLiterature",

           -- DVD
           d."productionYear",
           d."mainDirectorName" AS director,

           -- PERIODICAL
            p."issueNumber",
            p."publisher",
            p."editorName",

           -- STATUS
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
           END AS current_status

    FROM "Item" i
    LEFT JOIN "Book" b ON i."itemId" = b."itemId"
    LEFT JOIN "Dvd" d ON i."itemId" = d."itemId"
    LEFT JOIN "Periodical" p ON i."itemId" = p."itemId"

    WHERE (CAST(:title AS TEXT) IS NULL OR i."itemTitle" ILIKE CAST(:title AS TEXT))

AND (
    CAST(:creator AS TEXT) IS NULL
    OR (i."itemType" = 'Book' AND b."mainAuthorName" ILIKE CAST(:creator AS TEXT))
    OR (i."itemType" = 'Dvd' AND d."mainDirectorName" ILIKE CAST(:creator AS TEXT))
    OR (i."itemType" = 'Periodical' AND p."publisher" ILIKE CAST(:creator AS TEXT))
)

AND (CAST(:category AS TEXT) IS NULL OR i."itemType" = CAST(:category AS TEXT))

""";

	@Override
public List<Item> search(String title, String creator, String categoryId) {

    MapSqlParameterSource params = new MapSqlParameterSource();

    params.addValue("title",
        (title == null || title.isBlank()) ? null : "%" + title + "%");

    params.addValue("creator",
        (creator == null || creator.isBlank()) ? null : "%" + creator + "%");

    params.addValue("category",
        (categoryId == null || categoryId.isBlank()) ? null : categoryId);

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
            rs.getString("publisherId"),
            rs.getString("genre"),
            rs.getString("author"),
            rs.getBoolean("isCourseLiterature")
        );

    } else if ("Dvd".equalsIgnoreCase(type)) {
        return new Dvd(
            rs.getString("itemId"),
            type,
            rs.getString("itemTitle"),
            rs.getString("categoryId"),
            status,
            rs.getInt("productionYear"),
            rs.getString("director")
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

    throw new RuntimeException("Unknown itemType: " + type);
}
}





package com.library.db.impl;

import com.library.db.ItemDao;
import com.library.model.items.Item;
import com.library.model.items.Book;
import com.library.model.items.CourseLiterature;
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

   private final static String ADVANCED_SEARCH_SQL = """
    SELECT i.*, 
        CASE 
            WHEN EXISTS (SELECT 1 FROM Copy c WHERE c.item_id = i.item_id AND c.status = 'Available') THEN 'Available'
            WHEN EXISTS (SELECT 1 FROM Copy c WHERE c.item_id = i.item_id) THEN 'Loaned'
            ELSE 'No copies'
        END as current_status
    FROM Item i
    WHERE (:title IS NULL OR i.item_title ILIKE :title)
    AND (:creator IS NULL OR i.author ILIKE :creator OR i.director ILIKE :creator)
    AND (:category IS NULL OR i.category_id = :category)
    """;

       @Override
    public List<Item> search(String title, String creator, String categoryId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", (title == null || title.isBlank()) ? null : "%" + title + "%");
        params.addValue("creator", (creator == null || creator.isBlank()) ? null : "%" + creator + "%");
        params.addValue("category", (categoryId == null || categoryId.isBlank()) ? null : categoryId);
 
        return jdbc.query(ADVANCED_SEARCH_SQL, params, this::mapRow);
    }

    private Item mapRow(ResultSet rs, int rowNum) throws SQLException {
        String type = rs.getString("item_type");
        String status = rs.getString("current_status"); // Hämtas från vår CASE-sats i SQL
 
        if ("Book".equalsIgnoreCase(type)) {
            return new Book(
                rs.getString("item_id"),
                type,
                rs.getString("item_title"),
                rs.getString("category_id"),
                status, // current_status från SQL
                rs.getString("isbn"),
                rs.getString("publisher_id"),
                rs.getString("genre"),
                rs.getString("author") 
            );
        } else if ("Dvd".equalsIgnoreCase(type)) {
            return new Dvd(
                rs.getString("item_id"),
                type,
                rs.getString("item_title"),
                rs.getString("category_id"),
                status,
                rs.getInt("production_year"),
                rs.getString("director")
            );
        } else if ("CourseLiterature".equalsIgnoreCase(type)) {
            return new CourseLiterature(
                rs.getString("item_id"),
                type,
                rs.getString("item_title"),
                rs.getString("category_id"),
                status,
                rs.getString("course_name"),
                rs.getString("author")
            );
        }
 
        return null; // En säkerhetsåtgärd om inget matchar
    } // Stänger hela mapRow-metoden
 
} // Stänger hela ItemDAOImpl-klassen
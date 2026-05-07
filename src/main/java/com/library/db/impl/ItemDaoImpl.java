package com.library.db.impl;

import com.library.db.ItemDao;
import com.library.model.items.Book;
import com.library.model.items.Copy;
import com.library.model.items.Dvd;
import com.library.model.items.Item;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.*;
import java.util.List;

public class ItemDaoImpl implements ItemDao {

	private final NamedParameterJdbcTemplate jdbc;

	public ItemDaoImpl(NamedParameterJdbcTemplate jdbctemplate) {
		this.jdbc = jdbctemplate;
	}

	@Override
	public void addItem(Item item) {
		jdbc.getJdbcOperations().execute((Connection conn) -> {
			try {
				conn.setAutoCommit(false);

				// 1. Always insert into Item first
				insertIntoItem(item, conn);

				// 2. Insert into type-specific table
				switch (item.getItemType()) {
					case "Book" -> insertIntoBook(item, conn);
					case "DVD" -> insertIntoDvd(item, conn);
					default -> throw new IllegalArgumentException(
							"Unknown item type: " + item.getItemType());
				}

				conn.commit();

			} catch (SQLException e) {
				try {
					conn.rollback();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				throw new RuntimeException("Failed to add item: " + e.getMessage(), e);
			} finally {
				try {
					conn.setAutoCommit(true);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return null;
		});
	}

	private void insertIntoItem(Item item, Connection conn) throws SQLException {
		String sql = "INSERT INTO \"Item\" (\"itemId\", \"itemType\", \"itemTitle\", \"categoryId\") VALUES (?, ?, ?, ?)";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, item.getItemId());
			stmt.setString(2, item.getItemType());
			stmt.setString(3, item.getItemTitle());
			stmt.setString(4, item.getCategoryId());
			stmt.executeUpdate();
		}
	}

	private void insertIntoBook(Item item, Connection conn) throws SQLException {
		String sql = "INSERT INTO \"Book\" (\"itemId\", \"isbn\", \"genre\", \"mainAuthorName\", \"publisherId\") VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, item.getItemId());
			stmt.setString(2, ((Book) item).getIsbn());
			stmt.setString(3, ((Book) item).getGenre());
			stmt.setString(4, ((Book) item).getMainAuthorName());
			stmt.setString(5, ((Book) item).getPublisherId());
			stmt.executeUpdate();
		}
	}

	private void insertIntoDvd(Item item, Connection conn) throws SQLException {
		String sql = "INSERT INTO \"Dvd\" (\"itemId\", \"productionYear\", \"mainDirectorName\") VALUES (?, ?, ?)";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, item.getItemId());
			if (((Dvd) item).getProductionYear() != null) {
				stmt.setInt(2, ((Dvd) item).getProductionYear());
			} else {
				stmt.setNull(2, Types.INTEGER);
			}
			stmt.setString(3, ((Dvd) item).getMainDirectorName());
			stmt.executeUpdate();
		}
	}

	@Override
	public void addCopy(Copy copy) {
		String sql = """
				INSERT INTO \"Copy\" (\"copyId\", \"status\", \"barcode\", \"referenceCopy\", \"purchaseDate\",
				                  \"location\", \"lastOnLoan\", \"itemTitle\", \"itemId\")
				VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
				""";
		jdbc.getJdbcOperations().execute((Connection conn) -> {
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setString(1, copy.getCopyId());
				stmt.setString(2, copy.getStatus());
				stmt.setString(3, copy.getBarcode());
				stmt.setBoolean(4, copy.isReferenceCopy());
				stmt.setDate(5, copy.getPurchaseDate() != null
						? Date.valueOf(copy.getPurchaseDate())
						: null);
				stmt.setString(6, copy.getLocation());
				stmt.setDate(7, copy.getLastOnLoan() != null
						? Date.valueOf(copy.getLastOnLoan())
						: null);
				stmt.setString(8, copy.getItemTitle());
				stmt.setString(9, copy.getItemId());
				stmt.executeUpdate();
			} catch (SQLException e) {
				throw new RuntimeException("Failed to add copy: " + e.getMessage(), e);
			}
			return null;
		});
	}

	@Override
public List<Item> searchByTitle(String title) {
    String sql = """
            SELECT i.*, 
                   COUNT(c."copyId") as copy_count
            FROM "Item" i
            LEFT JOIN "Copy" c ON c."itemId" = i."itemId"
            WHERE LOWER(i."itemTitle") LIKE LOWER(:title)
            GROUP BY i."itemId", i."itemType", i."itemTitle", i."categoryId"
            """;
    MapSqlParameterSource params = new MapSqlParameterSource("title", "%" + title + "%");
    return jdbc.query(sql, params, (rs, rowNum) -> {
        Item item = new Item(
            rs.getString("itemId"),
            rs.getString("itemType"),
            rs.getString("itemTitle"),
            rs.getString("categoryId"),
            null
        );
        item.setCreator(rs.getString("copy_count") + " copies");
        return item;
    });
}

@Override
public void updateItem(Item item) {
    jdbc.getJdbcOperations().execute((Connection conn) -> {
        try {
            conn.setAutoCommit(false);
            updateItemBase(item, conn);
            switch (item.getItemType()) {
                case "Book" -> updateBook(item, conn);
                case "DVD" -> updateDvd(item, conn);
            }
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw new RuntimeException("Failed to update item: " + e.getMessage(), e);
        } finally {
            conn.setAutoCommit(true);
        }
        return null;
    });
}

private void updateItemBase(Item item, Connection conn) throws SQLException {
    String sql = "UPDATE \"Item\" SET \"itemTitle\" = ?, \"categoryId\" = ? WHERE \"itemId\" = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, item.getItemTitle());
        stmt.setString(2, item.getCategoryId());
        stmt.setString(3, item.getItemId());
        stmt.executeUpdate();
    }
}

private void updateBook(Item item, Connection conn) throws SQLException {
    String sql = """
            UPDATE "Book" SET "isbn" = ?, "genre" = ?, 
            "mainAuthorName" = ?, "publisherId" = ? 
            WHERE "itemId" = ?
            """;
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, ((Book) item).getIsbn());
        stmt.setString(2, ((Book) item).getGenre());
        stmt.setString(3, ((Book) item).getMainAuthorName());
        stmt.setString(4, ((Book) item).getPublisherId());
        stmt.setString(5, item.getItemId());
        stmt.executeUpdate();
    }
}

private void updateDvd(Item item, Connection conn) throws SQLException {
    String sql = """
            UPDATE "Dvd" SET "productionYear" = ?, "mainDirectorName" = ? 
            WHERE "itemId" = ?
            """;
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        if (((Dvd) item).getProductionYear() != null) {
            stmt.setInt(1, ((Dvd) item).getProductionYear());
        } else {
            stmt.setNull(1, Types.INTEGER);
        }
        stmt.setString(2, ((Dvd) item).getMainDirectorName());
        stmt.setString(3, item.getItemId());
        stmt.executeUpdate();
    }
}
}
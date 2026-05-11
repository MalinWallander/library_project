package com.library.db.impl;

import com.library.db.LoanDao;
import com.library.model.administration.Loan;
import com.library.model.administration.LoanSummary;
import com.library.model.administration.OverdueLoanSummary;
import com.library.model.administration.Receipt;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class LoanDaoImpl implements LoanDao {

	private final NamedParameterJdbcTemplate jdbc;

	public LoanDaoImpl(NamedParameterJdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	private final static String CREATE_SQL = """
			INSERT INTO "Loan"
			(\"loanId\", \"copyId\", \"userId\", \"borrowDate\", \"dueDate\", \"returnDate\")
			VALUES
			(:loanId, :copyId, :userId, :loanDate, :dueDate, :returnDate)
			""";

	private final static String UPDATE_STATUS = """
			UPDATE "Copy" SET \"status\" = 'On loan' WHERE \"copyId\" = :copyId
			""";

	private final static String FIND_BY_LOANID_SQL = """
			SELECT * FROM "Loan" WHERE \"loanId\" = :loanId
			""";
	// private final static String UPDATE_LAST_ON_LOAN = """
	// UPDATE "Item" SET \"lastOnLoan\" = CURRENT_DATE WHERE item_id = :itemId
	// AND (\"lastOnLoan\" IS NULL OR \"lastOnLoan\" < CURRENT_DATE)
	// """;

	private final static String RECEIPT_SQL = """
			SELECT l."loanId",
			       l."copyId",
			       l."userId",
			       l."borrowDate",
			       l."dueDate",
			       COALESCE(i."itemTitle", c."itemTitle") AS item_title,
			       i."itemType" AS item_type,
			       u.f_name,
			       u.l_name
			FROM "Loan" l
			JOIN "Copy" c ON c."copyId" = l."copyId"
			LEFT JOIN "Item" i ON i."itemId" = c."itemId"
			LEFT JOIN "User" u ON u.user_id = l."userId"
			WHERE l."loanId\" = :loanId
			""";

	@Override
	public Loan createLoan(Loan loan) {
		// Look up copyId from barcode first
		String copyIdSql = "SELECT \"copyId\" FROM \"Copy\" WHERE \"barcode\" = :barcode";
		MapSqlParameterSource barcodeParams = new MapSqlParameterSource("barcode", loan.getCopyId());
		String copyId = jdbc.queryForObject(copyIdSql, barcodeParams, String.class);

		// Now build params using the real copyId
		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("loanId", loan.getLoanId())
				.addValue("copyId", copyId)
				.addValue("userId", loan.getUserId())
				.addValue("loanDate", loan.getLoanDate())
				.addValue("dueDate", loan.getDueDate())
				.addValue("returnDate", loan.getReturnDate());

		jdbc.update(CREATE_SQL, params);
		jdbc.update(UPDATE_STATUS, params);

		return loan;
	}

	public Optional<Loan> findById(String id) {
		MapSqlParameterSource params = new MapSqlParameterSource("loanId", id);
		return jdbc.query(FIND_BY_LOANID_SQL, params, this::mapRow)
				.stream()
				.findFirst();
	}

	@Override
	public Optional<Receipt> receipt(String loanId) {
		MapSqlParameterSource params = new MapSqlParameterSource("loanId", loanId);
		return jdbc.query(RECEIPT_SQL, params, this::mapReceiptRow)
				.stream()
				.findFirst();
	}

	private Loan mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Loan(
				UUID.fromString(rs.getString("loanId")),
				rs.getString("copyId"),
				rs.getString("userId"),
				rs.getDate("borrowDate").toLocalDate(),

				rs.getDate("dueDate") != null
						? rs.getDate("dueDate").toLocalDate()
						: null,

				rs.getDate("returnDate") != null
						? rs.getDate("returnDate").toLocalDate()
						: null);
	}

	private Receipt mapReceiptRow(ResultSet rs, int rowNum) throws SQLException {
		LocalDate loanDate = rs.getObject("borrowDate", LocalDate.class);
		LocalDate dueDate = rs.getObject("dueDate", LocalDate.class);

		String firstName = rs.getString("f_name");
		String lastName = rs.getString("l_name");
		String memberName = ((firstName == null ? "" : firstName) + " "
				+ (lastName == null ? "" : lastName)).trim();

		return new Receipt(
				rs.getString("loanId"),
				rs.getString("copyId"),
				rs.getString("item_title"),
				rs.getString("item_type"),
				rs.getString("userId"),
				memberName.isBlank() ? "Unknown member" : memberName,
				loanDate,
				dueDate);
	}

	// TODO verify names in db
	@Override
	public List<Loan> findByUserId(String userId) {
		String sql = """
				SELECT * FROM "Loan" WHERE "userId" = :userId AND "returnDate" IS NULL
				""";
		MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
		return jdbc.query(sql, params, this::mapRow);
	}

	@Override
	public List<Loan> getOverdueLoans() {

		String sql = """
				    SELECT *
				    FROM "Loan"
				    WHERE "returnDate" IS NULL
				    AND "dueDate" < CURRENT_DATE
				""";

		return jdbc.query(sql, this::mapRow);
	}

	@Override
	public List<LoanSummary> findSummariesByUserId(String userId) {
		String sql = """
				SELECT COALESCE(i."itemTitle", c."itemTitle") AS item_title,
				       l."borrowDate",
				       l."dueDate"
				FROM "Loan" l
				JOIN "Copy" c ON c."copyId" = l."copyId"
				LEFT JOIN "Item" i ON i."itemId" = c."itemId"
				WHERE l."userId" = :userId
				AND l."returnDate" IS NULL
				""";
		MapSqlParameterSource params = new MapSqlParameterSource("userId", userId);
		return jdbc.query(sql, params, (rs, rowNum) -> new LoanSummary(
				rs.getString("item_title"),
				rs.getDate("borrowDate") != null ? rs.getDate("borrowDate").toLocalDate() : null,
				rs.getDate("dueDate") != null ? rs.getDate("dueDate").toLocalDate() : null));
	}

	@Override
	public List<OverdueLoanSummary> getOverdueLoanSummaries() {
		String sql = """
				SELECT COALESCE(i."itemTitle", c."itemTitle") AS item_title,
				       u.f_name,
				       u.l_name,
				       l."userId",
				       l."dueDate"
				FROM "Loan" l
				JOIN "Copy" c ON c."copyId" = l."copyId"
				LEFT JOIN "Item" i ON i."itemId" = c."itemId"
				LEFT JOIN "User" u ON u.user_id = l."userId"
				WHERE l."returnDate" IS NULL
				AND l."dueDate" < CURRENT_DATE
				ORDER BY l."dueDate" ASC
				""";
		return jdbc.query(sql, (rs, rowNum) -> {
			String firstName = rs.getString("f_name");
			String lastName = rs.getString("l_name");
			String memberName = ((firstName == null ? "" : firstName) + " "
					+ (lastName == null ? "" : lastName)).trim();
			return new OverdueLoanSummary(
					rs.getString("item_title"),
					memberName.isBlank() ? "Unknown member" : memberName,
					rs.getString("userId"),
					rs.getDate("dueDate").toLocalDate());
		});
	}
}

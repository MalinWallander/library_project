package com.library.db.impl;

import com.library.db.LoanDao;
import com.library.model.administration.Loan;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class LoanDaoImpl implements LoanDao {

	private final NamedParameterJdbcTemplate jdbc;

	public LoanDaoImpl(NamedParameterJdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	private final static String CREATE_SQL = """
			INSERT INTO "Loan" (\"loanId\", \"copyId\", \"userId\", \"borrowDate\", \"returnDate\")
			VALUES (:loanId, :copyId, :userId, :loanDate, :returnDate)
			""";

	private final static String UPDATE_STATUS = """
			UPDATE "Copy" SET \"status\" = 'On loan' WHERE \"copyId\" = :copyId
			""";

	private final static String FIND_BY_LOANID_SQL = """
			SELECT * FROM "Loan" WHERE \"loanId\" = :loanId
			""";

	@Override
	public Loan createLoan(Loan loan) {

		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("loanId", loan.getLoanId())
				.addValue("copyId", loan.getCopyId())
				.addValue("userId", loan.getUserId())
				.addValue("loanDate", loan.getLoanDate())
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

	private Loan mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Loan(
				UUID.fromString(rs.getString("loanId")),
				rs.getString("copyId"), // matches Copy.copyId in DB
				rs.getString("userId"),
				LocalDate.parse(rs.getString("loanDate")),
				rs.getString("returnDate") != null
						? LocalDate.parse(rs.getString("returnDate"))
						: null // returnDate can be null on active loans
		);
	}
 //TODO verify names in db
	@Override
	public List<Loan> findByUserId(String userId) {
		String sql = "SELECT * FROM loans WHERE user_id = :userId AND return_date IS NULL";
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		return jdbcTemplate.query(sql, params, (rs, rowNum) -> new Loan(
				UUID.fromString(rs.getString("loan_id")),
				rs.getString("copy_id"),
				rs.getString("user_id"),
				rs.getDate("loan_date").toLocalDate(),
				rs.getDate("return_date") != null ? rs.getDate("return_date").toLocalDate() : null));
	}
}

package com.library.db.impl;

import com.library.db.LoanDao;
import com.library.model.administration.Loan;

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

	@Override
	public Loan createLoan(Loan loan) {

		MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("loanId", loan.getLoanId())
        .addValue("copyId", loan.getCopyId())
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
        : null
);
	}
 //TODO verify names in db
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
}

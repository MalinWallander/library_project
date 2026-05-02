package com.library.db.impl;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.library.db.ReturnDao;

public class ReturnDaoImpl implements ReturnDao {

	private final NamedParameterJdbcTemplate jdbc;

	public ReturnDaoImpl(NamedParameterJdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	private static final String FIND_COPY_ID_BY_BARCODE = """
			SELECT "copyId" FROM "Copy" WHERE "barcode" = :barcode
			""";

	private static final String UPDATE_STATUS = """
			UPDATE "Copy" SET "status" = 'Available' WHERE "copyId" = :copyId
			""";

	private static final String UPDATE_LOAN_RETURNED = """
			UPDATE "Loan" SET "returnDate" = CURRENT_TIMESTAMP WHERE "copyId" = :copyId AND "returnDate" IS NULL
			""";

	@Override
	public String returnCopy(String barcode) {
		MapSqlParameterSource params = new MapSqlParameterSource("barcode", barcode);

		// Look up the copyId from the barcode
		// String copyId = jdbc.queryForObject(FIND_COPY_ID_BY_BARCODE, new Object[] {
		// params }, String.class);
		String copyId = (String) jdbc.queryForObject(FIND_COPY_ID_BY_BARCODE, params, String.class);

		// Update the status to Available
		MapSqlParameterSource updateParams = new MapSqlParameterSource("copyId", copyId);
		jdbc.update(UPDATE_STATUS, updateParams);
		jdbc.update(UPDATE_LOAN_RETURNED, updateParams);

		return copyId;
	}
}
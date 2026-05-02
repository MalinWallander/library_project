package com.library.db.impl;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.library.db.ReturnDao;

public class ReturnDaoImpl implements ReturnDao {

	private final NamedParameterJdbcTemplate jdbc;

	public ReturnDaoImpl(NamedParameterJdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	// LAST ON LOAN, RETURNED COPY BASED ON COPY ID
	private static final String UPDATE_STATUS_BY_BARCODE = """
			UPDATE "Copy" SET "status" = 'Available' WHERE "barcode" = :barcode
			""";

	@Override
	public String returnCopy(String barcode) {
		MapSqlParameterSource params = new MapSqlParameterSource("barcode", barcode);
		int rowsAffected = jdbc.update(UPDATE_STATUS_BY_BARCODE, params);

		if (rowsAffected == 0) {
			throw new IllegalArgumentException("No copy found with barcode: " + barcode);
		}

		return barcode;
	}
}
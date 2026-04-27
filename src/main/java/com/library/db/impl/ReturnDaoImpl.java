package com.library.db.impl;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class ReturnDaoImpl {
	private final NamedParameterJdbcTemplate jdbc;

	public ReturnDaoImpl(NamedParameterJdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	private final static String UPDATE_STATUS = """
			UPDATE "Copy" SET \"status\" = 'Available' WHERE \"copyId\" = :copyId
			""";

	@Override
	public Copy returnCopy(String copyId) {
		MapSqlParameterSource params = new MapSqlParameterSource("copyId", copyId);
		jdbc.update(UPDATE_STATUS, params);
		return copyId;
	}
}

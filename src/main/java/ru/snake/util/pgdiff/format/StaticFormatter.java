package ru.snake.util.pgdiff.format;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StaticFormatter implements ValueFormatter {

	private final String value;

	public StaticFormatter(String value) {
		this.value = value;
	}

	@Override
	public String format(ResultSet resultSet) throws SQLException {
		return value;
	}

	@Override
	public String toString() {
		return "StaticFormatter [value=" + value + "]";
	}

}

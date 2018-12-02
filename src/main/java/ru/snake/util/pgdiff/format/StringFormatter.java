package ru.snake.util.pgdiff.format;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StringFormatter implements ValueFormatter {

	private final int index;

	public StringFormatter(int index) {
		this.index = index;
	}

	@Override
	public String format(ResultSet resultSet) throws SQLException {
		String value = resultSet.getString(index);

		if (resultSet.wasNull()) {
			return "<NULL>";
		}

		return value;
	}

	@Override
	public String toString() {
		return "StringFormatter [index=" + index + "]";
	}

}

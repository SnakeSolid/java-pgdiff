package ru.snake.util.pgdiff.format;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NumberFormatter implements ValueFormatter {

	private final int index;

	public NumberFormatter(int index) {
		this.index = index;
	}

	@Override
	public String format(ResultSet resultSet) throws SQLException {
		Object value = resultSet.getObject(index);

		if (resultSet.wasNull()) {
			return "<NULL>";
		}

		return String.valueOf(value);
	}

	@Override
	public String toString() {
		return "NumberFormatter [index=" + index + "]";
	}

}

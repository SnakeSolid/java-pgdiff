package ru.snake.util.pgdiff.comparator;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StringAccessor implements ValueAccessor<String> {

	private final int index;

	public StringAccessor(int index) {
		this.index = index;
	}

	@Override
	public String get(ResultSet resultSet) throws SQLException {
		return resultSet.getString(index);
	}

	@Override
	public int compare(String left, String right) {
		// TODO: String ordering depends on column collation.
		return left.compareTo(right);
	}

	@Override
	public String toString() {
		return "StringAccessor [index=" + index + "]";
	}

}

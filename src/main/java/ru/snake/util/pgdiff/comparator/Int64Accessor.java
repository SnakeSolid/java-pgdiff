package ru.snake.util.pgdiff.comparator;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Int64Accessor implements ValueAccessor<Long> {

	private final int index;

	public Int64Accessor(int index) {
		this.index = index;
	}

	@Override
	public Long get(ResultSet resultSet) throws SQLException {
		return resultSet.getLong(index);
	}

	@Override
	public int compare(Long left, Long right) {
		return Long.compare(left, right);
	}

	@Override
	public String toString() {
		return "Int64Accessor [index=" + index + "]";
	}

}

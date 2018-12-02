package ru.snake.util.pgdiff.comparator;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Int16Accessor implements ValueAccessor<Short> {

	private final int index;

	public Int16Accessor(int index) {
		this.index = index;
	}

	@Override
	public Short get(ResultSet resultSet) throws SQLException {
		return resultSet.getShort(index);
	}

	@Override
	public int compare(Short left, Short right) {
		return Short.compare(left, right);
	}

	@Override
	public String toString() {
		return "Int16Accessor [index=" + index + "]";
	}

}

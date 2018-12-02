package ru.snake.util.pgdiff.comparator;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Int32Accessor implements ValueAccessor<Integer> {

	private final int index;

	public Int32Accessor(int index) {
		this.index = index;
	}

	@Override
	public Integer get(ResultSet resultSet) throws SQLException {
		return resultSet.getInt(index);
	}

	@Override
	public int compare(Integer left, Integer right) {
		return Integer.compare(left, right);
	}

	@Override
	public String toString() {
		return "Int32Accessor [index=" + index + "]";
	}

}

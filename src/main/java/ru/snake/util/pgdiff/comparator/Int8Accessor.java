package ru.snake.util.pgdiff.comparator;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Int8Accessor implements ValueAccessor<Byte> {

	private final int index;

	public Int8Accessor(int index) {
		this.index = index;
	}

	@Override
	public Byte get(ResultSet resultSet) throws SQLException {
		return resultSet.getByte(index);
	}

	@Override
	public int compare(Byte left, Byte right) {
		return Byte.compare(left, right);
	}

	@Override
	public String toString() {
		return "Int8Accessor [index=" + index + "]";
	}

}

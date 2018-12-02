package ru.snake.util.pgdiff.comparator;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Float32Accessor implements ValueAccessor<Float> {

	private final int index;

	public Float32Accessor(int index) {
		this.index = index;
	}

	@Override
	public Float get(ResultSet resultSet) throws SQLException {
		return resultSet.getFloat(index);
	}

	@Override
	public int compare(Float left, Float right) {
		return Float.compare(left, right);
	}

	@Override
	public String toString() {
		return "Float32Accessor [index=" + index + "]";
	}

}

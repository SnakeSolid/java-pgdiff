package ru.snake.util.pgdiff.comparator;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Float64Accessor implements ValueAccessor<Double> {

	private final int index;

	public Float64Accessor(int index) {
		this.index = index;
	}

	@Override
	public Double get(ResultSet resultSet) throws SQLException {
		return resultSet.getDouble(index);
	}

	@Override
	public int compare(Double left, Double right) {
		return Double.compare(left, right);
	}

	@Override
	public String toString() {
		return "Float64Accessor [index=" + index + "]";
	}

}

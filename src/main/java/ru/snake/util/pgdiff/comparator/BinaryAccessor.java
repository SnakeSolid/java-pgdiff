package ru.snake.util.pgdiff.comparator;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BinaryAccessor implements ValueAccessor<byte[]> {

	private final int index;

	public BinaryAccessor(int index) {
		this.index = index;
	}

	@Override
	public byte[] get(ResultSet resultSet) throws SQLException {
		return resultSet.getBytes(index);
	}

	@Override
	public int compare(byte[] left, byte[] right) {
		int index = 0;
		int result = 0;

		while (index < left.length && index < right.length) {
			result = Byte.compare(left[index], right[index]);

			if (result != 0) {
				return result;
			}

			index += 1;
		}

		if (result == 0) {
			result = Integer.compare(left.length, right.length);
		}

		return result;
	}

	@Override
	public String toString() {
		return "BinaryAccessor [index=" + index + "]";
	}

}

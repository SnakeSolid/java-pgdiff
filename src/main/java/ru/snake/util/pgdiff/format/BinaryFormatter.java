package ru.snake.util.pgdiff.format;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BinaryFormatter implements ValueFormatter {

	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
			'E', 'F', };

	private final int index;

	public BinaryFormatter(int index) {
		this.index = index;
	}

	@Override
	public String format(ResultSet resultSet) throws SQLException {
		byte[] value = resultSet.getBytes(index);

		if (resultSet.wasNull()) {
			return "<NULL>";
		}

		return bytesToHex(value);
	}

	private String bytesToHex(byte[] value) {
		StringBuilder builder = new StringBuilder(1 + 2 * value.length);
		builder.append('x');

		for (byte b : value) {
			char upper = HEX_DIGITS[(b & 0x000000f0) >> 4];
			char lower = HEX_DIGITS[b & 0x0000000f];

			builder.append(upper);
			builder.append(lower);
		}

		return builder.toString();
	}

	@Override
	public String toString() {
		return "BinaryFormatter [index=" + index + "]";
	}

}

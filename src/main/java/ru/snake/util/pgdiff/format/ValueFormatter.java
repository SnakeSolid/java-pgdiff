package ru.snake.util.pgdiff.format;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Single column formatter.
 *
 * @author snake
 *
 */
public interface ValueFormatter {

	/**
	 * Format single result set column and return formatted string.
	 *
	 * @param resultSet
	 *            result set
	 * @return string value
	 * @throws SQLException
	 *             if error occurred during retrieving value from result set
	 */
	public String format(ResultSet resultSet) throws SQLException;

}

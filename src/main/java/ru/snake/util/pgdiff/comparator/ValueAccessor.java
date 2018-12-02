package ru.snake.util.pgdiff.comparator;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implements access and comparing to single value type.
 *
 * @author snake
 *
 * @param <T>
 *            value type
 */
public interface ValueAccessor<T> {

	/**
	 * Returns required value from result set.
	 *
	 * @param resultSet
	 *            result set
	 * @return value
	 * @throws SQLException
	 *             if error occurred during retrieving value
	 */
	public abstract T get(ResultSet resultSet) throws SQLException;

	/**
	 * Compare two values and returns integer Java comparing result.
	 *
	 * @param left
	 *            left value
	 * @param right
	 *            right value
	 * @return comparing result
	 */
	public abstract int compare(T left, T right);

}

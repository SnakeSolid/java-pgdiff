package ru.snake.util.pgdiff.comparator;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Common interface for all merge comparators.
 *
 * @author snake
 *
 */
public interface ResultSetComparator {

	/**
	 * Compare current rows in both data sets. Returns {@link Ordering#TAKE_LEFT} if
	 * left data set contains previous row. Returns {@link Ordering#MOVE_NEXT} if
	 * left data set has the same row as right. Returns {@link Ordering#TAKE_RIGHT}
	 * if left data set contains next row.
	 *
	 * @param lhs
	 *            left data-set
	 * @param rhs
	 *            right data-set
	 * @return comparison result
	 * @throws SQLException
	 *             if exception occurred during receiving value
	 */
	public Ordering compare(ResultSet lhs, ResultSet rhs) throws SQLException;

}

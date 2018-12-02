package ru.snake.util.pgdiff.comparator;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Abstract value comparator.
 *
 * @author snake
 *
 * @param <T>
 *            comparing type
 */
public class ValueComparator<T> implements ResultSetComparator {

	private final ValueAccessor<T> accessor;

	/**
	 * Create new null-less value comparator.
	 *
	 * @param accessor
	 *            value accessor
	 */
	public ValueComparator(ValueAccessor<T> accessor) {
		this.accessor = accessor;
	}

	@Override
	public Ordering compare(ResultSet lhs, ResultSet rhs) throws SQLException {
		return Ordering.fromInt(accessor.compare(accessor.get(lhs), accessor.get(rhs)));
	}

	@Override
	public String toString() {
		return "ValueComparator [accessor=" + accessor + "]";
	}

}

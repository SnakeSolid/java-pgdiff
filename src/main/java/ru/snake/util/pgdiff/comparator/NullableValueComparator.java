package ru.snake.util.pgdiff.comparator;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Abstract nullable value comparator.
 *
 * @author snake
 *
 * @param <T>
 *            comparing type
 */
public class NullableValueComparator<T> implements ResultSetComparator {

	private final ValueAccessor<T> accessor;

	/**
	 * Create new nullable value comparator.
	 *
	 * @param accessor
	 *            value accessor
	 */
	public NullableValueComparator(ValueAccessor<T> accessor) {
		this.accessor = accessor;
	}

	@Override
	public Ordering compare(ResultSet lhs, ResultSet rhs) throws SQLException {
		T left = accessor.get(lhs);
		T right = accessor.get(rhs);
		boolean leftNull = lhs.wasNull();
		boolean rightNull = lhs.wasNull();

		if (leftNull && rightNull) {
			return Ordering.EQUALS;
		} else if (leftNull) {
			return Ordering.GREATER;
		} else if (rightNull) {
			return Ordering.LESS;
		}

		return Ordering.fromInt(accessor.compare(left, right));
	}

	@Override
	public String toString() {
		return "NullableValueComparator [accessor=" + accessor + "]";
	}

}

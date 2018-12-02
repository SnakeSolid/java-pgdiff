package ru.snake.util.pgdiff.comparator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ChainComparator implements ResultSetComparator {

	private final List<ResultSetComparator> comparators;

	public ChainComparator(List<ResultSetComparator> comparators) {
		this.comparators = comparators;
	}

	@Override
	public Ordering compare(ResultSet lhs, ResultSet rhs) throws SQLException {
		Ordering result = Ordering.EQUALS;

		for (ResultSetComparator comparator : this.comparators) {
			result = comparator.compare(lhs, rhs);

			if (result != Ordering.EQUALS) {
				break;
			}
		}

		return result;
	}

}

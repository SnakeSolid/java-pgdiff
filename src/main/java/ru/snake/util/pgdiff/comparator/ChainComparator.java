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
		Ordering result = Ordering.MOVE_NEXT;

		for (ResultSetComparator comparator : this.comparators) {
			result = comparator.compare(lhs, rhs);

			if (result != Ordering.MOVE_NEXT) {
				break;
			}
		}

		return result;
	}

	@Override
	public String toString() {
		return "ChainComparator [comparators=" + comparators + "]";
	}

}

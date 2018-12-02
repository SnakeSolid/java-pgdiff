package ru.snake.util.pgdiff.comparator;

/**
 * Order or data in data set.
 *
 * @author snake
 *
 */
public enum Ordering {

	LESS, EQUALS, GREATER;

	/**
	 * Convert integer, Java comparison result to {@link Ordering}.
	 *
	 * @param compare
	 *            compare result
	 * @return ordering
	 */
	public static Ordering fromInt(int compare) {
		if (compare < 0) {
			return LESS;
		} else if (compare == 0) {
			return EQUALS;
		} else {
			return GREATER;
		}
	}

}

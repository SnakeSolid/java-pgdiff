package ru.snake.util.pgdiff.comparator;

/**
 * Order or data in data set.
 *
 * @author snake
 *
 */
public enum Ordering {

	TAKE_LEFT, MOVE_NEXT, TAKE_RIGHT;

	/**
	 * Convert integer, Java comparison result to {@link Ordering}.
	 *
	 * @param compare
	 *            compare result
	 * @return ordering
	 */
	public static Ordering fromInt(int compare) {
		if (compare < 0) {
			return TAKE_LEFT;
		} else if (compare == 0) {
			return MOVE_NEXT;
		} else {
			return TAKE_RIGHT;
		}
	}

}

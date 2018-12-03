package ru.snake.util.pgdiff.config;

/**
 * Difference calculation method. Supported following values:
 *
 * <ul>
 * <li>{@link JoinMethod#POSTGRES} - sort query result using order by
 * clause;</li>
 * <li>{@link JoinMethod#HASH} - merge rows using in-memory hash table;</li>
 * <li>{@link JoinMethod#MERGE} - merge rows using merge tree (can be swapped to
 * disk).</li>
 * </ul>
 *
 * @author snake
 *
 */
public enum JoinMethod {

	POSTGRES, HASH, MERGE,

}

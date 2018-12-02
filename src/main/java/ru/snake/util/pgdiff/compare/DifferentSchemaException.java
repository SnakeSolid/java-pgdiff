package ru.snake.util.pgdiff.compare;

/**
 * Thrown to indicate that given table not found in database.
 *
 * @author snake
 *
 */
public class DifferentSchemaException extends Exception {

	private static final long serialVersionUID = 1977602031423883677L;

	private final String tableName1;

	private final String tableName2;

	/**
	 * Create new different table schemas exception.
	 *
	 * @param tableName1
	 *            first table name
	 * @param tableName2
	 *            second table name
	 */
	public DifferentSchemaException(String tableName1, String tableName2) {
		this.tableName1 = tableName1;
		this.tableName2 = tableName2;
	}

	/**
	 * Returns first table name.
	 *
	 * @return table name
	 */
	public String getTableName1() {
		return tableName1;
	}

	/**
	 * Returns second table name.
	 *
	 * @return table name
	 */
	public String getTableName2() {
		return tableName1;
	}

	@Override
	public String getMessage() {
		return String.format("Tables have different schema");
	}

	@Override
	public String toString() {
		return "DifferentSchemaException [tableName1=" + tableName1 + ", tableName2=" + tableName2 + "]";
	}

}

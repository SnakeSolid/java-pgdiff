package ru.snake.util.pgdiff.compare;

/**
 * Thrown to indicate that given table not found in database.
 *
 * @author snake
 *
 */
public class TableNotExistsException extends Exception {

	private static final long serialVersionUID = 6939217232282309619L;

	private final String databaseName;

	private final String tableSchema;

	private final String tableName;

	/**
	 * Create new table not found exception.
	 *
	 * @param databaseName
	 *            database name
	 * @param tableSchema
	 *            schema name
	 * @param tableName
	 *            table name
	 */
	public TableNotExistsException(String databaseName, String tableSchema, String tableName) {
		this.databaseName = databaseName;
		this.tableSchema = tableSchema;
		this.tableName = tableName;
	}

	/**
	 * Returns database name.
	 *
	 * @return database name
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * Returns schema name.
	 *
	 * @return schema name
	 */
	public String getSchemaName() {
		return tableSchema;
	}

	/**
	 * Returns table name.
	 *
	 * @return table name
	 */
	public String getTableName() {
		return tableName;
	}

	@Override
	public String getMessage() {
		return String.format("Table %s.%s does not exists in database %s", this.tableSchema, this.tableName,
				this.databaseName);
	}

	@Override
	public String toString() {
		return "TableNotExistsException [databaseName=" + databaseName + ", tableSchema=" + tableSchema + ", tableName="
				+ tableName + "]";
	}

}

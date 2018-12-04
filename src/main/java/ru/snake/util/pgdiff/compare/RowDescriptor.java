package ru.snake.util.pgdiff.compare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ru.snake.util.pgdiff.query.QueryBuilder;

/**
 * Represents single postgreSQL table.
 *
 * @author snake
 *
 */
public class RowDescriptor {

	private final List<ColumnDescriptor> columns;

	/**
	 * Create new row from columns descriptor list.
	 *
	 * @param columns
	 *            columns
	 */
	public RowDescriptor(List<ColumnDescriptor> columns) {
		this.columns = columns;
	}

	/**
	 * Returns list of all columns.
	 *
	 * @return columns
	 */
	public List<ColumnDescriptor> getColumns() {
		return columns;
	}

	/**
	 * Returns new list of all display columns.
	 *
	 * @return display columns
	 */
	public List<ColumnDescriptor> getDisplayColumns() {
		List<ColumnDescriptor> result = new ArrayList<>();

		for (ColumnDescriptor column : this.columns) {
			if (column.isDisplay()) {
				result.add(column);
			}
		}

		return result;
	}

	/**
	 * Returns new list of all comparable columns.
	 *
	 * @return comparable columns
	 */
	public List<ColumnDescriptor> getCompareColumns() {
		List<ColumnDescriptor> result = new ArrayList<>();

		for (ColumnDescriptor column : this.columns) {
			if (column.isCompare()) {
				result.add(column);
			}
		}

		return result;
	}

	/**
	 * Compare types of all comparable columns. If all types are similar returns
	 * true. If at least one type different returns false.
	 *
	 * @param other
	 *            other row
	 * @return true if rows have the same ordering
	 */
	public boolean equalCompare(RowDescriptor other) {
		List<ColumnDescriptor> thisColumns = this.getCompareColumns();
		List<ColumnDescriptor> otherColumns = other.getCompareColumns();

		if (thisColumns.size() != otherColumns.size()) {
			return false;
		}

		Iterator<ColumnDescriptor> thisIt = thisColumns.iterator();
		Iterator<ColumnDescriptor> otherIt = otherColumns.iterator();

		while (thisIt.hasNext() && otherIt.hasNext()) {
			ColumnDescriptor thisColumn = thisIt.next();
			ColumnDescriptor otherColumn = otherIt.next();

			if (thisColumn.getType() != otherColumn.getType()) {
				return false;
			}
		}

		return true;
	}

	@Override
	public String toString() {
		return "RowDescriptor [columns=" + columns + "]";
	}

	/**
	 * Read row descriptor from given connection information schema table.
	 * Returns new {@link RowDescriptor} instance.
	 *
	 * @param connection
	 *            connection
	 * @param tableSchema
	 *            schema name
	 * @param tableName
	 *            table name
	 * @return row descriptor
	 * @throws TableNotExistsException
	 *             if table not found or has no columns
	 * @throws QueryExecutionException
	 *             if error occurred during query execution
	 */
	public static RowDescriptor fromTable(Connection connection, String tableSchema, String tableName)
			throws TableNotExistsException, QueryExecutionException {
		String databaseName = getDatabaseName(connection);

		checkTableExists(connection, tableSchema, tableName, databaseName);

		List<ColumnDescriptor> columns = getColumnDescriptors(connection, tableSchema, tableName, databaseName);
		Set<String> keyColumns = getPrimaryKeys(connection, databaseName, tableSchema, tableName);

		// Hide all binary columns.
		for (ColumnDescriptor column : columns) {
			ColumnType type = column.getType();

			column.setDisplay(type != ColumnType.BINARY);
		}

		// If table contains other fields, exclude from comparing all primary
		// keys.
		if (columns.size() > keyColumns.size()) {
			for (ColumnDescriptor column : columns) {
				String name = column.getName();

				column.setCompare(!keyColumns.contains(name));
			}
		}

		return new RowDescriptor(columns);
	}

	/**
	 * Returns column descriptors for all columns of given table primary key.
	 *
	 * @param connection
	 *            connection
	 * @param databaseName
	 *            database name
	 * @param tableSchema
	 *            schema name
	 * @param tableName
	 *            table name
	 * @return set of column names
	 * @throws QueryExecutionException
	 *             if error occurred during query execution
	 */
	private static List<ColumnDescriptor> getColumnDescriptors(Connection connection, String tableSchema,
			String tableName, String databaseName) throws QueryExecutionException {
		List<ColumnDescriptor> columns = new ArrayList<>();
		int index = 1;

		try (PreparedStatement statement = connection.prepareStatement(QueryBuilder.tableColumnsQuery())) {
			statement.setString(1, databaseName);
			statement.setString(2, tableSchema);
			statement.setString(3, tableName);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					String columnName = resultSet.getString(1);
					ColumnType columnType = ColumnType.fromString(resultSet.getString(2));
					String nullable = resultSet.getString(3);
					ColumnDescriptor column = new ColumnDescriptor(index, columnName, columnType,
							nullable.contentEquals("YES"));

					columns.add(column);

					index += 1;
				}
			}
		} catch (SQLException e) {
			throw new QueryExecutionException(e);
		}

		return columns;
	}

	/**
	 * Returns set of all column name related to table primary key.
	 *
	 * @param connection
	 *            connection
	 * @param databaseName
	 *            database name
	 * @param tableSchema
	 *            schema name
	 * @param tableName
	 *            table name
	 * @return set of column names
	 * @throws QueryExecutionException
	 *             if error occurred during query execution
	 */
	private static Set<String> getPrimaryKeys(Connection connection, String databaseName, String tableSchema,
			String tableName) throws QueryExecutionException {
		Set<String> columns = new HashSet<>();

		try (PreparedStatement statement = connection.prepareStatement(QueryBuilder.primaryKeysQuery())) {
			statement.setString(1, databaseName);
			statement.setString(2, tableSchema);
			statement.setString(3, tableName);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					String columnName = resultSet.getString(1);

					columns.add(columnName);
				}
			}
		} catch (SQLException e) {
			throw new QueryExecutionException(e);
		}

		return columns;
	}

	private static void checkTableExists(Connection connection, String tableSchema, String tableName,
			String databaseName) throws QueryExecutionException, TableNotExistsException {
		boolean tableExists = false;

		try (PreparedStatement statement = connection.prepareStatement(QueryBuilder.tableExitstQuery())) {
			statement.setString(1, databaseName);
			statement.setString(2, tableSchema);
			statement.setString(3, tableName);

			try (ResultSet resultSet = statement.executeQuery()) {
				tableExists = resultSet.next();
			}
		} catch (SQLException e) {
			throw new QueryExecutionException(e);
		}

		if (!tableExists) {
			throw new TableNotExistsException(databaseName, tableSchema, tableName);
		}
	}

	/**
	 * Returns current connection database name.
	 *
	 * @param connection
	 *            connection
	 * @return database name
	 */
	private static String getDatabaseName(Connection connection) {
		String databaseName;

		try {
			databaseName = connection.getCatalog();
		} catch (SQLException e) {
			// TODO replace with custom exception, this should not happen.
			throw new RuntimeException(e);
		}

		return databaseName;
	}

}

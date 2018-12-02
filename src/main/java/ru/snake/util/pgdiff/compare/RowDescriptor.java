package ru.snake.util.pgdiff.compare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

	@Override
	public int hashCode() {
		return Objects.hash(columns);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		RowDescriptor other = (RowDescriptor) obj;

		return Objects.equals(columns, other.columns);
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
	 * @param fullTableName
	 *            full table name
	 * @return row descriptor
	 * @throws TableNotExistsException
	 *             if table not found or has no columns
	 * @throws QueryExecutionException
	 *             if error occurred during query execution
	 */
	public static RowDescriptor fromTable(Connection connection, String fullTableName)
			throws TableNotExistsException, QueryExecutionException {
		String databaseName;

		try {
			databaseName = connection.getCatalog();
		} catch (SQLException e) {
			// TODO replace with custom exception, this should not happen.
			throw new RuntimeException(e);
		}

		String queryString = QueryBuilder.tableColumnsQuery();
		String tableSchema = getTableSchema(fullTableName, "public");
		String tableName = getTableName(fullTableName);
		List<ColumnDescriptor> columns = new ArrayList<>();

		try (PreparedStatement statement = connection.prepareStatement(queryString)) {
			statement.setString(1, databaseName);
			statement.setString(2, tableSchema);
			statement.setString(3, tableName);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					String columnName = resultSet.getString(1);
					ColumnType columnType = ColumnType.fromString(resultSet.getString(2));
					String nullable = resultSet.getString(3);
					ColumnDescriptor column = new ColumnDescriptor(columnName, columnType,
							nullable.contentEquals("YES"));

					columns.add(column);
				}
			}
		} catch (SQLException e) {
			throw new QueryExecutionException(e);
		}

		if (columns.isEmpty()) {
			throw new TableNotExistsException(databaseName, fullTableName);
		}

		return new RowDescriptor(columns);
	}

	private static String getTableName(String fullTableName) {
		int dotIndex = fullTableName.indexOf('.');

		if (dotIndex != -1) {
			return fullTableName.substring(dotIndex + 1);
		}

		return fullTableName;
	}

	private static String getTableSchema(String fullTableName, String defalutSchema) {
		int dotIndex = fullTableName.indexOf('.');

		if (dotIndex != -1) {
			return fullTableName.substring(0, dotIndex);
		}

		return defalutSchema;
	}

}

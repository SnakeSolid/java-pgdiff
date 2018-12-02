package ru.snake.util.pgdiff.query;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.snake.util.pgdiff.compare.ColumnDescriptor;
import ru.snake.util.pgdiff.compare.RowDescriptor;

/**
 * Query builder factory.
 *
 * @author snake
 *
 */
public class QueryBuilder {

	private static final Logger LOG = LoggerFactory.getLogger(QueryBuilder.class);

	/**
	 * Build query returning all columns of given table. Query has three
	 * parameters:
	 *
	 * <ol>
	 * <li>database name;</li>
	 * <li>table schema name;</li>
	 * <li>table name.</li>
	 * </ol>
	 *
	 * Result set will contain three columns:
	 *
	 * <ol>
	 * <li>(column_name) column name;</li>
	 * <li>(data_type) column data type;</li>
	 * <li>(is_nullable) nullable flag, contains string YES or NO.</li>
	 * </ol>
	 *
	 * @return query string
	 */
	public static String tableColumnsQuery() {
		return readQueryResource("ru/snake/util/pgdiff/query/tableColumnsQuery.sql");
	}

	private static String readQueryResource(String resourcePath) {
		ClassLoader classLoader = QueryBuilder.class.getClassLoader();
		StringBuilder builder = new StringBuilder();

		try (InputStream stream = classLoader.getResourceAsStream(resourcePath)) {
			byte[] buffer = new byte[8192];

			while (true) {
				int n = stream.read(buffer);

				if (n == -1) {
					break;
				} else {
					builder.append(new String(buffer, 0, n));
				}
			}
		} catch (IOException e) {
			LOG.error("Failed to read resource", e);

			throw new RuntimeException(e);
		}

		return builder.toString();
	}

	/**
	 * Create select query returning ordered data-set from the table.
	 *
	 * @param tableName
	 *            table name
	 * @param row
	 *            row structure
	 * @return query string without parameters
	 */
	public static String orderedDatasetQuery(String tableName, RowDescriptor row) {
		// TODO: Move query generation to template.

		StringBuilder builder = new StringBuilder();
		builder.append("SELECT ");

		boolean isFirst = true;

		for (ColumnDescriptor column : row.getColumns()) {
			if (isFirst) {
				isFirst = false;
			} else {
				builder.append(", ");
			}

			builder.append('"');
			builder.append(column.getName());
			builder.append('"');
		}

		builder.append(" FROM \"");
		builder.append(getTableSchema(tableName, "public"));
		builder.append("\".\"");
		builder.append(getTableName(tableName));
		builder.append('"');
		builder.append(" ORDER BY ");

		isFirst = true;

		for (ColumnDescriptor column : row.getColumns()) {
			if (isFirst) {
				isFirst = false;
			} else {
				builder.append(", ");
			}

			builder.append('"');
			builder.append(column.getName());
			builder.append("\" ASC");

			if (column.isNullable()) {
				builder.append(" NULLS LAST");
			}
		}

		return builder.toString();
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

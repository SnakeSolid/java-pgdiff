package ru.snake.util.pgdiff.query;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

}

package ru.snake.util.pgdiff;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

import javax.sql.DataSource;

import org.postgresql.ds.PGSimpleDataSource;

import ru.snake.util.pgdiff.compare.DifferentSchemaException;
import ru.snake.util.pgdiff.compare.QueryExecutionException;
import ru.snake.util.pgdiff.compare.TableComparator;
import ru.snake.util.pgdiff.compare.TableName;
import ru.snake.util.pgdiff.compare.TableNotExistsException;
import ru.snake.util.pgdiff.options.CliOptions;
import ru.snake.util.pgdiff.options.CliOptionsParseException;
import ru.snake.util.pgdiff.options.InvalidPortException;
import ru.snake.util.pgdiff.options.NoParameterException;
import ru.snake.util.pgdiff.options.OptionsParser;

/**
 * Main class contains error processing, validation and comparison loop.
 *
 * @author snake
 *
 */
public class Main {

	private static final int EXIT_SUCCESS = 0;

	private static final int EXIT_CONFIGURATIN_ERROR = 1;

	private static final int EXIT_CONNECTION_ERROR = 2;

	/**
	 * PG Diff entry point.
	 *
	 * @param args
	 *            command-line arguments
	 */
	public static void main(String[] args) {
		int exitCode = new Main().run(args);

		System.exit(exitCode);
	}

	private int run(String[] args) {
		Map<String, String> env = System.getenv();
		OptionsParser parser = new OptionsParser(args, env);
		CliOptions options;

		try {
			options = parser.getOptions();
		} catch (CliOptionsParseException e) {
			parser.printHelp();

			return EXIT_CONFIGURATIN_ERROR;
		} catch (NoParameterException | InvalidPortException e) {
			System.err.println(e.getMessage());

			return EXIT_CONFIGURATIN_ERROR;
		}

		if (options.getTableNames().isEmpty()) {
			System.err.println("No tables to compare.");

			return EXIT_CONFIGURATIN_ERROR;
		}

		DataSource dataSource1 = createDatasource(options::getUser1, options::getPassword1, options::getHost1,
				options::getPort1, options::getDbName1);
		DataSource dataSource2 = createDatasource(options::getUser2, options::getPassword2, options::getHost2,
				options::getPort2, options::getDbName2);

		try (Connection connection1 = dataSource1.getConnection();
				Connection connection2 = dataSource2.getConnection()) {
			for (String tableName : options.getTableNames()) {
				TableName table = new TableName(tableName);
				TableComparator comparator = new TableComparator(connection1, connection2, table);

				try {
					comparator.compare();
				} catch (TableNotExistsException | QueryExecutionException | DifferentSchemaException e) {
					System.err.println(String.format("Failed to compare tables %s and %s: %s", table.getTableName1(),
							table.getTableName1(), e.getMessage()));
				}
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());

			return EXIT_CONNECTION_ERROR;
		}

		return EXIT_SUCCESS;
	}

	/**
	 * Create new PostgreSQL data source using given parameters. It's expected
	 * that all values not null.
	 *
	 * @param user
	 *            user name
	 * @param password
	 *            password
	 * @param host
	 *            server host name
	 * @param port
	 *            port number
	 * @param database
	 *            database name
	 * @return data source
	 */
	private DataSource createDatasource(Supplier<String> user, Supplier<String> password, Supplier<String> host,
			IntSupplier port, Supplier<String> database) {
		PGSimpleDataSource dataSource = new PGSimpleDataSource();
		dataSource.setUser(user.get());
		dataSource.setPassword(password.get());
		dataSource.setServerName(host.get());
		dataSource.setPortNumber(port.getAsInt());
		dataSource.setDatabaseName(database.get());

		return dataSource;
	}

}

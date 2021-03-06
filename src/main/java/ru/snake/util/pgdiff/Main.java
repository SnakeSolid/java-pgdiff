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
import ru.snake.util.pgdiff.compare.TableNames;
import ru.snake.util.pgdiff.compare.TableNotExistsException;
import ru.snake.util.pgdiff.config.ConfigNotFoundException;
import ru.snake.util.pgdiff.config.Configuration;
import ru.snake.util.pgdiff.config.ConfigurationReader;
import ru.snake.util.pgdiff.config.ReadConfigException;
import ru.snake.util.pgdiff.options.CliOptions;
import ru.snake.util.pgdiff.options.CliOptionsParseException;
import ru.snake.util.pgdiff.options.InvalidPortException;
import ru.snake.util.pgdiff.options.NoParameterException;
import ru.snake.util.pgdiff.options.OptionsParser;
import ru.snake.util.pgdiff.writer.DiffWriter;
import ru.snake.util.pgdiff.writer.RowWriter;
import ru.snake.util.pgdiff.writer.SideBySideWriter;
import ru.snake.util.pgdiff.writer.TableWriter;

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

		Configuration config = new Configuration();

		if (options.getConfigFile() != null) {
			try {
				config = ConfigurationReader.read(options.getConfigFile());
			} catch (ConfigNotFoundException | ReadConfigException e) {
				System.err.println(e.getMessage());

				return EXIT_CONFIGURATIN_ERROR;
			}
		}

		DataSource dataSource1 = createDatasource(options::getUser1, options::getPassword1, options::getHost1,
				options::getPort1, options::getDbName1);
		DataSource dataSource2 = createDatasource(options::getUser2, options::getPassword2, options::getHost2,
				options::getPort2, options::getDbName2);

		RowWriter writer = createRowWriter(config);

		try (Connection connection1 = dataSource1.getConnection();
				Connection connection2 = dataSource2.getConnection()) {
			for (String tableName : options.getTableNames()) {
				TableNames table = TableNames.fromString(tableName);
				TableComparator comparator = new TableComparator(connection1, connection2, config, writer, table);

				System.out.println(
						String.format("Comparing tables %s and %s...", table.getTableName1(), table.getTableName2()));

				try {
					comparator.compare();
				} catch (TableNotExistsException e) {
					System.err.println(String.format("Table %s.%s does not exists in database %s", e.getSchemaName(),
							e.getTableName(), e.getDatabaseName()));
				} catch (QueryExecutionException | DifferentSchemaException e) {
					System.err.println(String.format("Failed to compare tables %s and %s: %s", table.getTableName1(),
							table.getTableName2(), e.getMessage()));
				}
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());

			return EXIT_CONNECTION_ERROR;
		}

		return EXIT_SUCCESS;
	}

	/**
	 * Create new row writer according configuration settings.
	 *
	 * @param config
	 *            configuration.
	 * @return row writer
	 */
	private RowWriter createRowWriter(Configuration config) {
		RowWriter writer;

		switch (config.getOutput()) {
		case DIFF:
			writer = new DiffWriter(System.out, config.getDelimiter());
			break;

		case TABLE:
			writer = new TableWriter(System.out, config.getBufferSize());
			break;

		case SIDE_BY_SIDE:
			writer = new SideBySideWriter(System.out, config.getBufferSize());
			break;

		default:
			throw new RuntimeException("Output method unimplemented");
		}

		return writer;
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

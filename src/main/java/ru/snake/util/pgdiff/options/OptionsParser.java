package ru.snake.util.pgdiff.options;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Parse command line arguments and environment variables to determine options.
 *
 * @author snake
 *
 */
public class OptionsParser {

	/**
	 * Short command line options.
	 */

	private static final String SOPT_USER1 = "u";

	private static final String SOPT_USER2 = "U";

	private static final String SOPT_PASSWORD1 = "w";

	private static final String SOPT_PASSWORD2 = "W";

	private static final String SOPT_HOST1 = "h";

	private static final String SOPT_HOST2 = "H";

	private static final String SOPT_PORT1 = "p";

	private static final String SOPT_PORT2 = "P";

	private static final String SOPT_DBNAME1 = "d";

	private static final String SOPT_DBNAME2 = "D";

	private static final String SOPT_CONFIG = "c";

	/**
	 * Environment variables.
	 */

	private static final String ENV_USER1 = "PGDIFF_USER1";

	private static final String ENV_USER2 = "PGDIFF_USER2";

	private static final String ENV_PASSWORD1 = "PGDIFF_PASSWORD1";

	private static final String ENV_PASSWORD2 = "PGDIFF_PASSWORD2";

	private static final String ENV_HOST1 = "PGDIFF_HOST1";

	private static final String ENV_HOST2 = "PGDIFF_HOST2";

	private static final String ENV_PORT1 = "PGDIFF_PORT1";

	private static final String ENV_PORT2 = "PGDIFF_PORT2";

	private static final String ENV_DBNAME1 = "PGDIFF_DBNAME1";

	private static final String ENV_DBNAME2 = "PGDIFF_DBNAME2";

	private static final String ENV_CONFIG = "PGDIFF_CONFIG";

	/**
	 * Default option values.
	 */

	private static final String DEFAULT_HOST = "localhost";

	private static final String DEFAULT_PORT = "5432";

	private final String[] arguments;

	private final Map<String, String> environment;

	private Options options;

	/**
	 * Create new parser from options from arguments and environment.
	 *
	 * @param arguments
	 *            command line arguments
	 * @param environment
	 *            environment variables
	 */
	public OptionsParser(String[] arguments, Map<String, String> environment) {
		this.arguments = arguments;
		this.environment = environment;

		this.options = createOptions();
	}

	/**
	 * Print usage info to STDOUT.
	 */
	public void printHelp() {
		HelpFormatter formatter = new HelpFormatter();

		formatter.printHelp("pgdiff", options);
	}

	/**
	 * Parse and returns application options. Command line options have priority
	 * over environment variables. Default value will be used of all other
	 * option not defined.
	 *
	 * @return application options
	 * @throws NoParameterException
	 *             if required parameters missed
	 * @throws CliOptionsParseException
	 *             if command line option are invalid
	 * @throws InvalidPortException
	 *             if port number invalid
	 */
	public CliOptions getOptions() throws NoParameterException, CliOptionsParseException, InvalidPortException {
		CommandLine commandLine;

		try {
			commandLine = new DefaultParser().parse(this.options, this.arguments);
		} catch (ParseException e) {
			throw new CliOptionsParseException(e);
		}

		String user1 = getRequiredOption(commandLine, SOPT_USER1, ENV_USER1, "Value for user1 must be set");
		String user2 = getRequiredOption(commandLine, SOPT_USER2, ENV_USER2, "Value for user2 must be set");
		String password1 = getRequiredOption(commandLine, SOPT_PASSWORD1, ENV_PASSWORD1,
				"Value for password1 must be set");
		String password2 = getRequiredOption(commandLine, SOPT_PASSWORD2, ENV_PASSWORD2,
				"Value for password2 must be set");
		String host1 = getDefaultOption(commandLine, SOPT_HOST1, ENV_HOST1, DEFAULT_HOST);
		String host2 = getDefaultOption(commandLine, SOPT_HOST2, ENV_HOST2, DEFAULT_HOST);
		short port1 = toShort(getDefaultOption(commandLine, SOPT_PORT1, ENV_PORT1, DEFAULT_PORT));
		short port2 = toShort(getDefaultOption(commandLine, SOPT_PORT2, ENV_PORT2, DEFAULT_PORT));
		String dbname1 = getRequiredOption(commandLine, SOPT_DBNAME1, ENV_DBNAME1, "Value for dbname1 must be set");
		String dbname2 = getRequiredOption(commandLine, SOPT_DBNAME2, ENV_DBNAME2, "Value for dbname2 must be set");
		List<String> tableNames = commandLine.getArgList();
		String config = getOption(commandLine, SOPT_CONFIG, ENV_CONFIG);
		File configFile = Optional.ofNullable(config).map(File::new).orElse(null);

		return new CliOptions.Builder().setUser1(user1)
				.setUser2(user2)
				.setPassword1(password1)
				.setPassword2(password2)
				.setHost1(host1)
				.setHost2(host2)
				.setPort1(port1)
				.setPort2(port2)
				.setDbName1(dbname1)
				.setDbName2(dbname2)
				.setConfigFile(configFile)
				.setTableNames(tableNames)
				.build();
	}

	/**
	 * Try to parse short value from string. If string contains invalid value
	 * throws exception.
	 *
	 * @param value
	 *            value
	 * @return parsed short
	 * @throws InvalidPortException
	 *             if values contains invalid short
	 */
	private short toShort(String value) throws InvalidPortException {
		try {
			return Short.parseShort(value);
		} catch (NumberFormatException e) {
			throw new InvalidPortException(value, e);
		}
	}

	/**
	 * Return parameter value. If given command line option defined, return it's
	 * value. Next if given environment variable defined, return it's value.
	 * Otherwise returns default value.
	 *
	 * @param commandLine
	 *            command line options
	 * @param optName
	 *            option name
	 * @param envVar
	 *            variable name
	 * @return option value or null
	 */
	private String getOption(CommandLine commandLine, String optName, String envVar) {
		String argument = commandLine.getOptionValue(optName);

		if (argument != null) {
			return argument;
		}

		argument = this.environment.get(envVar);

		if (argument != null) {
			return argument;
		}

		return null;
	}

	/**
	 * Return parameter value. If given command line option defined, return it's
	 * value. Next if given environment variable defined, return it's value.
	 * Otherwise returns default value.
	 *
	 * @param commandLine
	 *            command line options
	 * @param optName
	 *            option name
	 * @param envVar
	 *            variable name
	 * @param defaultValue
	 *            default value
	 * @return option value
	 */
	private String getDefaultOption(CommandLine commandLine, String optName, String envVar, String defaultValue) {
		String argument = commandLine.getOptionValue(optName);

		if (argument != null) {
			return argument;
		}

		argument = this.environment.get(envVar);

		if (argument != null) {
			return argument;
		}

		return defaultValue;
	}

	/**
	 * Return parameter value. If given command line option defined, return it's
	 * value. Next if given environment variable defined, return it's value.
	 * Otherwise returns default value.
	 *
	 * @param commandLine
	 *            command line options
	 * @param optName
	 *            option name
	 * @param envVar
	 *            variable name
	 * @param message
	 *            error message
	 * @return option value
	 * @throws NoParameterException
	 *             if required option not defined
	 */
	private String getRequiredOption(CommandLine commandLine, String optName, String envVar, String message)
			throws NoParameterException {
		String argument = commandLine.getOptionValue(optName);

		if (argument != null) {
			return argument;
		}

		argument = this.environment.get(envVar);

		if (argument != null) {
			return argument;
		}

		throw new NoParameterException(optName, envVar, message);
	}

	/**
	 * Create and returns new {@link Options}.
	 *
	 * @return options instance
	 */
	private static Options createOptions() {
		Option user1 = Option.builder(SOPT_USER1)
				.longOpt("user1")
				.argName("USER_1")
				.hasArg()
				.desc("First PostgreSQL user.")
				.build();
		Option user2 = Option.builder(SOPT_USER2)
				.longOpt("user2")
				.argName("USER_2")
				.hasArg()
				.desc("Second PostgreSQL user.")
				.build();
		Option password1 = Option.builder(SOPT_PASSWORD1)
				.longOpt("password1")
				.argName("PASSWORD_1")
				.hasArg()
				.desc("First db password.")
				.build();
		Option password2 = Option.builder(SOPT_PASSWORD2)
				.longOpt("password2")
				.argName("PASSWORD_2")
				.hasArg()
				.desc("Second db password.")
				.build();
		Option host1 = Option.builder(SOPT_HOST1)
				.longOpt("host1")
				.argName("HOST_1")
				.hasArg()
				.desc("First db host. default is localhost.")
				.build();
		Option host2 = Option.builder(SOPT_HOST2)
				.longOpt("host2")
				.argName("HOST_2")
				.hasArg()
				.desc("Second db host. default is localhost.")
				.build();
		Option port1 = Option.builder(SOPT_PORT1)
				.longOpt("port1")
				.argName("PORT_1")
				.hasArg()
				.desc("First db port number. default is 5432.")
				.build();
		Option port2 = Option.builder(SOPT_PORT2)
				.longOpt("port2")
				.argName("PORT_2")
				.hasArg()
				.desc("Second db port number. default is 5432.")
				.build();
		Option dbname1 = Option.builder(SOPT_DBNAME1)
				.longOpt("dbname1")
				.argName("DBNAME_1")
				.hasArg()
				.desc("First db name.")
				.build();
		Option dbname2 = Option.builder(SOPT_DBNAME2)
				.longOpt("dbname2")
				.argName("DBNAME_2")
				.hasArg()
				.desc("Second db name.")
				.build();
		Option config = Option.builder(SOPT_CONFIG)
				.longOpt("config")
				.argName("PATH")
				.hasArg()
				.desc("Path to configuration file.")
				.build();

		Options options = new Options();
		options.addOption(user1);
		options.addOption(user2);
		options.addOption(password1);
		options.addOption(password2);
		options.addOption(host1);
		options.addOption(host2);
		options.addOption(port1);
		options.addOption(port2);
		options.addOption(dbname1);
		options.addOption(dbname2);
		options.addOption(config);

		return options;
	}

	@Override
	public String toString() {
		return "OptionsParser [arguments=" + Arrays.toString(arguments) + ", environment=" + environment + ", options="
				+ options + "]";
	}

}

package ru.snake.util.pgdiff;

import java.util.Map;

import ru.snake.util.pgdiff.options.CliOptions;
import ru.snake.util.pgdiff.options.CliOptionsParseException;
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

	private static final int EXIT_ERROR = -1;

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

			return EXIT_ERROR;
		} catch (NoParameterException e) {
			System.err.println(e.getMessage());

			return EXIT_ERROR;
		}

		System.out.println(options);

		return EXIT_SUCCESS;
	}

}

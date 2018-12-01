package ru.snake.util.pgdiff.options;

import org.apache.commons.cli.ParseException;

/**
 * Thrown to indicate that some invalid command-line option found.
 *
 * @author snake
 *
 */
public class CliOptionsParseException extends Exception {

	private static final long serialVersionUID = 4700057869776053466L;

	/**
	 * Create new instance.
	 *
	 * @param exception
	 *            inner exception
	 */
	public CliOptionsParseException(ParseException exception) {
		super(exception);
	}

	@Override
	public String getMessage() {
		return getCause().getMessage();
	}

	@Override
	public String getLocalizedMessage() {
		return this.getCause().getLocalizedMessage();
	}

	@Override
	public String toString() {
		return "CliOptionsParseException []";
	}

}

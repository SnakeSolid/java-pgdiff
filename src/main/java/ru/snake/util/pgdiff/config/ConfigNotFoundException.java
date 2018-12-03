package ru.snake.util.pgdiff.config;

import java.io.FileNotFoundException;

/**
 * Thrown to indicate that configuration file not found.
 *
 * @author snake
 *
 */
public class ConfigNotFoundException extends Exception {

	private static final long serialVersionUID = 5808218890268877671L;

	/**
	 * configuration file not found.
	 *
	 * @param exception
	 *            inner exception
	 */
	public ConfigNotFoundException(FileNotFoundException exception) {
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
		return "ConfigNotFoundException []";
	}

}

package ru.snake.util.pgdiff.config;

import java.io.IOException;

/**
 * Thrown to indicate that some error happens during reading configuration.
 *
 * @author snake
 *
 */
public class ReadConfigException extends Exception {

	private static final long serialVersionUID = 5954651350287486384L;

	/**
	 * reading configuration error.
	 *
	 * @param exception
	 *            inner exception
	 */
	public ReadConfigException(IOException exception) {
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
		return "ReadConfigException []";
	}

}

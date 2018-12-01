package ru.snake.util.pgdiff.options;

/**
 * Thrown to indicate that required parameter was not defined.
 *
 * @author snake
 *
 */
public class NoParameterException extends Exception {

	private static final long serialVersionUID = 4726127900410474005L;

	private final String optName;

	private final String envVar;

	private final String message;

	/**
	 * Create new no parameter exception.
	 *
	 * @param optName
	 *            option name
	 * @param envVar
	 *            variable name
	 * @param message
	 *            message
	 */
	public NoParameterException(String optName, String envVar, String message) {
		this.optName = optName;
		this.envVar = envVar;
		this.message = message;
	}

	/**
	 * Returns expected command-line option name.
	 *
	 * @return option name
	 */
	public String getOptName() {
		return optName;
	}

	/**
	 * Returns expected environment variable name.
	 *
	 * @return variable name
	 */
	public String getEnvVar() {
		return envVar;
	}

	@Override
	public String getMessage() {
		return String.format("%s, use option %s or environment variable %s", this.message, this.optName, this.envVar);
	}

	@Override
	public String toString() {
		return "NoParameterException [optName=" + optName + ", envVar=" + envVar + ", message=" + message + "]";
	}

}

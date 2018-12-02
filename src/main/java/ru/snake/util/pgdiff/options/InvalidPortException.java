package ru.snake.util.pgdiff.options;

/**
 * Thrown to indicate that given port has invalid value.
 *
 * @author snake
 *
 */
public class InvalidPortException extends Exception {

	private static final long serialVersionUID = 4726127900410474005L;

	private final String value;

	/**
	 * Create new no parameter exception.
	 *
	 * @param value
	 *            port value
	 * @param cause
	 *            cause
	 */
	public InvalidPortException(String value, Throwable cause) {
		super(cause);

		this.value = value;
	}

	/**
	 * Returns actual port value.
	 *
	 * @return port value
	 */
	public String getValue() {
		return value;
	}

	@Override
	public String getMessage() {
		return String.format("Invalid port number %s, expected integer 0..65535.", this.value);
	}

	@Override
	public String toString() {
		return "InvalidPortException [value=" + value + "]";
	}

}

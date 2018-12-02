package ru.snake.util.pgdiff.compare;

/**
 * Thrown to indicate that error occurred during query execution.
 *
 * @author snake
 *
 */
public class QueryExecutionException extends Exception {

	private static final long serialVersionUID = -4441182692686717644L;

	/**
	 * Create new query execution exception.
	 *
	 * @param cause
	 *            error cause
	 */
	public QueryExecutionException(Throwable cause) {
		super(cause);
	}

	@Override
	public String getLocalizedMessage() {
		return this.getCause().getLocalizedMessage();
	}

	@Override
	public String getMessage() {
		return this.getCause().getMessage();
	}

	@Override
	public String toString() {
		return "QueryExecutionException []";
	}

}

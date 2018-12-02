package ru.snake.util.pgdiff.compare;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RowIterator {

	private final ResultSet resultSet;

	private final RowDescriptor row;

	private boolean hasRow;

	private RowIterator(ResultSet resultSet, RowDescriptor row, boolean hasRow) {
		this.resultSet = resultSet;
		this.row = row;
		this.hasRow = hasRow;
	}

	/**
	 * Move to next row in inner result set.
	 *
	 * @throws SQLException
	 *             if inner result set returns error
	 */
	public void next() throws SQLException {
		this.hasRow = this.resultSet.next();
	}

	/**
	 * Returns true if inner result set has current row.
	 *
	 * @return true if row present
	 */
	public boolean hasRow() {
		return hasRow;
	}

	public String getRowString() throws SQLException {
		StringBuilder builder = new StringBuilder();
		boolean isFirst = true;
		int index = 1;

		for (ColumnDescriptor _column : this.row.getColumns()) {
			if (isFirst) {
				isFirst = false;
			} else {
				builder.append(" | ");
			}

			builder.append(this.resultSet.getObject(index));

			index += 1;
		}

		return builder.toString();
	}

	public static RowIterator create(ResultSet resultSet, RowDescriptor row) throws SQLException {
		boolean hasRow = resultSet.next();

		return new RowIterator(resultSet, row, hasRow);
	}

	@Override
	public String toString() {
		return "ResultSetIterator [resultSet=" + resultSet + ", row=" + row + ", hasRow=" + hasRow + "]";
	}

}

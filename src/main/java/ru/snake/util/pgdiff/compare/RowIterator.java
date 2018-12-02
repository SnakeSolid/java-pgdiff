package ru.snake.util.pgdiff.compare;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.snake.util.pgdiff.format.BinaryFormatter;
import ru.snake.util.pgdiff.format.NumberFormatter;
import ru.snake.util.pgdiff.format.StaticFormatter;
import ru.snake.util.pgdiff.format.StringFormatter;
import ru.snake.util.pgdiff.format.ValueFormatter;

public class RowIterator {

	private final ResultSet resultSet;

	private final List<ValueFormatter> formatters;

	private boolean hasRow;

	private RowIterator(ResultSet resultSet, List<ValueFormatter> formatters, boolean hasRow) {
		this.resultSet = resultSet;
		this.formatters = formatters;
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

	/**
	 * Returns string representation of current row.
	 *
	 * @return row representation
	 * @throws SQLException
	 *             if some error occurred during reading values
	 */
	public String getRowString() throws SQLException {
		StringBuilder builder = new StringBuilder();
		boolean isFirst = true;
		int index = 1;

		for (ValueFormatter formatter : this.formatters) {
			if (isFirst) {
				isFirst = false;
			} else {
				builder.append(" | ");
			}

			builder.append(formatter.format(this.resultSet));

			index += 1;
		}

		return builder.toString();
	}

	/**
	 * Create new {@link RowIterator} instance. Before creation move given
	 * {@link ResultSet} to first row.
	 *
	 * @param resultSet
	 *            result set
	 * @param row
	 *            row description
	 * @return row iterator
	 * @throws SQLException
	 *             if result set con't be moved to next row
	 */
	public static RowIterator create(ResultSet resultSet, RowDescriptor row) throws SQLException {
		boolean hasRow = resultSet.next();
		List<ValueFormatter> formatters = new ArrayList<>();

		int index = 1;

		for (ColumnDescriptor column : row.getColumns()) {
			ColumnType columnType = column.getType();

			switch (columnType) {
			case INT8:
			case INT16:
			case INT32:
			case INT64:
			case FLOAT32:
			case FLOAT64:
			case DECIMAL:
				formatters.add(new NumberFormatter(index));
				break;

			case STRING:
				formatters.add(new StringFormatter(index));
				break;

			case BINARY:
				formatters.add(new BinaryFormatter(index));
				break;

			case UNSUPPORTED:
				formatters.add(new StaticFormatter("---"));
				break;
			}
			index += 1;
		}

		return new RowIterator(resultSet, formatters, hasRow);
	}

	@Override
	public String toString() {
		return "RowIterator [resultSet=" + resultSet + ", formatters=" + formatters + ", hasRow=" + hasRow + "]";
	}

}

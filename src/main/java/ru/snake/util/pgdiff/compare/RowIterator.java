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

/**
 * Wrapper over {@link ResultSet} to provide iteration and display methods.
 *
 * @author snake
 *
 */
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

		for (ValueFormatter formatter : this.formatters) {
			if (isFirst) {
				isFirst = false;
			} else {
				builder.append(" | ");
			}

			builder.append(formatter.format(this.resultSet));
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
			if (column.isDisplay()) {
				formatters.add(createFormatter(column, index));
			}

			index += 1;
		}

		return new RowIterator(resultSet, formatters, hasRow);
	}

	/**
	 * Create new formatter for this column.
	 *
	 * @param column
	 *            column descriptor
	 * @param index
	 *            column index
	 * @return column formatter
	 */
	private static ValueFormatter createFormatter(ColumnDescriptor column, int index) {
		ColumnType columnType = column.getType();

		switch (columnType) {
		case INT8:
		case INT16:
		case INT32:
		case INT64:
		case FLOAT32:
		case FLOAT64:
		case DECIMAL:
			return new NumberFormatter(index);

		case STRING:
			return new StringFormatter(index);

		case BINARY:
			return new BinaryFormatter(index);

		case UNSUPPORTED:
			return new StaticFormatter("---");

		default:
			throw new RuntimeException("Columjn type has no corresponding formatter");
		}
	}

	@Override
	public String toString() {
		return "RowIterator [resultSet=" + resultSet + ", formatters=" + formatters + ", hasRow=" + hasRow + "]";
	}

}

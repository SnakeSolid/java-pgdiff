package ru.snake.util.pgdiff.writer;

import java.io.PrintStream;

/**
 * Print like diff like output.
 *
 * @author snake
 *
 */
public class DiffWriter implements RowWriter {

	private static final String TAG_HEADER = "  ";

	private static final String TAG_LEFT = "> ";

	private static final String TAG_RIGHT = "< ";

	private final PrintStream out;

	private final String delimiter;

	/**
	 * Create new instance of diff writer.
	 *
	 * @param out
	 *            output stream
	 * @param delimiter
	 *            delimiter
	 */
	public DiffWriter(PrintStream out, String delimiter) {
		this.out = out;
		this.delimiter = delimiter;
	}

	@Override
	public void setHeader(RowData leftRow, RowData rightRow) {
		writeRow(leftRow, TAG_HEADER);
	}

	@Override
	public void pushLeft(RowData row) {
		writeRow(row, TAG_LEFT);
	}

	@Override
	public void pushSeparator() {
		// Unused.
	}

	@Override
	public void pushRight(RowData row) {
		writeRow(row, TAG_RIGHT);
	}

	/**
	 * Write single row to output stream using given delimiter.
	 *
	 * @param row
	 *            row
	 * @param tag
	 *            tag
	 */
	private void writeRow(RowData row, String tag) {
		StringBuilder builder = new StringBuilder(tag);
		boolean isFirst = true;

		for (String value : row.getValues()) {
			if (isFirst) {
				isFirst = false;
			} else {
				builder.append(this.delimiter);
			}

			builder.append(value);
		}

		out.println(builder.toString());
	}

	@Override
	public void flush() {
		out.flush();
	}

	@Override
	public String toString() {
		return "DiffWriter [out=" + out + ", delimiter=" + delimiter + "]";
	}

}

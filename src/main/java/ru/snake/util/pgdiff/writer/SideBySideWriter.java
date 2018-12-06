package ru.snake.util.pgdiff.writer;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Print output in side by side mode. This mode contains two separate tables for
 * left and right rows.
 *
 * @author snake
 *
 */
public class SideBySideWriter implements RowWriter {

	private static final String TAG_HEADER = " . ";

	private static final String TAG_SEPARATOR = " : ";

	private static final String TAG_SPACE = " : ";

	private static final String DELIMITER = " | ";

	private final PrintStream out;

	private final int bufferSize;

	private final List<DiffRowData> rows;

	private int[] leftWidths;

	private int[] rightWidths;

	private int rightOffset;

	private RowData leftHeader;

	private RowData rightHeader;

	/**
	 * Create new table writer with given buffer size.
	 *
	 * @param out
	 *            output stream
	 * @param bufferSize
	 *            buffer size
	 */
	public SideBySideWriter(PrintStream out, int bufferSize) {
		this.out = out;
		this.bufferSize = bufferSize;

		this.rows = new ArrayList<DiffRowData>(bufferSize);
		this.leftWidths = null;
		this.rightWidths = null;
		this.rightOffset = 0;
		this.leftHeader = null;
		this.rightHeader = null;
	}

	@Override
	public void setHeader(RowData leftRow, RowData rightRow) {
		this.leftHeader = leftRow;
		this.rightHeader = rightRow;
		this.leftWidths = new int[leftRow.size()];
		this.rightWidths = new int[rightRow.size()];
		this.rightOffset = 0;

		updateLeftWidths(leftRow);
		updateRightWidths(rightRow);
	}

	@Override
	public void pushLeft(RowData row) {
		updateLeftWidths(row);

		this.rows.add(new DiffRowData(DiffSide.LEFT, row));

		if (this.rows.size() >= this.bufferSize) {
			flushBuffer();
		}
	}

	@Override
	public void pushSeparator() {
		// Unused in this class.
	}

	@Override
	public void pushRight(RowData row) {
		updateRightWidths(row);

		this.rows.add(new DiffRowData(DiffSide.RIGHT, row));

		if (this.rows.size() >= this.bufferSize) {
			flushBuffer();
		}
	}

	@Override
	public void flush() {
		flushBuffer();

		out.flush();
	}

	/**
	 * Wrote header, if present and all buffered row to output stream.
	 */
	private void flushBuffer() {
		if (this.leftHeader != null && this.rightHeader != null) {
			writeHeaderRow();
			writeSeparator(TAG_SEPARATOR);

			this.leftHeader = null;
			this.rightHeader = null;
		}

		if (!this.rows.isEmpty()) {
			for (DiffRowData diffRow : this.rows) {
				switch (diffRow.getSide()) {
				case LEFT:
					writeLeftRow(diffRow.getRow(), TAG_SPACE);
					break;

				case RIGHT:
					writeRightRow(diffRow.getRow(), TAG_SPACE);
					break;
				}
			}

			this.rows.clear();
		}
	}

	private void writeHeaderRow() {
		StringBuilder builder = new StringBuilder();
		boolean isFirst;

		isFirst = true;

		for (int index = 0; index < this.leftHeader.size(); index += 1) {
			if (isFirst) {
				isFirst = false;
			} else {
				builder.append(DELIMITER);
			}

			String value = this.leftHeader.get(index);
			int width = this.leftWidths[index];

			builder.append(leftPad(value, width, ' '));
		}

		builder.append(TAG_HEADER);

		isFirst = true;

		for (int index = 0; index < this.rightHeader.size(); index += 1) {
			if (isFirst) {
				isFirst = false;
			} else {
				builder.append(DELIMITER);
			}

			String value = this.rightHeader.get(index);
			int width = this.rightWidths[index];

			builder.append(leftPad(value, width, ' '));
		}

		out.println(builder.toString());
	}

	private void writeLeftRow(RowData row, String tag) {
		StringBuilder builder = new StringBuilder();
		boolean isFirst = true;

		for (int index = 0; index < row.size(); index += 1) {
			if (isFirst) {
				isFirst = false;
			} else {
				builder.append(DELIMITER);
			}

			String value = row.get(index);
			int width = this.leftWidths[index];

			builder.append(leftPad(value, width, ' '));
		}

		builder.append(tag);

		out.println(builder.toString());
	}

	private void writeRightRow(RowData row, String tag) {
		StringBuilder builder = new StringBuilder();
		boolean isFirst = true;

		builder.append(leftPad("", this.rightOffset, ' '));
		builder.append(tag);

		for (int index = 0; index < row.size(); index += 1) {
			if (isFirst) {
				isFirst = false;
			} else {
				builder.append(DELIMITER);
			}

			String value = row.get(index);
			int width = this.rightWidths[index];

			builder.append(leftPad(value, width, ' '));
		}

		out.println(builder.toString());
	}

	/**
	 * Print separator between table headers and following data-set.
	 *
	 * @param tag
	 *            separate tables tag
	 */
	private void writeSeparator(String tag) {
		StringBuilder builder = new StringBuilder();
		boolean isFirst;

		isFirst = true;

		for (Integer width : this.leftWidths) {
			if (isFirst) {
				isFirst = false;
			} else {
				builder.append("-+-");
			}

			builder.append(leftPad("", width, '-'));
		}

		builder.append(tag);

		isFirst = true;

		for (Integer width : this.rightWidths) {
			if (isFirst) {
				isFirst = false;
			} else {
				builder.append("-+-");
			}

			builder.append(leftPad("", width, '-'));
		}

		out.println(builder.toString());
	}

	/**
	 * Right pad string up to given width using characcter ch.
	 *
	 * @param value
	 *            value
	 * @param width
	 *            required width
	 * @param ch
	 *            filler
	 * @return padded string
	 */
	private Object leftPad(String value, int width, char ch) {
		if (value.length() >= width) {
			return value;
		}

		StringBuilder builder = new StringBuilder(width);

		for (int index = value.length(); index < width; index += 1) {
			builder.append(ch);
		}

		builder.append(value);

		return builder.toString();
	}

	/**
	 * Update widths of left table to fit all values of given row.
	 *
	 * @param row
	 *            row
	 */
	private void updateLeftWidths(RowData row) {
		boolean updated = false;

		for (int index = 0; index < row.size(); index += 1) {
			int valueLength = row.get(index).length();

			if (this.leftWidths[index] < valueLength) {
				this.leftWidths[index] = valueLength;

				updated = true;
			}
		}

		if (updated) {
			this.rightOffset = 0;

			if (leftWidths.length > 1) {
				this.rightOffset += DELIMITER.length() * (leftWidths.length - 1);
			}

			for (int width : this.leftWidths) {
				this.rightOffset += width;
			}
		}
	}

	/**
	 * Update widths of right table to fit all values of given row.
	 *
	 * @param row
	 *            row
	 */
	private void updateRightWidths(RowData row) {
		for (int index = 0; index < row.size(); index += 1) {
			int valueLength = row.get(index).length();

			if (this.rightWidths[index] < valueLength) {
				this.rightWidths[index] = valueLength;
			}
		}
	}

	@Override
	public String toString() {
		return "SideBySideWriter [out=" + out + ", bufferSize=" + bufferSize + ", rows=" + rows + ", leftWidths="
				+ Arrays.toString(leftWidths) + ", rightWidths=" + Arrays.toString(rightWidths) + ", leftHeader="
				+ leftHeader + ", rightHeader=" + rightHeader + "]";
	}

}

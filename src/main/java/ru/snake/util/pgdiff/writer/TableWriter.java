package ru.snake.util.pgdiff.writer;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableWriter implements RowWriter {

	private static final String TAG_HEADER = "   ";

	private static final String TAG_SEPARATOR = " - ";

	private static final String TAG_LEFT = "-> ";

	private static final String TAG_RIGHT = "<- ";

	private static final int UNUSED = -1;

	private final PrintStream out;

	private final int bufferSize;

	private final List<DiffRowData> rows;

	private int[] leftMapping;

	private int[] rightMapping;

	private int[] widths;

	private RowData header;

	/**
	 * Create new table writer with given buffer size.
	 *
	 * @param out
	 *            output stream
	 * @param bufferSize
	 *            buffer size
	 */
	public TableWriter(PrintStream out, int bufferSize) {
		this.out = out;
		this.bufferSize = bufferSize;

		this.rows = new ArrayList<DiffRowData>(bufferSize);
		this.leftMapping = null;
		this.rightMapping = null;
		this.widths = null;
		this.header = null;
	}

	@Override
	public void setHeader(RowData leftRow, RowData rightRow) {
		Map<String, Integer> columnIndexes = new HashMap<>();
		RowData headerData = new RowData();
		int headerIndex = 0;

		// Collect all left columns to common header
		for (String value : leftRow.getValues()) {
			Integer columnIndex = columnIndexes.get(value);

			if (columnIndex == null) {
				headerData.push(value);
				columnIndex = headerIndex;
				headerIndex += 1;

				columnIndexes.put(value, columnIndex);
			}
		}

		// Collect all right columns to common header
		for (String value : rightRow.getValues()) {
			Integer columnIndex = columnIndexes.get(value);

			if (columnIndex == null) {
				headerData.push(value);
				columnIndex = headerIndex;
				headerIndex += 1;

				columnIndexes.put(value, columnIndex);
			}
		}

		// Initialize mappings to point from common columns to particular data
		// set.

		this.leftMapping = new int[columnIndexes.size()];
		this.rightMapping = new int[columnIndexes.size()];

		Arrays.fill(this.leftMapping, UNUSED);
		Arrays.fill(this.rightMapping, UNUSED);

		int leftIndex = 0;

		for (String value : leftRow.getValues()) {
			int columnIndex = columnIndexes.get(value);

			this.leftMapping[columnIndex] = leftIndex;

			leftIndex += 1;
		}

		int rightIndex = 0;

		for (String value : rightRow.getValues()) {
			int columnIndex = columnIndexes.get(value);

			this.rightMapping[columnIndex] = rightIndex;

			rightIndex += 1;
		}

		this.header = headerData;
		this.widths = new int[headerData.size()];

		updateWidths(headerData);
	}

	@Override
	public void pushLeft(RowData row) {
		RowData rowData = new RowData();

		for (int index : this.leftMapping) {
			if (index == UNUSED) {
				rowData.push("");
			} else {
				rowData.push(row.get(index));
			}
		}

		updateWidths(rowData);

		this.rows.add(new DiffRowData(DiffSide.LEFT, rowData));

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
		RowData rowData = new RowData();

		for (int index : this.rightMapping) {
			if (index == UNUSED) {
				rowData.push("");
			} else {
				rowData.push(row.get(index));
			}
		}

		updateWidths(rowData);

		this.rows.add(new DiffRowData(DiffSide.RIGHT, rowData));

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
		if (this.header != null) {
			writeRow(header, TAG_HEADER);
			writeSeparator(TAG_SEPARATOR);

			this.header = null;
		}

		if (!this.rows.isEmpty()) {
			for (DiffRowData diffRow : this.rows) {
				switch (diffRow.getSide()) {
				case LEFT:
					writeRow(diffRow.getRow(), TAG_LEFT);
					break;

				case RIGHT:
					writeRow(diffRow.getRow(), TAG_RIGHT);
					break;
				}
			}

			this.rows.clear();
		}
	}

	private void writeSeparator(String tag) {
		StringBuilder builder = new StringBuilder(tag);
		boolean isFirst = true;

		for (Integer width : this.widths) {
			if (isFirst) {
				isFirst = false;
			} else {
				builder.append("-+-");
			}

			builder.append(leftPad("", width, '-'));
		}

		out.println(builder.toString());
	}

	private void writeRow(RowData row, String tag) {
		StringBuilder builder = new StringBuilder(tag);
		boolean isFirst = true;

		for (int index = 0; index < row.size(); index += 1) {
			if (isFirst) {
				isFirst = false;
			} else {
				builder.append(" | ");
			}

			String value = row.get(index);
			int width = this.widths[index];

			builder.append(leftPad(value, width, ' '));
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
	 * Update internal column widths to fit all values of given row.
	 *
	 * @param row
	 *            row
	 */
	private void updateWidths(RowData row) {
		for (int index = 0; index < row.size(); index += 1) {
			int valueLength = row.get(index).length();

			if (this.widths[index] < valueLength) {
				this.widths[index] = valueLength;
			}
		}
	}

	@Override
	public String toString() {
		return "TableWriter [out=" + out + ", bufferSize=" + bufferSize + ", rows=" + rows + ", widths="
				+ Arrays.toString(widths) + ", header=" + header + "]";
	}

}

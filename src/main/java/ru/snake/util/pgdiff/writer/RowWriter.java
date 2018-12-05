package ru.snake.util.pgdiff.writer;

/**
 * Common interface for all output writers.
 *
 * @author snake
 *
 */
public interface RowWriter {

	/**
	 * Set headers for both tables.
	 *
	 * @param leftRow
	 *            left header
	 * @param rightRow
	 *            right header
	 */
	public void setHeader(RowData leftRow, RowData rightRow);

	/**
	 * Push next row data for left result set.
	 *
	 * @param row
	 *            row data
	 */
	public void pushLeft(RowData row);

	/**
	 * Push row separator.
	 */
	public void pushSeparator();

	/**
	 * Push next row data for right result set.
	 *
	 * @param row
	 *            row data
	 */
	public void pushRight(RowData row);

	/**
	 * Flush output and clear all internal buffers.
	 */
	public void flush();

}

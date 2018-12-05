package ru.snake.util.pgdiff.writer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RowData {

	private final List<String> values;

	/**
	 * Create empty formatted row.
	 */
	public RowData() {
		this.values = new ArrayList<>();
	}

	/**
	 * Create empty formatted row with given capacity.
	 *
	 * @param capacity
	 *            capacity
	 */
	public RowData(int capacity) {
		this.values = new ArrayList<>(capacity);
	}

	/**
	 * Returns number of values in row.
	 *
	 * @return row size
	 */
	public int size() {
		return this.values.size();
	}

	/**
	 * Add next value to formatted row.
	 *
	 * @param value
	 *            value
	 */
	public void push(String value) {
		this.values.add(value);
	}

	/**
	 * Return values for given column.
	 *
	 * @param index
	 *            index
	 * @return value at index
	 */
	public String get(int index) {
		return this.values.get(index);
	}

	/**
	 * Returns all values.
	 *
	 * @return values
	 */
	public List<String> getValues() {
		return Collections.unmodifiableList(values);
	}

	@Override
	public String toString() {
		return "RowData [values=" + values + "]";
	}

}

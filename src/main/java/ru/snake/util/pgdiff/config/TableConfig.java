package ru.snake.util.pgdiff.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Configuration option for single table. Contains two main options - display
 * fields and compare fields.
 *
 * Display fields will be shown in output if row marked as different.
 *
 * Compare fields will be used to compare two rows of result set.
 *
 * @author snake
 *
 */
public class TableConfig {

	private List<String> display;

	private List<String> compare;

	/**
	 * Create empty table configuration instance.
	 */
	public TableConfig() {
		this.display = new ArrayList<>();
		this.compare = new ArrayList<>();
	}

	/**
	 * Returns list of filed names to display for this table.
	 *
	 * @return list of filed names
	 */
	public List<String> getDisplay() {
		return Collections.unmodifiableList(display);
	}

	/**
	 * Returns list of fields names to compare for this table.
	 *
	 * @return list of filed names
	 */
	public List<String> getCompare() {
		return Collections.unmodifiableList(compare);
	}

	@Override
	public String toString() {
		return "TableConfig [display=" + display + ", compare=" + compare + "]";
	}

}

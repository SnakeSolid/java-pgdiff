package ru.snake.util.pgdiff.config;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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

	private Set<String> display;

	private Set<String> compare;

	/**
	 * Create empty table configuration instance.
	 */
	public TableConfig() {
		this.display = new HashSet<>();
		this.compare = new HashSet<>();
	}

	/**
	 * Returns set of filed names to display for this table.
	 *
	 * @return set of filed names
	 */
	public Set<String> getDisplay() {
		return Collections.unmodifiableSet(display);
	}

	/**
	 * Returns set of fields names to compare for this table.
	 *
	 * @return set of filed names
	 */
	public Set<String> getCompare() {
		return Collections.unmodifiableSet(compare);
	}

	@Override
	public String toString() {
		return "TableConfig [display=" + display + ", compare=" + compare + "]";
	}

}

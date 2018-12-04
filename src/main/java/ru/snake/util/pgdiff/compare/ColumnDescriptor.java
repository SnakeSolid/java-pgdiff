package ru.snake.util.pgdiff.compare;

/**
 * Represents single postgreSQL column.
 *
 * Note: methods {@link Object#hashCode()} and {@link Object#equals(Object)} of
 * this class do not use fields {@link ColumnDescriptor#display} and
 * {@link ColumnDescriptor#compare}.
 *
 * @author snake
 *
 */
public class ColumnDescriptor {

	private final int index;

	private final String name;

	private final ColumnType type;

	private final boolean nullable;

	private boolean display;

	private boolean compare;

	/**
	 * Create initialize instance of {@link ColumnDescriptor}.
	 *
	 * @param index
	 *            column index ion result set
	 * @param name
	 *            column name
	 * @param type
	 *            column type
	 * @param nullable
	 *            is column nullable
	 */
	public ColumnDescriptor(int index, String name, ColumnType type, boolean nullable) {
		this.index = index;
		this.name = name;
		this.type = type;
		this.nullable = nullable;

		this.display = true;
		this.compare = true;
	}

	/**
	 * Returns actual column index in result set.
	 *
	 * @return column index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Returns actual PostgreSQL column name.
	 *
	 * @return column name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns mapped column type. If type unsupported should be
	 * {@link ColumnType#UNSUPPORTED}.
	 *
	 * @return column type
	 */
	public ColumnType getType() {
		return type;
	}

	/**
	 * Returns true if corresponding PostgreSQL column is nullable.
	 *
	 * @return true, if column nullable
	 */
	public boolean isNullable() {
		return nullable;
	}

	/**
	 * Returns internal display property.
	 *
	 * @return display value
	 */
	public boolean isDisplay() {
		return display;
	}

	/**
	 * Set new display value.
	 *
	 * @param display
	 *            display value
	 */
	public void setDisplay(boolean display) {
		this.display = display;
	}

	/**
	 * Returns internal compare property.
	 *
	 * @return compare value
	 */
	public boolean isCompare() {
		return compare;
	}

	/**
	 * Set new compare value.
	 *
	 * @param compare
	 *            compare value
	 */
	public void setCompare(boolean compare) {
		this.compare = compare;
	}

	@Override
	public String toString() {
		return "ColumnDescriptor [index=" + index + ", name=" + name + ", type=" + type + ", nullable=" + nullable
				+ ", display=" + display + ", compare=" + compare + "]";
	}

}

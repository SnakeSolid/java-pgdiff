package ru.snake.util.pgdiff.compare;

import java.util.Objects;

/**
 * Represents single postgreSQL column.
 *
 * @author snake
 *
 */
public class ColumnDescriptor {

	private final String name;

	private final ColumnType type;

	private final boolean nullable;

	public ColumnDescriptor(String name, ColumnType type, boolean nullable) {
		this.name = name;
		this.type = type;
		this.nullable = nullable;
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

	@Override
	public int hashCode() {
		return Objects.hash(name, nullable, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		ColumnDescriptor other = (ColumnDescriptor) obj;

		return Objects.equals(name, other.name) && nullable == other.nullable && type == other.type;
	}

	@Override
	public String toString() {
		return "ColumnDescriptor [name=" + name + ", type=" + type + ", nullable=" + nullable + "]";
	}

}

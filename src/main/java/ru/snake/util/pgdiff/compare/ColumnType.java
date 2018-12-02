package ru.snake.util.pgdiff.compare;

/**
 * Mapping PostgreSQL column types to comparable types.
 *
 * @author snake
 *
 */
public enum ColumnType {

	INT8, INT16, INT32, INT64, FLOAT32, FLOAT64, DECIMAL, STRING, BINARY, UNSUPPORTED,;

	/**
	 * Convert string type name to column type.
	 *
	 * @param value
	 *            string name
	 * @return column type
	 */
	public static ColumnType fromString(String value) {
		switch (value) {
		case "smallint":
		case "smallserial":
			return INT16;

		case "integer":
		case "serial":
			return INT32;

		case "bigint":
		case "bigserial":
			return INT64;

		case "real":
			return FLOAT32;

		case "double precision":
			return FLOAT64;

		case "decimal":
		case "numeric":
		case "money":
			return DECIMAL;

		case "character varying":
		case "varchar":
		case "character":
		case "char":
		case "text":
			return STRING;

		case "bytea":
			return BINARY;

		default:
			return UNSUPPORTED;
		}
	}

}

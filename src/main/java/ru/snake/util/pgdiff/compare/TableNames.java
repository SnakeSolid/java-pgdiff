package ru.snake.util.pgdiff.compare;

/**
 * Parse table definition. And store it as three different parts: table name for
 * first connection, table name for second connection and options. Table names
 * separated by comma, option separated by colon. Expected format is -
 * name1[,name2][:options].
 *
 * <ul>
 * <li>If only one name defined - both table name will be the same;</li>
 * <li>If both names defined - first table name will name1, second table name
 * will be name2;</li>
 * <li>If only one name and options defined - both table name will be the same,
 * options will be options;</li>
 * <li>If everything defined - first table name will name1, second table name
 * will be name2, options will be options;</li>
 * </ul>
 *
 * @author snake
 *
 */
public class TableNames {

	private static final String DEFAULT_SCHEMA_NAME = "public";

	private final String fullTableName1;

	private final String fullTableName2;

	private final String tableName1;

	private final String tableName2;

	private final String schemaName1;

	private final String schemaName2;

	private final String options;

	private TableNames(String fullTableName1, String fullTableName2, String tableName1, String tableName2,
			String schemaName1, String schemaName2, String options) {
		super();
		this.fullTableName1 = fullTableName1;
		this.fullTableName2 = fullTableName2;
		this.tableName1 = tableName1;
		this.tableName2 = tableName2;
		this.schemaName1 = schemaName1;
		this.schemaName2 = schemaName2;
		this.options = options;
	}

	/**
	 * Returns parsed full name of first table.
	 *
	 * @return first table name
	 */
	public String getFullTableName1() {
		return fullTableName1;
	}

	/**
	 * Returns parsed full name of second table.
	 *
	 * @return second table name
	 */
	public String getFullTableName2() {
		return fullTableName2;
	}

	/**
	 * Returns parsed name of first table.
	 *
	 * @return first table name
	 */
	public String getTableName1() {
		return tableName1;
	}

	/**
	 * Returns parsed name of second table.
	 *
	 * @return second table name
	 */
	public String getTableName2() {
		return tableName2;
	}

	/**
	 * Returns parsed schema name of first table.
	 *
	 * @return first schema name
	 */
	public String getSchemaName1() {
		return schemaName1;
	}

	/**
	 * Returns parsed schema name of second table.
	 *
	 * @return second schema name
	 */
	public String getSchemaName2() {
		return schemaName2;
	}

	/**
	 * Returns table options.
	 *
	 * @return options
	 */
	public String getOptions() {
		return options;
	}

	@Override
	public String toString() {
		return "TableNames [fullTableName1=" + fullTableName1 + ", fullTableName2=" + fullTableName2 + ", tableName1="
				+ tableName1 + ", tableName2=" + tableName2 + ", schemaName1=" + schemaName1 + ", schemaName2="
				+ schemaName2 + ", options=" + options + "]";
	}

	/**
	 * Parse table definition from argument.
	 *
	 * @param tableDef
	 *            table definition
	 * @return new tables definition
	 */
	public static TableNames fromString(String tableDef) {
		int commaIndex = tableDef.indexOf(',');
		int colonIndex = tableDef.indexOf(':');
		String fullTableName1;
		String fullTableName2;
		String options;

		if (commaIndex != -1 && colonIndex != -1) {
			fullTableName1 = tableDef.substring(0, commaIndex);
			fullTableName2 = tableDef.substring(commaIndex + 1, colonIndex);
			options = tableDef.substring(colonIndex + 1);
		} else if (commaIndex != -1) {
			fullTableName1 = tableDef.substring(0, commaIndex);
			fullTableName2 = tableDef.substring(commaIndex + 1);
			options = "";
		} else if (colonIndex != -1) {
			fullTableName1 = tableDef.substring(0, colonIndex);
			fullTableName2 = tableDef.substring(0, colonIndex);
			options = tableDef.substring(colonIndex + 1);
		} else {
			fullTableName1 = tableDef;
			fullTableName2 = tableDef;
			options = "";
		}

		String tableName1 = getTableName(fullTableName1);
		String tableName2 = getTableName(fullTableName2);
		String tableSchema1 = getTableSchema(fullTableName1, DEFAULT_SCHEMA_NAME);
		String tableSchema2 = getTableSchema(fullTableName2, DEFAULT_SCHEMA_NAME);

		return new TableNames(fullTableName1, fullTableName2, tableName1, tableName2, tableSchema1, tableSchema2,
				options);
	}

	/**
	 * Returns table name from (schema dot table) pair. If dot not found in
	 * name, returns source string.
	 *
	 * @param fullTableName
	 *            full table name
	 * @return table name
	 */
	private static String getTableName(String fullTableName) {
		int dotIndex = fullTableName.indexOf('.');

		if (dotIndex != -1) {
			return fullTableName.substring(dotIndex + 1);
		}

		return fullTableName;
	}

	/**
	 * Returns schema name from (schema dot table) pair. If dot not found in
	 * name, returns default value.
	 *
	 * @param fullTableName
	 *            full table name
	 * @param defalutSchema
	 *            default schema name
	 * @return schema name
	 */
	private static String getTableSchema(String fullTableName, String defalutSchema) {
		int dotIndex = fullTableName.indexOf('.');

		if (dotIndex != -1) {
			return fullTableName.substring(0, dotIndex);
		}

		return defalutSchema;
	}

}

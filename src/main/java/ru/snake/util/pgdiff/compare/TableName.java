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
public class TableName {

	private final String tableName1;

	private final String tableName2;

	private final String options;

	/**
	 * Parse table definition from argument.
	 *
	 * @param tableDef
	 *            table definition
	 */
	public TableName(String tableDef) {
		int commaIndex = tableDef.indexOf(',');
		int colonIndex = tableDef.indexOf(':');

		if (commaIndex != -1 && colonIndex != -1) {
			this.tableName1 = tableDef.substring(0, commaIndex);
			this.tableName2 = tableDef.substring(commaIndex + 1, colonIndex);
			this.options = tableDef.substring(colonIndex + 1);
		} else if (commaIndex != -1) {
			this.tableName1 = tableDef.substring(0, commaIndex);
			this.tableName2 = tableDef.substring(commaIndex + 1);
			this.options = "";
		} else if (colonIndex != -1) {
			this.tableName1 = tableDef.substring(0, colonIndex);
			this.tableName2 = tableDef.substring(0, colonIndex);
			this.options = tableDef.substring(colonIndex + 1);
		} else {
			this.tableName1 = tableDef;
			this.tableName2 = tableDef;
			this.options = "";
		}
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
	 * Returns table options.
	 *
	 * @return options
	 */
	public String getOptions() {
		return options;
	}

	@Override
	public String toString() {
		return "TableName [tableName1=" + tableName1 + ", tableName2=" + tableName2 + ", options=" + options + "]";
	}

}

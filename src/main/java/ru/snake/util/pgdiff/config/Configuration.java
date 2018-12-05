package ru.snake.util.pgdiff.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration setting. Contains join method and table configuration.
 *
 * @author snake
 *
 */
public class Configuration {

	private JoinMethod join;

	private OutputType outputType;

	private String delimiter;

	private int bufferSize;

	private Map<String, TableConfig> tables;

	/**
	 * Create empty configuration instance.
	 */
	public Configuration() {
		this.join = JoinMethod.POSTGRES;
		this.tables = new HashMap<>();
		this.outputType = OutputType.TABLE;
		this.delimiter = " | ";
		this.bufferSize = 1_000;
	}

	/**
	 * Returns join method.
	 *
	 * @return join method
	 */
	public JoinMethod getJoin() {
		return join;
	}

	/**
	 * Returns output type.
	 *
	 * @return output type
	 */
	public OutputType getOutput() {
		return outputType;
	}

	/**
	 * Returns delimiter string for {@link OutputType#DIFF} output type.
	 *
	 * @return delimiter string
	 */
	public String getDelimiter() {
		return delimiter;
	}

	/**
	 * Returns buffer size for {@link OutputType#TABLE} output type.
	 *
	 * @return vuffer size
	 */
	public int getBufferSize() {
		return bufferSize;
	}

	/**
	 * Returns table configuration for given table name. If table was not
	 * configured returns null.
	 *
	 * First try to find configuration for full table name (schema dot table).
	 * If configuration not found tries to find configuration for table name
	 * only.
	 *
	 * @param schemaName
	 *            schema name
	 * @param tableName
	 *            table name
	 * @return table configuration
	 */
	public TableConfig getTableConfig(String schemaName, String tableName) {
		String fullTableName = schemaName + "." + tableName;
		TableConfig result = this.tables.get(fullTableName);

		if (result != null) {
			return result;
		}

		return this.tables.get(tableName);
	}

	@Override
	public String toString() {
		return "Configuration [join=" + join + ", tables=" + tables + "]";
	}

}

package ru.snake.util.pgdiff.compare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import ru.snake.util.pgdiff.comparator.ComparatorFactory;
import ru.snake.util.pgdiff.comparator.ResultSetComparator;
import ru.snake.util.pgdiff.config.Configuration;
import ru.snake.util.pgdiff.config.TableConfig;
import ru.snake.util.pgdiff.query.QueryBuilder;
import ru.snake.util.pgdiff.writer.RowData;
import ru.snake.util.pgdiff.writer.RowWriter;

/**
 * Table comparator based on merging two sorted result sets. Comparator read
 * both tables simultaneously comparing every next row with other table. If rows
 * similar - move both result sets to next row. Otherwise show smaller row and
 * move corresponding data-set forward.
 *
 * @author snake
 *
 */
public class TableComparator {

	private final Connection connection1;

	private final Connection connection2;

	private final Configuration config;

	private final RowWriter writer;

	private final TableNames tables;

	/**
	 * Create new instance of table comparator.
	 *
	 * @param connection1
	 *            first connection
	 * @param connection2
	 *            second connection
	 * @param config
	 *            configuration
	 * @param writer
	 *            row output writer
	 * @param table
	 *            tables description
	 */
	public TableComparator(Connection connection1, Connection connection2, Configuration config, RowWriter writer,
			TableNames table) {
		this.connection1 = connection1;
		this.connection2 = connection2;
		this.config = config;
		this.writer = writer;
		this.tables = table;
	}

	/**
	 * Compare the tables.
	 *
	 * @throws TableNotExistsException
	 *             if table does not exists in database
	 * @throws QueryExecutionException
	 *             if query execution error occurred
	 * @throws DifferentSchemaException
	 *             if schema of two tables are different
	 */
	public void compare() throws TableNotExistsException, QueryExecutionException, DifferentSchemaException {
		RowDescriptor leftRow = createRowDescriptor(this.connection1, this.tables.getSchemaName1(),
				this.tables.getTableName1());
		RowDescriptor rightRow = createRowDescriptor(this.connection2, this.tables.getSchemaName2(),
				this.tables.getTableName2());

		if (!leftRow.equalCompare(rightRow)) {
			throw new DifferentSchemaException(this.tables.getFullTableName1(), this.tables.getFullTableName2());
		}

		this.writer.setHeader(getRowHeader(leftRow), getRowHeader(rightRow));

		// TODO: create comparator valid for both rows, according column
		// indexes.
		ResultSetComparator comparator = ComparatorFactory.createRowComparator(leftRow);
		String leftQueryString = QueryBuilder.orderedDatasetQuery(this.tables.getSchemaName1(),
				this.tables.getTableName1(), leftRow);
		String rightQueryString = QueryBuilder.orderedDatasetQuery(this.tables.getSchemaName2(),
				this.tables.getTableName2(), rightRow);

		try (PreparedStatement leftStatement = this.connection1.prepareStatement(leftQueryString);
				PreparedStatement rightStatement = this.connection2.prepareStatement(rightQueryString);
				ResultSet leftResultSet = leftStatement.executeQuery();
				ResultSet rightResultSet = rightStatement.executeQuery();) {
			RowIterator leftIt = RowIterator.create(leftResultSet, leftRow);
			RowIterator rightIt = RowIterator.create(rightResultSet, rightRow);

			while (leftIt.hasRow() && rightIt.hasRow()) {
				switch (comparator.compare(leftResultSet, rightResultSet)) {
				case TAKE_LEFT:
					this.writer.pushLeft(leftIt.getRow());

					leftIt.next();
					break;

				case MOVE_NEXT:
					this.writer.pushSeparator();

					leftIt.next();
					rightIt.next();
					break;

				case TAKE_RIGHT:
					this.writer.pushRight(rightIt.getRow());

					rightIt.next();
					break;
				}
			}

			// Show remaining row from first result set
			while (leftIt.hasRow()) {
				this.writer.pushLeft(leftIt.getRow());

				leftIt.next();
			}

			// Show remaining row from second result set
			while (rightIt.hasRow()) {
				this.writer.pushRight(rightIt.getRow());

				rightIt.next();
			}
		} catch (SQLException e) {
			throw new QueryExecutionException(e);
		}

		this.writer.flush();
	}

	/**
	 * Create and return new row data with all displayable column names.
	 *
	 * @param row
	 *            row descriptor
	 * @return row header
	 */
	private RowData getRowHeader(RowDescriptor row) {
		List<ColumnDescriptor> columns = row.getDisplayColumns();
		RowData result = new RowData(columns.size());

		for (ColumnDescriptor column : columns) {
			result.push(column.getName());
		}

		return result;
	}

	private RowDescriptor createRowDescriptor(Connection connection, String schemaName, String tableName)
			throws TableNotExistsException, QueryExecutionException {
		RowDescriptor rowDescriptor = RowDescriptor.fromTable(connection, schemaName, tableName);
		TableConfig tableConfig = config.getTableConfig(schemaName, tableName);

		if (tableConfig != null) {
			Set<String> displayNames = tableConfig.getDisplay();
			Set<String> compareNames = tableConfig.getCompare();

			for (ColumnDescriptor column : rowDescriptor.getColumns()) {
				String name = column.getName();

				column.setDisplay(displayNames.contains(name));
				column.setCompare(compareNames.contains(name));
			}
		}

		return rowDescriptor;
	}

	@Override
	public String toString() {
		return "TableComparator [connection1=" + connection1 + ", connection2=" + connection2 + ", config=" + config
				+ ", writer=" + writer + ", tables=" + tables + "]";
	}

}

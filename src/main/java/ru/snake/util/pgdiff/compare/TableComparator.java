package ru.snake.util.pgdiff.compare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ru.snake.util.pgdiff.comparator.ComparatorFactory;
import ru.snake.util.pgdiff.comparator.ResultSetComparator;
import ru.snake.util.pgdiff.query.QueryBuilder;

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

	private final String tableName1;

	private final String tableName2;

	private final String options;

	/**
	 * Create new instance of table comparator.
	 *
	 * @param connection1
	 *            first connection
	 * @param connection2
	 *            second connection
	 * @param table
	 *            table description
	 */
	public TableComparator(Connection connection1, Connection connection2, TableName table) {
		this.connection1 = connection1;
		this.connection2 = connection2;
		this.tableName1 = table.getTableName1();
		this.tableName2 = table.getTableName2();
		this.options = table.getOptions();
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
		RowDescriptor row = RowDescriptor.fromTable(this.connection1, this.tableName1);
		RowDescriptor otherRow = RowDescriptor.fromTable(this.connection2, this.tableName2);

		if (!row.equals(otherRow)) {
			throw new DifferentSchemaException(this.tableName1, this.tableName2);
		}

		ResultSetComparator comparator = ComparatorFactory.createRowComparator(row);
		String queryString1 = QueryBuilder.orderedDatasetQuery(this.tableName1, row);
		String queryString2 = QueryBuilder.orderedDatasetQuery(this.tableName2, row);

		try (PreparedStatement statement1 = this.connection1.prepareStatement(queryString1);
				PreparedStatement statement2 = this.connection2.prepareStatement(queryString2);
				ResultSet resultSet1 = statement1.executeQuery();
				ResultSet resultSet2 = statement2.executeQuery();) {
			RowIterator it1 = RowIterator.create(resultSet1, row);
			RowIterator it2 = RowIterator.create(resultSet2, row);

			while (it1.hasRow() && it2.hasRow()) {
				switch (comparator.compare(resultSet1, resultSet2)) {
				case LESS:
					System.out.println("> " + it1.getRowString());

					it1.next();
					break;

				case EQUALS:
					it1.next();
					it2.next();
					break;

				case GREATER:
					System.out.println("< " + it2.getRowString());

					it2.next();
					break;
				}
			}

			// Show remaining row from first result set
			while (it1.hasRow()) {
				System.out.println("> " + it1.getRowString());

				it1.next();
			}

			// Show remaining row from second result set
			while (it2.hasRow()) {
				System.out.println("< " + it2.getRowString());

				it2.next();
			}
		} catch (SQLException e) {
			throw new QueryExecutionException(e);
		}
	}

	@Override
	public String toString() {
		return "TableComparator [connection1=" + connection1 + ", connection2=" + connection2 + ", tableName1="
				+ tableName1 + ", tableName2=" + tableName2 + ", options=" + options + "]";
	}

}

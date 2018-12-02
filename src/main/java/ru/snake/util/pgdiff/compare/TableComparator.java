package ru.snake.util.pgdiff.compare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ru.snake.util.pgdiff.comparator.ComparatorFactory;
import ru.snake.util.pgdiff.comparator.ResultSetComparator;
import ru.snake.util.pgdiff.query.QueryBuilder;

public class TableComparator {

	private final Connection connection1;

	private final Connection connection2;

	private final String tableName1;

	private final String tableName2;

	private final String options;

	public TableComparator(Connection connection1, Connection connection2, TableName table) {
		this.connection1 = connection1;
		this.connection2 = connection2;
		this.tableName1 = table.getTableName1();
		this.tableName2 = table.getTableName2();
		this.options = table.getOptions();
	}

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
			boolean hasNext1 = resultSet1.next();
			boolean hasNext2 = resultSet2.next();

			while (hasNext1 && hasNext2) {
				switch (comparator.compare(resultSet1, resultSet2)) {
				case LESS:
					System.out.println("> " + resultSet1.getObject(1));

					hasNext1 = resultSet1.next();
					break;

				case EQUALS:
					hasNext1 = resultSet1.next();
					hasNext2 = resultSet2.next();
					break;

				case GREATER:
					System.out.println("< " + resultSet2.getObject(1));

					hasNext2 = resultSet2.next();
					break;
				}
			}

			// Show remaining row from first result set
			while (hasNext1) {
				System.out.println("> " + resultSet1.getObject(1));

				hasNext1 = resultSet1.next();
			}

			// Show remaining row from second result set
			while (hasNext2) {
				System.out.println("< " + resultSet2.getObject(1));

				hasNext2 = resultSet2.next();
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

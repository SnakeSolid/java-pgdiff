package ru.snake.util.pgdiff.compare;

import java.sql.Connection;

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

		System.out.println(row);
	}

	@Override
	public String toString() {
		return "TableComparator [connection1=" + connection1 + ", connection2=" + connection2 + ", tableName1="
				+ tableName1 + ", tableName2=" + tableName2 + ", options=" + options + "]";
	}

}

package ru.snake.util.pgdiff.compare;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 *
 * @author snake
 *
 */
public class TableNameTest {

	@Test
	public void shouldParseSingleTableName() {
		TableNames name = TableNames.fromString("table1");

		assertEquals("table1", name.getFullTableName1());
		assertEquals("table1", name.getFullTableName2());
		assertEquals("table1", name.getTableName1());
		assertEquals("table1", name.getTableName2());
		assertEquals("public", name.getSchemaName1());
		assertEquals("public", name.getSchemaName2());
		assertEquals("", name.getOptions());
	}

	@Test
	public void shouldParseTwoTableNames() {
		TableNames name = TableNames.fromString("table1,table2");

		assertEquals("table1", name.getFullTableName1());
		assertEquals("table2", name.getFullTableName2());
		assertEquals("table1", name.getTableName1());
		assertEquals("table2", name.getTableName2());
		assertEquals("public", name.getSchemaName1());
		assertEquals("public", name.getSchemaName2());
		assertEquals("", name.getOptions());
	}

	@Test
	public void shouldParseOptionsWhenOneTableGiven() {
		TableNames name = TableNames.fromString("table1:options");

		assertEquals("table1", name.getFullTableName1());
		assertEquals("table1", name.getFullTableName2());
		assertEquals("table1", name.getTableName1());
		assertEquals("table1", name.getTableName2());
		assertEquals("public", name.getSchemaName1());
		assertEquals("public", name.getSchemaName2());
		assertEquals("options", name.getOptions());
	}

	@Test
	public void shouldParseOptionsWhenTwoTablesGiven() {
		TableNames name = TableNames.fromString("table1,table2:options");

		assertEquals("table1", name.getFullTableName1());
		assertEquals("table2", name.getFullTableName2());
		assertEquals("table1", name.getTableName1());
		assertEquals("table2", name.getTableName2());
		assertEquals("public", name.getSchemaName1());
		assertEquals("public", name.getSchemaName2());
		assertEquals("options", name.getOptions());
	}

	@Test
	public void shouldParseSchemaWhenTwoTablesGiven() {
		TableNames name = TableNames.fromString("my.table1,your.table2:options");

		assertEquals("my.table1", name.getFullTableName1());
		assertEquals("your.table2", name.getFullTableName2());
		assertEquals("table1", name.getTableName1());
		assertEquals("table2", name.getTableName2());
		assertEquals("my", name.getSchemaName1());
		assertEquals("your", name.getSchemaName2());
		assertEquals("options", name.getOptions());
	}

}

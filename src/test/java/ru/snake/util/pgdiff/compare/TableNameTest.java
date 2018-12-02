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
		TableName name = new TableName("table1");

		assertEquals("table1", name.getTableName1());
		assertEquals("table1", name.getTableName2());
		assertEquals("", name.getOptions());
	}

	@Test
	public void shouldParseTwoTableNames() {
		TableName name = new TableName("table1,table2");

		assertEquals("table1", name.getTableName1());
		assertEquals("table2", name.getTableName2());
		assertEquals("", name.getOptions());
	}

	@Test
	public void shouldParseOptionsWhenOneTableGiven() {
		TableName name = new TableName("table1:options");

		assertEquals("table1", name.getTableName1());
		assertEquals("table1", name.getTableName2());
		assertEquals("options", name.getOptions());
	}

	@Test
	public void shouldParseOptionsWhenTwoTablesGiven() {
		TableName name = new TableName("table1,table2:options");

		assertEquals("table1", name.getTableName1());
		assertEquals("table2", name.getTableName2());
		assertEquals("options", name.getOptions());
	}

}

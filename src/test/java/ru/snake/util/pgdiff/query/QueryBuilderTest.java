package ru.snake.util.pgdiff.query;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author snake
 *
 */
public class QueryBuilderTest {

	@Test
	public void shouldReturnQuery() {
		String queryString = QueryBuilder.tableColumnsQuery();

		Assertions.assertNotNull(queryString);
	}

}

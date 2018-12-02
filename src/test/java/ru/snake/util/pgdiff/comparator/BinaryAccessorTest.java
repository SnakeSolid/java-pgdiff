package ru.snake.util.pgdiff.comparator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author snake
 *
 */
public class BinaryAccessorTest {

	@Test
	public void shouldReturnZeroIfBytesEmpty() {
		BinaryAccessor accessor = new BinaryAccessor(0);
		int result = accessor.compare(new byte[] {}, new byte[] {});

		Assertions.assertEquals(0, result);
	}

	@Test
	public void shouldReturnZeroIfBytesSame() {
		BinaryAccessor accessor = new BinaryAccessor(0);
		int result = accessor.compare(new byte[] { 3, 4, 5 }, new byte[] { 3, 4, 5 });

		Assertions.assertEquals(0, result);
	}

	@Test
	public void shouldReturnLessIfLeftShort() {
		BinaryAccessor accessor = new BinaryAccessor(0);
		int result = accessor.compare(new byte[] { 3, 4 }, new byte[] { 3, 4, 5 });

		Assertions.assertEquals(true, result < 0);
	}

	@Test
	public void shouldReturnGreaterIfLeftLonger() {
		BinaryAccessor accessor = new BinaryAccessor(0);
		int result = accessor.compare(new byte[] { 3, 4, 5, 6 }, new byte[] { 3, 4, 5 });

		Assertions.assertEquals(true, result > 0);
	}

	@Test
	public void shouldReturnGreaterIfLeftGreater() {
		BinaryAccessor accessor = new BinaryAccessor(0);
		int result = accessor.compare(new byte[] { 4, 5, 6 }, new byte[] { 3, 4, 5 });

		Assertions.assertEquals(true, result > 0);
	}

	@Test
	public void shouldReturnLessIfLeftLeft() {
		BinaryAccessor accessor = new BinaryAccessor(0);
		int result = accessor.compare(new byte[] { 3, 4, 5 }, new byte[] { 4, 5, 6 });

		Assertions.assertEquals(true, result < 0);
	}

}

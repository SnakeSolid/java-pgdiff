package ru.snake.util.pgdiff.comparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.snake.util.pgdiff.compare.ColumnDescriptor;
import ru.snake.util.pgdiff.compare.ColumnType;
import ru.snake.util.pgdiff.compare.RowDescriptor;

/**
 * Factory of result set comparators.
 *
 * @author snake
 *
 */
public class ComparatorFactory {

	private static final Logger LOG = LoggerFactory.getLogger(ComparatorFactory.class);

	/**
	 * Create new result set comparator for given row descriptor.
	 *
	 * @param row
	 *            row descriptor
	 * @return result set comparator
	 */
	public static ResultSetComparator createRowComparator(RowDescriptor row) {
		List<ColumnDescriptor> columns = row.getCompareColumns();
		List<ResultSetComparator> comparators = new ArrayList<>();

		for (ColumnDescriptor column : columns) {
			createColumnComparator(column).ifPresent(comparators::add);
		}

		return createChainComparator(comparators);
	}

	private static Optional<ResultSetComparator> createColumnComparator(ColumnDescriptor column) {
		int index = column.getIndex();
		ColumnType columnType = column.getType();
		boolean nullable = column.isNullable();

		switch (columnType) {
		case INT8:
			return Optional.of(ComparatorFactory.createInt8Comparator(index, nullable));

		case INT16:
			return Optional.of(ComparatorFactory.createInt16Comparator(index, nullable));

		case INT32:
			return Optional.of(ComparatorFactory.createInt32Comparator(index, nullable));

		case INT64:
			return Optional.of(ComparatorFactory.createInt64Comparator(index, nullable));

		case FLOAT32:
			return Optional.of(ComparatorFactory.createFloat32Comparator(index, nullable));

		case FLOAT64:
			return Optional.of(ComparatorFactory.createFloat64Comparator(index, nullable));

		case STRING:
			return Optional.of(ComparatorFactory.createStringComparator(index, nullable));

		case BINARY:
			return Optional.of(ComparatorFactory.createBinaryComparator(index, nullable));

		default:
			LOG.warn("Unsupported column type {}, column {} will be ignored", columnType, column.getName());

			return Optional.empty();
		}
	}

	private static ResultSetComparator createChainComparator(List<ResultSetComparator> comparators) {
		return new ChainComparator(comparators);
	}

	private static ResultSetComparator createInt8Comparator(int index, boolean nullable) {
		return createComparator(new Int8Accessor(index), nullable);
	}

	private static ResultSetComparator createInt16Comparator(int index, boolean nullable) {
		return createComparator(new Int16Accessor(index), nullable);
	}

	private static ResultSetComparator createInt32Comparator(int index, boolean nullable) {
		return createComparator(new Int32Accessor(index), nullable);
	}

	private static ResultSetComparator createInt64Comparator(int index, boolean nullable) {
		return createComparator(new Int64Accessor(index), nullable);
	}

	private static ResultSetComparator createFloat32Comparator(int index, boolean nullable) {
		return createComparator(new Float32Accessor(index), nullable);
	}

	private static ResultSetComparator createFloat64Comparator(int index, boolean nullable) {
		return createComparator(new Float64Accessor(index), nullable);
	}

	private static ResultSetComparator createStringComparator(int index, boolean nullable) {
		return createComparator(new StringAccessor(index), nullable);
	}

	private static ResultSetComparator createBinaryComparator(int index, boolean nullable) {
		return createComparator(new BinaryAccessor(index), nullable);
	}

	private static <T> ResultSetComparator createComparator(ValueAccessor<T> accessor, boolean nullable) {
		if (nullable) {
			return new NullableValueComparator<>(accessor);
		} else {
			return new ValueComparator<>(accessor);
		}
	}

}

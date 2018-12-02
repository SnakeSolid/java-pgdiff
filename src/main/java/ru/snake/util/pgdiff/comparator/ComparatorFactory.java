package ru.snake.util.pgdiff.comparator;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.snake.util.pgdiff.compare.ColumnDescriptor;
import ru.snake.util.pgdiff.compare.ColumnType;
import ru.snake.util.pgdiff.compare.RowDescriptor;

public class ComparatorFactory {

	private static final Logger LOG = LoggerFactory.getLogger(ComparatorFactory.class);

	public static ResultSetComparator createRowComparator(RowDescriptor row) {
		List<ColumnDescriptor> columns = row.getColumns();
		List<ResultSetComparator> comparators = new ArrayList<>();
		int index = 1;

		for (ColumnDescriptor column : columns) {
			ColumnType columnType = column.getType();
			boolean nullable = column.isNullable();

			switch (columnType) {
			case INT8:
				comparators.add(ComparatorFactory.createInt8Comparator(index, nullable));
				break;

			case INT16:
				comparators.add(ComparatorFactory.createInt16Comparator(index, nullable));
				break;

			case INT32:
				comparators.add(ComparatorFactory.createInt32Comparator(index, nullable));
				break;

			case INT64:
				comparators.add(ComparatorFactory.createInt64Comparator(index, nullable));
				break;

			case FLOAT32:
				comparators.add(ComparatorFactory.createFloat32Comparator(index, nullable));
				break;

			case FLOAT64:
				comparators.add(ComparatorFactory.createFloat64Comparator(index, nullable));
				break;

			case STRING:
				comparators.add(ComparatorFactory.createStringComparator(index, nullable));
				break;

			case BINARY:
				comparators.add(ComparatorFactory.createBinaryComparator(index, nullable));
				break;

			default:
				LOG.warn("Unsupported column type {}, column {} will be ignored", columnType, column.getName());
				break;
			}

			index += 1;
		}

		return createChainComparator(comparators);
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

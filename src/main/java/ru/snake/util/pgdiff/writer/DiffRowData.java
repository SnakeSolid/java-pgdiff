package ru.snake.util.pgdiff.writer;

public class DiffRowData {

	private final DiffSide side;

	private final RowData row;

	public DiffRowData(DiffSide side, RowData row) {
		this.side = side;
		this.row = row;
	}

	public DiffSide getSide() {
		return side;
	}

	public RowData getRow() {
		return row;
	}

	@Override
	public String toString() {
		return "DiffRowData [side=" + side + ", row=" + row + "]";
	}

}

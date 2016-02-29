package com.pyrin.gameoflife;

/**
 * Represents Game of Life Grid Implementation class
 * 
 * @author Igor Pyrin
 *
 */
public class GridImpl implements Grid {
	private final int cellRows;
	private final int cellCols;

	// Cells' generation counter
	protected int generationCount = 0;
	private final GridViewRenderer renderImpl;

	// Two dimensional array which represents grid of cells
	protected boolean[][] gridArr;

	/**
	 * Constructor for Grid
	 * 
	 * @param cellRows
	 *            Rows in the grid
	 * @param cellCols
	 *            Columns in the grid
	 * @throws GridException
	 *             If grid size is incorrect or GridViewRenderer is null
	 */
	public GridImpl(int cellRows, int cellCols, GridViewRenderer renderImpl) throws GridException {
		if (cellRows < 1) {
			throw new GridException("The cells rows number '" + cellRows + "' should be positive!");
		}
		if (cellCols < 1) {
			throw new GridException("The cells columns number '" + cellCols + "' should be positive!");
		}
		if (renderImpl == null) {
			throw new GridException("The grid view renderer can't be null!");
		}
		this.cellRows = cellRows;
		this.cellCols = cellCols;
		this.renderImpl = renderImpl;
		gridArr = new boolean[this.cellRows][this.cellCols];
	}

	public GridImpl(GridInitialStateReader stateReader, GridViewRenderer renderImpl) throws GridException {
		if (stateReader == null) {
			throw new GridException("The grid initial state reader can't be null!");
		}
		if (renderImpl == null) {
			throw new GridException("The grid view renderer can't be null!");
		}
		this.renderImpl = renderImpl;
		gridArr = stateReader.readState();
		this.cellRows = gridArr.length;
		this.cellCols = gridArr[0].length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pyrin.gameoflife.Grid#setCellState(int, int, boolean)
	 */
	@Override
	public void setCellState(int row, int col, boolean newState) throws GridException {
		checkCellPosition(row, col);
		gridArr[row][col] = newState;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pyrin.gameoflife.Grid#getCellState(int, int)
	 */
	@Override
	public boolean getCellState(int row, int col) throws GridException {
		checkCellPosition(row, col);
		return gridArr[row][col];
	}

	/**
	 * Checks cell position
	 * 
	 * @param row
	 *            row position
	 * @param col
	 *            column position
	 * @throws GridException
	 *             If given cell position is not in the range of grid
	 */
	protected void checkCellPosition(int row, int col) throws GridException {
		if (row < 0 || row >= cellRows) {
			throw new GridException("The cell at row '" + row + "' is out of grid field range!");
		}
		if (col < 0 || col >= cellCols) {
			throw new GridException("The cell at column '" + col + "' is out of grid field range!");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pyrin.gameoflife.Grid#calculateNextGeneration()
	 */
	@Override
	public void calculateNextGeneration() {
		boolean[][] nextGridArr = new boolean[cellRows][cellCols];
		for (int row = 0; row < cellRows; row++) {
			for (int col = 0; col < cellCols; col++) {
				nextGridArr[row][col] = processCell(row, col);
			}
		}
		gridArr = nextGridArr;
		generationCount++;
	}

	protected boolean processCell(int row, int col) {
		int surroundCount = countSurround(row, col);
		if (gridArr[row][col]) {
			// If live cell has less than two or more than three neighbors -> die
			if (surroundCount < 2 || surroundCount > 3) {
				return false;
			}
			// If live cell has two or three neighbors -> live
			return true;
		} else {
			// Any dead cell with exactly three live neighbors becomes a live cell. 
			if (surroundCount == 3) {
				return true;
			}
			return false;
		}
	}

	protected int countSurround(int row, int col) {
		int surroundCount = 0;
		for (int x = row - 1; x <= row + 1; x++)
			for (int y = col - 1; y <= col + 1; y++) {
				if (x == row && y == col) {
					continue;
				}
				surroundCount += checkIfAlive(x, y);
			}
		return surroundCount;
	}

	/**
	 * Checks if given cell is alive
	 * 
	 * @param row
	 * @param col
	 * @return number one if cell is alive
	 */
	protected int checkIfAlive(int row, int col) {
		if (col < 0 || row < 0 || col == cellCols || row == cellRows) {
			return 0;
		}
		if (gridArr[row][col]) {
			return 1;
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pyrin.gameoflife.Grid#renderView()
	 */
	@Override
	public void renderView() {
		renderImpl.render(gridArr, generationCount);
	}

}

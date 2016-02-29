package com.pyrin.gameoflife;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Grid initial state file reader class
 * 
 * @author Igor Pyrin
 *
 */
public class GridInitialFileStateReader implements GridInitialStateReader {
	protected static final char DEAD_CELL = GridCell.DEAD.getConsoleChar();
	protected static final char ALIVE_CELL = GridCell.ALIVE.getConsoleChar();

	private Path inStateFilePath;

	public GridInitialFileStateReader(Path inStateFilePath) {
		this.inStateFilePath = inStateFilePath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pyrin.gameoflife.GridInitialStateReader#readState()
	 */
	@Override
	public boolean[][] readState() throws GridException {
		List<String> lines;
		try {
			lines = Files.readAllLines(inStateFilePath, Charset.defaultCharset());
		} catch (IOException e) {
			throw new GridException("There was an exception during initial grid state reading!", e);
		}
		if (lines == null || lines.isEmpty()) {
			throw new GridException("The initial grid state file is empty!");
		}
		if (lines.get(0) == null || lines.get(0).trim().isEmpty()) {
			throw new GridException("The initial grid state file is empty!");
		}
		int colSize = lines.get(0).trim().length();
		boolean[][] arr = new boolean[lines.size()][colSize];
		processInputLines(arr, lines, colSize);
		return arr;
	}

	/**
	 * Processes input lines for initial grid state. To avoid errors with
	 * filling array we assume absent input data in the grid as dead cell
	 * 
	 * @param arr
	 *            array to keep output grid data
	 * @param lines
	 *            input string lines
	 * @param colSize
	 *            size of the grid columns
	 */
	protected void processInputLines(boolean[][] arr, List<String> lines, int colSize) {
		int row = 0;
		for (String line : lines) {
			for (int col = 0; col < colSize; col++) {
				try {
					arr[row][col] = evalCellState(line.charAt(col));
				} catch (StringIndexOutOfBoundsException ex) {
					// Setting dead cell for absent input data in the grid
					arr[row][col] = false;
				}
			}
			row++;
		}
	}

	/**
	 * Evaluates if cell from input file is alive or dead. For simplifying
	 * evaluation process this function only checks if input character is equal
	 * to alive cell.
	 * 
	 * @param charAt
	 *            the character to evaluate
	 * @return boolean true is cell is alive and false if cell is dead
	 */
	protected static boolean evalCellState(char charAt) {
		if (charAt == ALIVE_CELL) {
			return true;
		}
		return false;
	}

}

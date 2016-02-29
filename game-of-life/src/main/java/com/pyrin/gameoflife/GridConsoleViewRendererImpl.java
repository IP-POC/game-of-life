package com.pyrin.gameoflife;

import java.io.PrintStream;

/**
 * Grid console view renderer implementation class
 * @author Igor Pyrin
 *
 */
public class GridConsoleViewRendererImpl implements GridViewRenderer {
	protected static final char DEAD_CELL = GridCell.DEAD.getConsoleChar();
	protected static final char ALIVE_CELL = GridCell.ALIVE.getConsoleChar();
	private final PrintStream ps;
	
	/**
	 * Default constructor to setup system console print stream  
	 */
	public GridConsoleViewRendererImpl() {
		ps = System.out;
	}

	/**
	 * Constructor to setup custom view print stream
	 * @param ps
	 */
	public GridConsoleViewRendererImpl(PrintStream ps) {
		this.ps = ps;
	}
	
	/* (non-Javadoc)
	 * @see com.pyrin.gameoflife.GridViewRenderer#render(boolean[][], int)
	 */
	public void render(boolean[][] gridArr, int generationCount) {
		ps.println();
		ps.println("Grid generation " + generationCount);
		int cellRows = gridArr.length;
		int cellCols = gridArr[0].length;
		for (int row = 0; row < cellRows; row++) {
			for (int col = 0; col < cellCols; col++) {
				ps.print(gridArr[row][col] ? ALIVE_CELL : DEAD_CELL);
			}
			ps.println();
		}
	}
	
}

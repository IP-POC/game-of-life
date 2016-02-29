package com.pyrin.gameoflife;

/**
 * Game of Life Grid Interface
 * 
 * @author Igor Pyrin
 *
 */
public interface Grid {

	/**
	 * Sets cell state
	 * 
	 * @param row
	 *            cell's row
	 * @param col
	 *            cell's column
	 * @param newState
	 *            alive or dead
	 * @throws GridException
	 */
	void setCellState(int row, int col, boolean newState) throws GridException;

	/**
	 * Gets cell state
	 * 
	 * @param row
	 *            cell's row
	 * @param col
	 *            cell's column
	 * @return cell's state
	 * @throws GridException
	 */
	boolean getCellState(int row, int col) throws GridException;

	/**
	 * Calculates next generation of cells
	 */
	void calculateNextGeneration();

	/**
	 * Renders grid view
	 */
	void renderView();

}
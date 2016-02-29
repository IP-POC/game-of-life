package com.pyrin.gameoflife;

/**
 * Grid view renderer interface
 * 
 * @author Igor Pyrin
 *
 */
public interface GridViewRenderer {

	/**
	 * Renders the given grid array view
	 * 
	 * @param gridArr
	 * @param generationCount
	 */
	void render(boolean[][] gridArr, int generationCount);

}
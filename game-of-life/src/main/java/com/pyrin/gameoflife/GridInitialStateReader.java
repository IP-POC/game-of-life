package com.pyrin.gameoflife;

/**
 * Grid initial state reader interface
 * 
 * @author Igor Pyrin
 *
 */
public interface GridInitialStateReader {

	boolean[][] readState() throws GridException;

}
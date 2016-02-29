package com.pyrin.gameoflife;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Grid cell enum test
 * 
 * @author Igor Pyrin
 *
 */
public class GridCellTest {

	/**
	 * Tests console chars
	 */
	@Test
	public void testConsoleChar() {
		assertEquals('O', GridCell.ALIVE.getConsoleChar());
		assertEquals('.', GridCell.DEAD.getConsoleChar());
	}

	/**
	 * Tests is alive method
	 */
	@Test
	public void testIsAlive() {
		assertTrue(GridCell.ALIVE.isAlive());
		assertFalse(GridCell.DEAD.isAlive());
	}
}

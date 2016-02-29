package com.pyrin.gameoflife;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Game of Life Grid Implementation class test
 * 
 * @author Igor Pyrin
 *
 */
public class GridImplTest {
	private GridViewRenderer renderImpl = new GridConsoleViewRendererImpl();
	private GridImpl grid;

	/**
	 * Sets up test environment
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		grid = new GridImpl(8, 8, renderImpl);
	}

	/**
	 * Tests Grid initialization
	 * 
	 * @throws GridException
	 */
	@Test
	public void testGridImplInit() throws GridException {
		Grid grid1 = new GridImpl(8, 8, renderImpl);
		assertNotNull("Grid object is not created!", grid1);
	}

	/**
	 * Tests Grid initialization with exception for columns
	 * 
	 */
	@Test
	public void testGridImplInitColsException() {
		try {
			new GridImpl(7, 0, renderImpl);
			fail("The GridException was not thrown!");
		} catch (GridException e) {
			assertEquals(e.getMessage(), "The cells columns number '0' should be positive!");
		}
	}

	/**
	 * Tests Grid initialization with exception for rows
	 * 
	 */
	@Test
	public void testGridImplInitRowsException() {
		try {
			new GridImpl(0, 3, renderImpl);
			fail("The GridException was not thrown!");
		} catch (GridException e) {
			assertEquals(e.getMessage(), "The cells rows number '0' should be positive!");
		}
	}

	/**
	 * Tests Grid initialization with exception for grid render
	 * 
	 */
	@Test
	public void testGridImplInitRenderException() {
		try {
			new GridImpl(7, 7, null);
			fail("The GridException was not thrown!");
		} catch (GridException e) {
			assertEquals(e.getMessage(), "The grid view renderer can't be null!");
		}
	}

	/**
	 * Tests Grid initialization with exception for grid render in another
	 * constructor
	 * 
	 */
	@Test
	public void testGridImplInitRenderException2() {
		try {
			new GridImpl(Mockito.mock(GridInitialFileStateReader.class), null);
			fail("The GridException was not thrown!");
		} catch (GridException e) {
			assertEquals(e.getMessage(), "The grid view renderer can't be null!");
		}
	}

	/**
	 * Tests Grid initialization with initial state reader
	 * 
	 * @throws GridException
	 * 
	 */
	@Test
	public void testGridImplInitStateReader() throws GridException {
		GridInitialStateReader stateReader = Mockito.mock(GridInitialFileStateReader.class);
		when(stateReader.readState()).thenReturn(new boolean[7][7]);
		new GridImpl(stateReader, renderImpl);
		verify(stateReader, times(1)).readState();
	}

	/**
	 * Tests Grid initialization with exception for grid initial state reader
	 * 
	 */
	@Test
	public void testGridImplInitStateReaderException() {
		try {
			new GridImpl(null, renderImpl);
			fail("The GridException was not thrown!");
		} catch (GridException e) {
			assertEquals(e.getMessage(), "The grid initial state reader can't be null!");
		}
	}

	/**
	 * Tests Grid set cell state
	 * 
	 * @throws GridException
	 */
	@Test
	public void testSetCellState() throws GridException {
		grid.setCellState(2, 1, true);
		assertTrue("Excpected cell state to be true!", grid.getCellState(2, 1));
	}

	/**
	 * Tests Grid set cell state with exception for row
	 * 
	 */
	@Test
	public void testSetCellStateRowException() {
		try {
			grid.setCellState(10, 1, false);
			fail("The GridException was not thrown!");
		} catch (GridException e) {
			assertEquals(e.getMessage(), "The cell at row '10' is out of grid field range!");
		}
	}

	/**
	 * Tests Grid set cell state with exception for column
	 * 
	 */
	@Test
	public void testSetCellStateColumnException() {
		try {
			grid.setCellState(1, 11, false);
			fail("The GridException was not thrown!");
		} catch (GridException e) {
			assertEquals(e.getMessage(), "The cell at column '11' is out of grid field range!");
		}
	}

	/**
	 * Tests Grid get cell state
	 * 
	 * @throws GridException
	 */
	@Test
	public void testGetCellState() throws GridException {
		assertFalse("Excpected cell state to be false!", grid.getCellState(2, 1));
	}

	/**
	 * Tests Grid get cell state with exception for column
	 * 
	 * @throws GridException
	 */
	@Test
	public void testGetCellStateColumnException() throws GridException {
		try {
			grid.getCellState(0, 11);
			fail("The GridException was not thrown!");
		} catch (GridException e) {
			assertEquals(e.getMessage(), "The cell at column '11' is out of grid field range!");
		}
	}

	/**
	 * Tests calculate grid next generation case 3x3 grid
	 * 
	 * @throws GridException
	 */
	@Test
	public void testCalculateNextGeneration1() throws GridException {
		boolean[][] gridTestArrInit = { { false, false, false }, { true, true, true }, { false, false, false } };
		boolean[][] gridTestArrExp = { { false, true, false }, { false, true, false }, { false, true, false } };

		GridImpl grid1 = new GridImpl(3, 3, renderImpl);
		grid1.gridArr = gridTestArrInit;
		grid1.calculateNextGeneration();
		assertArrayEquals("Next generation array should be equal to expected one!", gridTestArrExp, grid1.gridArr);
		assertEquals("Next generation counter should be equal 1!", 1, grid1.generationCount);
	}

	/**
	 * Tests calculate grid next generation case 3x3 grid
	 * 
	 * @throws GridException
	 */
	@Test
	public void testCalculateNextGeneration2() throws GridException {
		boolean[][] gridTestArrInit = { { false, true, false }, { false, true, false }, { false, true, false } };
		boolean[][] gridTestArrExp = { { false, false, false }, { true, true, true }, { false, false, false } };

		GridImpl grid1 = new GridImpl(3, 3, renderImpl);
		grid1.gridArr = gridTestArrInit;
		grid1.calculateNextGeneration();
		assertArrayEquals("Next generation array should be equal to expected one!", gridTestArrExp, grid1.gridArr);
		assertEquals("Next generation counter should be equal 1!", 1, grid1.generationCount);
	}

	/**
	 * Tests calculate grid next generation case 4x4 empty grid
	 * 
	 * @throws GridException
	 */
	@Test
	public void testCalculateNextGenerationEmpty() throws GridException {
		boolean[][] gridTestArrExp = { { false, false, false, false }, { false, false, false, false },
				{ false, false, false, false }, { false, false, false, false } };

		GridImpl grid1 = new GridImpl(4, 4, renderImpl);
		grid1.calculateNextGeneration();
		assertArrayEquals("Next generation array should be equal to expected one!", gridTestArrExp, grid1.gridArr);
		assertEquals("Next generation counter should be equal 1!", 1, grid1.generationCount);
	}

	/**
	 * Tests count surround alive cells for zero cells case
	 */
	@Test
	public void testCountSurroundZero() {
		assertEquals("Expected CountSurround function to return 0!", 0, grid.countSurround(1, 1));
	}

	/**
	 * Tests count surround alive cells for one cell case
	 * 
	 * @throws GridException
	 */
	@Test
	public void testCountSurroundOne() throws GridException {
		grid.setCellState(0, 0, true);
		assertEquals("Expected CountSurround function to return 1!", 1, grid.countSurround(1, 1));
	}

	/**
	 * Tests count surround alive cells for two cells case
	 * 
	 * @throws GridException
	 */
	@Test
	public void testCountSurroundTwo() throws GridException {
		grid.setCellState(0, 0, true);
		grid.setCellState(2, 1, true);
		assertEquals("Expected CountSurround function to return 2!", 2, grid.countSurround(1, 1));
	}

	/**
	 * Tests count surround alive cells for three cells case
	 * 
	 * @throws GridException
	 */
	@Test
	public void testCountSurroundThree() throws GridException {
		grid.setCellState(0, 0, true);
		grid.setCellState(2, 1, true);
		grid.setCellState(2, 2, true);
		assertEquals("Expected CountSurround function to return 3!", 3, grid.countSurround(1, 1));
	}

	/**
	 * Tests check if cell is alive with dead cell
	 * 
	 */
	@Test
	public void testCheckIfAliveFalse() {
		assertEquals("Expected checkIfAlive function to return 0!", 0, grid.checkIfAlive(1, 1));
	}

	/**
	 * Tests check if cell is alive with alive cell
	 * 
	 * @throws GridException
	 */
	@Test
	public void testCheckIfAliveTrue() throws GridException {
		grid.setCellState(1, 1, true);
		assertEquals("Expected checkIfAlive function to return 1!", 1, grid.checkIfAlive(1, 1));
	}

	/**
	 * Tests grid render view
	 * 
	 * @throws GridException
	 */
	@Test
	public void testRenderView() throws GridException {
		GridViewRenderer renderImpl1 = Mockito.mock(GridConsoleViewRendererImpl.class);
		GridImpl grid1 = new GridImpl(8, 8, renderImpl1);
		grid1.renderView();
		verify(renderImpl1, times(1)).render(grid1.gridArr, grid1.generationCount);
	}

}

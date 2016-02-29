package com.pyrin.gameoflife;

import static org.junit.Assert.*;
import static org.powermock.api.easymock.PowerMock.*;
import static org.easymock.EasyMock.expect;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Grid initial file state reader test
 * 
 * @author Igor Pyrin
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ GridInitialFileStateReader.class })
public class GridInitialFileStateReaderTest {

	/**
	 * Tests read initial grid state from test file
	 * 
	 * @throws GridException
	 * @throws URISyntaxException
	 */
	@Test
	public void testReadState() throws GridException, IOException {
		boolean[][] arrExp = { { false, false, false }, { false, true, false }, { false, true, false } };

		Path pathMock = Mockito.mock(Path.class);
		GridInitialFileStateReader stReader = new GridInitialFileStateReader(pathMock);

		mockStatic(Files.class);
		List<String> lines = new ArrayList<String>();
		lines.add("...");
		lines.add(".O.");
		lines.add(".O.");

		expect(Files.readAllLines(pathMock, Charset.defaultCharset())).andReturn(lines);
		replayAll();

		boolean[][] arr = stReader.readState();
		assertArrayEquals("The array from readState function should be equal to expected one!", arrExp, arr);
		verifyAll();
	}

	/**
	 * Tests read initial grid state from test file with empty lines
	 * 
	 * @throws GridException
	 * @throws URISyntaxException
	 */
	@Test
	public void testReadStateEmpty() throws IOException {
		Path pathMock = Mockito.mock(Path.class);
		GridInitialFileStateReader stReader = new GridInitialFileStateReader(pathMock);

		mockStatic(Files.class);
		List<String> lines = new ArrayList<String>();

		expect(Files.readAllLines(pathMock, Charset.defaultCharset())).andReturn(lines);
		replayAll();

		try {
			stReader.readState();
			fail("The GridException was not thrown!");
		} catch (GridException e) {
			assertEquals(e.getMessage(), "The initial grid state file is empty!");
		}
		verifyAll();
	}

	/**
	 * Tests process input lines all dead cells
	 */
	@Test
	public void testProcessInputLinesAllDeadCells() {
		boolean[][] arrExp = { { false, false, false } };
		GridInitialFileStateReader stReader = new GridInitialFileStateReader(Mockito.mock(Path.class));
		List<String> lines = new ArrayList<String>();
		lines.add("...");
		boolean[][] arr = new boolean[1][3];
		int colSize = 3;
		stReader.processInputLines(arr, lines, colSize);
		assertArrayEquals("The array from processInputLines function should be equal to expected one!", arrExp, arr);
	}

	/**
	 * Tests process input lines with different cells
	 */
	@Test
	public void testProcessInputLinesDifferentCells() {
		boolean[][] arrExp = { { false, false, false }, { false, true, false }, { false, false, false } };
		GridInitialFileStateReader stReader = new GridInitialFileStateReader(Mockito.mock(Path.class));
		List<String> lines = new ArrayList<String>();
		lines.add("...");
		lines.add(".O.");
		lines.add("...");
		boolean[][] arr = new boolean[3][3];
		int colSize = 3;
		stReader.processInputLines(arr, lines, colSize);
		assertArrayEquals("The array from processInputLines function should be equal to expected one!", arrExp, arr);
	}

	/**
	 * Tests process input lines with unspecified cells
	 */
	@Test
	public void testProcessInputLinesUnspecifiedCells() {
		boolean[][] arrExp = { { false, false, false }, { false, true, false }, { false, false, false } };
		GridInitialFileStateReader stReader = new GridInitialFileStateReader(Mockito.mock(Path.class));
		List<String> lines = new ArrayList<String>();
		lines.add("...");
		lines.add(".O");
		lines.add("..");
		boolean[][] arr = new boolean[3][3];
		int colSize = 3;
		stReader.processInputLines(arr, lines, colSize);
		assertArrayEquals("The array from processInputLines function should be equal to expected one!", arrExp, arr);
	}

	/**
	 * Tests evaluation of alive cell state
	 */
	@Test
	public void testEvalCellStateAlive() {
		assertEquals("The evalCellState function should return true for alive cell!", GridCell.ALIVE.isAlive(),
				GridInitialFileStateReader.evalCellState(GridCell.ALIVE.getConsoleChar()));
	}

}

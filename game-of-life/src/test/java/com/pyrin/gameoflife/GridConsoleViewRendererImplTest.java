package com.pyrin.gameoflife;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Grid console view renderer implementation class test
 * 
 * @author Igor Pyrin
 *
 */
public class GridConsoleViewRendererImplTest {
	// System-dependent line separator string
	private static final String LINE_SEPARATOR = System.lineSeparator();
	private GridViewRenderer displImpl;
	private boolean[][] gridArr;
	// Stream to hold the output
	private ByteArrayOutputStream baos;
	private PrintStream ps;

	/**
	 * Sets up test environment
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Create a stream to hold the output
		baos = new ByteArrayOutputStream();
		ps = new PrintStream(baos);
		displImpl = new GridConsoleViewRendererImpl(ps);
		gridArr = new boolean[4][4];
	}

	/**
	 * Tear down test environment
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		ps.close();
	}

	/**
	 * Tests render empty grid
	 */
	@Test
	public void testRenderGridEmpty() {
		StringBuilder expectSb = new StringBuilder();
		expectSb.append(LINE_SEPARATOR);
		expectSb.append("Grid generation 0");
		expectSb.append(LINE_SEPARATOR);
		expectSb.append("....");
		expectSb.append(LINE_SEPARATOR);
		expectSb.append("....");
		expectSb.append(LINE_SEPARATOR);
		expectSb.append("....");
		expectSb.append(LINE_SEPARATOR);
		expectSb.append("....");
		expectSb.append(LINE_SEPARATOR);

		int generationCount = 0;
		displImpl.render(gridArr, generationCount);
		ps.flush();
		assertEquals(expectSb.toString(), baos.toString());
	}

	/**
	 * Tests render grid with one cell
	 */
	@Test
	public void testRenderGridOneCell() {
		StringBuilder expectSb = new StringBuilder();
		expectSb.append(LINE_SEPARATOR);
		expectSb.append("Grid generation 1");
		expectSb.append(LINE_SEPARATOR);
		expectSb.append(".O..");
		expectSb.append(LINE_SEPARATOR);
		expectSb.append("....");
		expectSb.append(LINE_SEPARATOR);
		expectSb.append("....");
		expectSb.append(LINE_SEPARATOR);
		expectSb.append("....");
		expectSb.append(LINE_SEPARATOR);

		gridArr[0][1] = true;
		int generationCount = 1;
		displImpl.render(gridArr, generationCount);
		ps.flush();
		assertEquals(expectSb.toString(), baos.toString());
	}

	/**
	 * Tests render grid with multiple cells
	 */
	@Test
	public void testRenderGridMultiCell() {
		StringBuilder expectSb = new StringBuilder();
		expectSb.append(LINE_SEPARATOR);
		expectSb.append("Grid generation 2");
		expectSb.append(LINE_SEPARATOR);
		expectSb.append(".OO.");
		expectSb.append(LINE_SEPARATOR);
		expectSb.append("..O.");
		expectSb.append(LINE_SEPARATOR);
		expectSb.append("...O");
		expectSb.append(LINE_SEPARATOR);
		expectSb.append("....");
		expectSb.append(LINE_SEPARATOR);

		gridArr[0][1] = true;
		gridArr[0][2] = true;
		gridArr[1][2] = true;
		gridArr[2][3] = true;
		int generationCount = 2;
		displImpl.render(gridArr, generationCount);
		ps.flush();
		assertEquals(expectSb.toString(), baos.toString());
	}

}

package com.pyrin.gameoflife;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.startsWith;

import java.io.PrintStream;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Game Of Life application class test
 * 
 * @author Igor Pyrin
 *
 */
public class GameOfLifeAppTest {
	private GameOfLifeApp app;

	@Before
	public void setUp() throws Exception {
		app = new GameOfLifeApp();
	}

	/**
	 * Tests launch application no arguments command
	 * 
	 * @throws GridException
	 */
	@Test
	public void testLaunchAppNoCommand() throws GridException {
		PrintStream out = Mockito.mock(PrintStream.class);
		System.setOut(out);
		String[] args = { "" };
		app.launchAppCommand(args);
		verify(out).println(startsWith("Use option"));
	}

	/**
	 * Tests show application help
	 */
	@Test
	public void testShowHelp() {
		PrintStream out = Mockito.mock(PrintStream.class);
		System.setOut(out);
		app.showHelp();
		verify(out).println(startsWith("Use option"));
	}

	/**
	 * Tests get path to default grid state
	 * 
	 * @throws GridException
	 */
	@Test
	public void testGetPathToDefaultGridState() throws GridException {
		Path defPath = app.getPathToDefaultGridState();
		assertNotNull(defPath);
		assertEquals(GameOfLifeApp.GRID_DEFAULT_STATE_CONFIG, defPath.getFileName().toString());
	}

}

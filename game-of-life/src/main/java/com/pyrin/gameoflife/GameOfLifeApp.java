package com.pyrin.gameoflife;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;

/**
 * Game of Life Standalone version class
 * 
 * @author Igor Pyrin
 *
 */
public class GameOfLifeApp {
	private static final String Y_KEY = "y";
	protected static final String GRID_DEFAULT_STATE_CONFIG = "GridDefaultState.config";
	private static final int MIN_ARG = 1;
	private static final String GRID_STATE_FILE_KEY = "-f";
	private static final String HELP_KEY = "-help";

	/*
	 * Protected constructor in order to hide public one
	 */
	protected GameOfLifeApp() {
	}

	/**
	 * Main method
	 * 
	 * @param args
	 * @throws GridException
	 */
	public static void main(String[] args) throws GridException {
		GameOfLifeApp app = new GameOfLifeApp();
		app.launchAppCommand(args);
	}

	/**
	 * Launches application command
	 * 
	 * @param args
	 * @throws GridException
	 */
	protected void launchAppCommand(String[] args) throws GridException {
		Path inStateFilePath;
		int curArg = 0;
		if (args.length >= MIN_ARG && GRID_STATE_FILE_KEY.equalsIgnoreCase(args[curArg])) {
			if (args.length > MIN_ARG && args[curArg + 1] != null && !args[curArg + 1].isEmpty()) {
				inStateFilePath = Paths.get(args[curArg + 1]);
			} else {
				inStateFilePath = getPathToDefaultGridState();
			}
		} else if (args.length == 1 && HELP_KEY.equalsIgnoreCase(args[curArg])) {
			showHelp();
			return;
		} else {
			showHelp();
			return;
		}

		processGame(inStateFilePath);
	}

	/**
	 * Processes the game iterations
	 * 
	 * @param inStateFilePath
	 *            grid input state file
	 * @throws GridException
	 */
	private void processGame(Path inStateFilePath) throws GridException {
		Grid grid = new GridImpl(new GridInitialFileStateReader(inStateFilePath), new GridConsoleViewRendererImpl());

		Scanner scanner = new Scanner(System.in);
		try {
			String inStr = "";
			do {
				grid.renderView();
				System.out.println("Do you want to see next generation? y/n");
				inStr = scanner.nextLine();
				grid.calculateNextGeneration();
			} while (inStr.equalsIgnoreCase(Y_KEY));
		} finally {
			scanner.close();
		}
	}

	protected void showHelp() {
		System.out.println("Use option '" + GRID_STATE_FILE_KEY + "' to set input file for initial state of grid.");
		System.out.println("Example: -f initialstate.config");
		System.out.println("Note: If you don't provide the file name then application will assume default 8x6 initial grid.");
	}

	/**
	 * Get path to default grid state
	 * 
	 * @return
	 * @throws GridException
	 */
	protected Path getPathToDefaultGridState() throws GridException {
		try {
			return getPathFromURI(getClass().getClassLoader().getResource(GRID_DEFAULT_STATE_CONFIG).toURI());
		} catch (URISyntaxException e) {
			throw new GridException("Can't read default grid state file!", e);
		} catch (IOException e) {
			throw new GridException("Can't read default grid state file!", e);
		}
	}

	/**
	 * Gets path from URI. It helps to resolve bug JDK-7181278 : Paths.get(uri)
	 * throws IllegalArgumentException using ZipFileSystemProvider
	 * 
	 * @param uri
	 *            representation of path to file
	 * @return path to file
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	private static Path getPathFromURI(URI uri) throws URISyntaxException, IOException {
		try {
			return Paths.get(uri);
		} catch (FileSystemNotFoundException exp) {
			Map<String, String> env = Collections.emptyMap();
			FileSystems.newFileSystem(uri, env, null);
			return Paths.get(uri);
		}
	}

}

package com.pyrin.gameoflife;

/**
 * Signals that an error occurred with Grid.
 * 
 * @author Igor Pyrin
 *
 */
public class GridException extends Exception {
	/**
	 * The serial version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new exception with the specified detail message.
	 *
	 * @param message
	 *            the detail message (which is saved for later retrieval by the
	 *            {@link #getMessage()} method)
	 */
	public GridException(String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * 
	 * @param message
	 * @param cause
	 */
	public GridException(String message, Throwable cause) {
		super(message, cause);
	}

}
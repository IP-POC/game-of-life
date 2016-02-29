package com.pyrin.gameoflife;

public enum GridCell {
	ALIVE(true, 'O'), DEAD(false, '.');

	private char consoleChar;
	private boolean alive;

	private GridCell(boolean alive, char consoleChar) {
		this.alive = alive;
		this.consoleChar = consoleChar;
	}

	public char getConsoleChar() {
		return consoleChar;
	}

	public boolean isAlive() {
		return alive;
	}

}

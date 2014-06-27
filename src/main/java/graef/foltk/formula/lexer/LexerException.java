package graef.foltk.formula.lexer;

import graef.foltk.formula.Location;

public class LexerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int expected;
	private final int got;
	private final Location location;

	public LexerException(int expected, int got, Location location) {
		super(generateErrorMessage(expected, got, location));
		this.expected = expected;
		this.got = got;
		this.location = location;
	}
	
	public LexerException(int got, Location location) {
		this(-1, got, location);
	}
	
	private static String generateErrorMessage(int expected, int got, Location location) {
		if (expected == -1) {
			return location + ": Unexpected character '" + got + "'";
		}
		else {
			return location + ": Expected '" + expected + "' but read '" + got + "'";
		}
	}

	public int getExpected() {
		return expected;
	}

	public int getGot() {
		return got;
	}

	public Location getLocation() {
		return location;
	}

}

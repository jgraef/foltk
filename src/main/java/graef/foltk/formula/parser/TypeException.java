package graef.foltk.formula.parser;

import graef.foltk.formula.ast.Declaration;

public class TypeException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Declaration expected;
	private final Declaration got;

	public TypeException(Declaration expected, Declaration got) {
		super(generateErrorMessage(expected, got));
		this.expected = expected;
		this.got = got;
	}
	
	public TypeException(Declaration got) {
		this(null, got);
	}

	private static String generateErrorMessage(Declaration expected, Declaration got) {
		return "type error"; // TODO
	}

	public Declaration getExpected() {
		return expected;
	}

	public Declaration getGot() {
		return got;
	}
}

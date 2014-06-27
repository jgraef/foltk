package graef.foltk.formula.parser;

import graef.foltk.formula.lexer.Token;
import graef.foltk.formula.lexer.TokenType;

public class ParserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final TokenType expected;
	private final Token got;

	public ParserException(TokenType expected, Token got) {
		super(generateErrorMessage(expected, got));
		this.expected = expected;
		this.got = got;
	}
	
	private static String generateErrorMessage(TokenType expected, Token got) {
		if (expected == null) {
			return got.getLocation() + ": Unexpected token '" + got + "'";
		}
		else {
			return got.getLocation() + ": Expected token '" + expected + "' but got '" + got + "'";
		}
	}
	
	public ParserException(Token got) {
		this(null, got);
	}

	public TokenType getExpected() {
		return expected;
	}

	public Token getGot() {
		return got;
	}

}

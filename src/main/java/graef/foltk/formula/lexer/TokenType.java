package graef.foltk.formula.lexer;

public enum TokenType {
	EOF,
	DUMMY,
	
	IMPORT,
	STRING,
	
	SEMICOLON,
	COLON,
	
	/*
	PREDICATE_DECL,
	VARIABLE_DECL,
	FUNCTION_DECL,
	CONSTANT_DECL,
	*/
	
	NUMBER,
	SYMBOL,
	
	LPAREN,
	RPAREN,
	
	TRUE,
	FALSE,
	
	FORALL,
	EXISTS,
	
	NOT,
	AND(4),
	OR(3),
	IMPL(2, true),
	BICOND(1);
	
	private final int precedence;
	private final boolean rightAssociative;
	
	private TokenType() {
		this(-1);
	}
	
	private TokenType(int precedence) {
		this(precedence, false);
	}
	
	private TokenType(int precedence, boolean rightAssociative) {
		this.precedence = precedence;
		this.rightAssociative = rightAssociative;
	}
	
	public int getPrecedence() {
		return precedence;
	}
	
	public boolean isRightAssociative() {
		return rightAssociative;
	}
}

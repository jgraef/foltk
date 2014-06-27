package graef.foltk.formula.ast;

import graef.foltk.formula.lexer.Token;

public class Symbol extends AstNode {
	private final String ident;
	
	public Symbol(Token token, String ident) {
		super(token);
		this.ident = ident;
	}
	
	public String getIdentifier() {
		return ident;
	}
	
	public String toString() {
		return ident;
	}

	@Override
	public void accept(AstVisitor visitor) {
		visitor.visit(this);
	}
}

package graef.foltk.formula.ast;

import graef.foltk.formula.Location;
import graef.foltk.formula.lexer.Token;

public abstract class AstNode {
	private final Token token;
	
	public AstNode(Token token) {
		this.token = token;
	}
	
	public Token getToken() {
		return token;
	}
	
	public Location getLocation() {
		return token.getLocation();
	}
	
	public abstract void accept(AstVisitor visitor);
}

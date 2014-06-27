package graef.foltk.formula.ast.term;

import graef.foltk.formula.ast.AstNode;
import graef.foltk.formula.lexer.Token;

public abstract class Term extends AstNode {
	public Term(Token token) {
		super(token);
	}

}

package graef.foltk.formula.ast.proposition;

import graef.foltk.formula.ast.AstVisitor;
import graef.foltk.formula.lexer.Token;

public class NotProposition extends Proposition {

	public NotProposition(Token token, Proposition proposition) {
		super(token, proposition);
	}
	
	@Override
	public String toString() {
		return "!(" + this.getOperand(0) + ")";
	}
	
	@Override
	public void accept(AstVisitor visitor) {
		visitor.visit(this);
	}

}

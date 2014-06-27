package graef.foltk.formula.ast.proposition;

import graef.foltk.formula.ast.AstVisitor;
import graef.foltk.formula.lexer.Token;

public class AndProposition extends BinaryProposition {

	public AndProposition(Token token, Proposition operand1, Proposition operand2) {
		super(token, operand1, operand2);
	}
	
	@Override
	public void accept(AstVisitor visitor) {
		visitor.visit(this);
	}

}

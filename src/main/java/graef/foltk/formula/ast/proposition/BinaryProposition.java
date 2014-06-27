package graef.foltk.formula.ast.proposition;

import graef.foltk.formula.lexer.Token;

public abstract class BinaryProposition extends Proposition {
	public BinaryProposition(Token token, Proposition operand1, Proposition operand2) {
		super(token, operand1, operand2);
	}
	
	@Override
	public String toString() {
		return "(" + getOperand(0) + ") " + getToken() + " (" + getOperand(1) + ")";
	}	
}

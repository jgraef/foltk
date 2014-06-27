package graef.foltk.formula.ast.proposition;

import graef.foltk.formula.ast.AstNode;
import graef.foltk.formula.lexer.Token;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class Proposition extends AstNode {
	private final List<Proposition> operands;
	
	public Proposition(Token token, Proposition... operands) {
		super(token);
		this.operands = Arrays.asList(operands);
	}
	
	public Proposition getOperand(int i) {
		return operands.get(i);
	}
	
	public List<Proposition> getOperands() {
		return Collections.unmodifiableList(operands);
	}
}

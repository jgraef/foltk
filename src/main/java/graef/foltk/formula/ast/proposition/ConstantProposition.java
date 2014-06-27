package graef.foltk.formula.ast.proposition;

import graef.foltk.formula.ast.AstVisitor;
import graef.foltk.formula.lexer.Token;

public class ConstantProposition extends Proposition {
	private final boolean value;
	
	public ConstantProposition(Token token, boolean value) {
		super(token);
		this.value = value;
	}
	
	public boolean getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return value ? "T" : "F";
	}
	
	@Override
	public void accept(AstVisitor visitor) {
		visitor.visit(this);
	}
}

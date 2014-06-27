package graef.foltk.formula.ast.proposition;

import graef.foltk.formula.ast.Symbol;
import graef.foltk.formula.lexer.Token;
import graef.foltk.formula.parser.Scope;

public abstract class QuantifiedProposition extends Proposition {
	private final Symbol variable;
	private final Scope scope;
	
	public QuantifiedProposition(Token token, Symbol variable, Proposition body, Scope scope) {
		super(token, body);
		
		this.variable = variable;
		this.scope = scope;
	}
	
	public Symbol getVariable() {
		return variable;
	}
	
	public Scope getScope() {
		return scope;
	}
	
	@Override
	public String toString() {
		return getToken() + " " + getVariable() + " (" + getOperand(0) + ")";
	}
}

package graef.foltk.compiler.prenex;

import graef.foltk.formula.ast.Symbol;
import graef.foltk.formula.parser.Scope;

public abstract class Quantifier {
	private final Symbol var;
	private final Scope scope;
	
	public Quantifier(Symbol var, Scope scope) {
		this.var = var;
		this.scope = scope;
	}
	
	public Scope getScope() {
		return scope;
	}
	
	public Symbol getVariable() {
		return var;
	}
	
	public abstract void accept(QuantorVisitor visitor);
}

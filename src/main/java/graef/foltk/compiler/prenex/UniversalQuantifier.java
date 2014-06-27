package graef.foltk.compiler.prenex;

import graef.foltk.formula.ast.Symbol;
import graef.foltk.formula.ast.proposition.UniversalQuantifiedProposition;
import graef.foltk.formula.parser.Scope;

public class UniversalQuantifier extends Quantifier {

	public UniversalQuantifier(UniversalQuantifiedProposition univ) {
		this(univ.getVariable(), univ.getScope());
	}
	
	public UniversalQuantifier(Symbol var, Scope scope) {
		super(var, scope);
	}

}

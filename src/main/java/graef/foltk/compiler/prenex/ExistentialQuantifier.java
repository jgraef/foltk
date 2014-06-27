package graef.foltk.compiler.prenex;

import graef.foltk.formula.ast.Symbol;
import graef.foltk.formula.ast.proposition.ExistentialQuantifiedProposition;
import graef.foltk.formula.parser.Scope;

public class ExistentialQuantifier extends Quantifier {

	public ExistentialQuantifier(ExistentialQuantifiedProposition exists) {
		this(exists.getVariable(), exists.getScope());
	}

	public ExistentialQuantifier(Symbol variable, Scope scope) {
		super(variable, scope);
	}

}

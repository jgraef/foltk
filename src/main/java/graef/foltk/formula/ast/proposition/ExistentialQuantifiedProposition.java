package graef.foltk.formula.ast.proposition;

import graef.foltk.formula.ast.AstVisitor;
import graef.foltk.formula.ast.Symbol;
import graef.foltk.formula.lexer.Token;
import graef.foltk.formula.parser.Scope;

public class ExistentialQuantifiedProposition extends QuantifiedProposition {

	public ExistentialQuantifiedProposition(Token token, Symbol variable,
			Proposition proposition, Scope scope) {
		super(token, variable, proposition, scope);
	}
	
	@Override
	public void accept(AstVisitor visitor) {
		visitor.visit(this);
	}

}

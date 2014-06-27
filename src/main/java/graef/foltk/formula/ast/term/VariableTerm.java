package graef.foltk.formula.ast.term;

import graef.foltk.formula.ast.AstVisitor;
import graef.foltk.formula.ast.Symbol;
import graef.foltk.formula.lexer.Token;

public class VariableTerm extends Term {
	private Symbol symbol;
	
	public VariableTerm(Token token, Symbol symbol) {
		super(token);
		this.symbol = symbol;
	}
	
	public Symbol getSymbol() {
		return symbol;
	}
	
	public String toString() {
		return symbol.toString();
	}
	
	@Override
	public void accept(AstVisitor visitor) {
		visitor.visit(this);
	}
	
}

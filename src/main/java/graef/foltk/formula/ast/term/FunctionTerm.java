package graef.foltk.formula.ast.term;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import graef.foltk.formula.ast.AstVisitor;
import graef.foltk.formula.ast.Symbol;
import graef.foltk.formula.lexer.Token;

public class FunctionTerm extends Term {
	private Symbol symbol;
	private List<Term> arguments;
	
	public FunctionTerm(Token token, Symbol symbol, List<Term> terms) {
		super(token);
		this.symbol = symbol;
		this.arguments = new ArrayList<Term>(terms);
	}
	
	public Symbol getSymbol() {
		return symbol;
	}

	public List<Term> getArguments() {
		return Collections.unmodifiableList(arguments);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(symbol.toString());
		Iterator<Term> itTerms = arguments.iterator();
		if (itTerms.hasNext()) {
			sb.append("(");
			while (itTerms.hasNext()) {
				sb.append(itTerms.next().toString());
				if (itTerms.hasNext()) {
					sb.append(", ");
				}
			}
			sb.append(")");
		}
		return sb.toString();
	}
	
	@Override
	public void accept(AstVisitor visitor) {
		visitor.visit(this);
	}
}

package graef.foltk.formula.ast.proposition;

import graef.foltk.formula.ast.AstVisitor;
import graef.foltk.formula.ast.Symbol;
import graef.foltk.formula.ast.term.Term;
import graef.foltk.formula.lexer.Token;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class PredicateProposition extends Proposition {
	private final Symbol symbol;
	private final List<Term> terms;
	
	public PredicateProposition(Token token, Symbol symbol) {
		this(token, symbol, Collections.<Term>emptyList());
	}
	
	public PredicateProposition(Token token, Symbol symbol, List<Term> terms) {
		super(token);
		this.symbol = symbol;
		this.terms = new ArrayList<Term>(terms);
	}
	
	public Symbol getSymbol() {
		return symbol;
	}
	
	public int getArity() {
		return terms.size();
	}
	
	public List<Term> getTerms() {
		return Collections.unmodifiableList(terms);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(symbol.toString());
		Iterator<Term> termIter = terms.iterator();
		if (termIter.hasNext()) {
			sb.append("(");
			while (termIter.hasNext()) {
				sb.append(termIter.next().toString());
				if (termIter.hasNext()) {
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

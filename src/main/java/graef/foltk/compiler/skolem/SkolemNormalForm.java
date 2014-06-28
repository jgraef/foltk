package graef.foltk.compiler.skolem;

import graef.foltk.compiler.prenex.UniversalQuantifier;
import graef.foltk.formula.ast.proposition.AndProposition;
import graef.foltk.formula.ast.proposition.Proposition;
import graef.foltk.formula.lexer.Token;
import graef.foltk.formula.lexer.TokenType;

import java.util.ArrayList;
import java.util.List;

public class SkolemNormalForm {
	private final List<UniversalQuantifier> quantifiers;
	private final Proposition matrix;
	
	public SkolemNormalForm(List<UniversalQuantifier> quantifiers, Proposition matrix) {
		this.quantifiers = quantifiers;
		this.matrix = matrix;
	}

	public List<UniversalQuantifier> getQuantifiers() {
		return quantifiers;
	}

	public Proposition getMatrix() {
		return matrix;
	}
	
	public SkolemNormalForm join(SkolemNormalForm other) {
		List<UniversalQuantifier> quantifiers = new ArrayList<>(this.quantifiers);
		quantifiers.addAll(other.getQuantifiers());
		Proposition matrix = new AndProposition(new Token(TokenType.AND, "&"), this.matrix, other.getMatrix());
		return new SkolemNormalForm(quantifiers, matrix);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (UniversalQuantifier q: quantifiers) {
			sb.append(q.toString());
			sb.append(" ");
		}
		sb.append(matrix.toString());
		return sb.toString();
	}
}

package graef.foltk.compiler.prenex;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import graef.foltk.formula.ast.proposition.Proposition;

public class PrenexNormalForm {
	private final List<Quantifier> quantifiers;
	private final Proposition matrix;

	public PrenexNormalForm(List<Quantifier> quantifiers, Proposition matrix) {
		this.quantifiers = quantifiers;
		this.matrix = matrix;
	}

	public List<Quantifier> getQuantifiers() {
		return Collections.unmodifiableList(quantifiers);
	}

	public Proposition getMatrix() {
		return matrix;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Quantifier q: quantifiers) {
			sb.append(q.toString());
			sb.append(" ");
		}
		sb.append(matrix.toString());
		return sb.toString();
	}
}

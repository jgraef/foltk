package graef.foltk.compiler.prenex;

import java.util.Collections;
import java.util.List;

import graef.foltk.formula.ast.proposition.Proposition;

public class PrenexNormalForm {
	private List<Quantifier> quantifiers;
	private Proposition matrix;

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
	
	
}

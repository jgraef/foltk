package graef.foltk.formula.ast.transform;

import graef.foltk.formula.ast.Symbol;
import graef.foltk.formula.ast.proposition.AndProposition;
import graef.foltk.formula.ast.proposition.BiconditionalProposition;
import graef.foltk.formula.ast.proposition.ConstantProposition;
import graef.foltk.formula.ast.proposition.ExistentialQuantifiedProposition;
import graef.foltk.formula.ast.proposition.ImplicationProposition;
import graef.foltk.formula.ast.proposition.NotProposition;
import graef.foltk.formula.ast.proposition.OrProposition;
import graef.foltk.formula.ast.proposition.PredicateProposition;
import graef.foltk.formula.ast.proposition.UniversalQuantifiedProposition;
import graef.foltk.formula.ast.term.FunctionTerm;
import graef.foltk.formula.ast.term.VariableTerm;

public class NullTransformation<T> implements Transformation<T> {
	@Override
	public T transform(Transformer<T> rewriter, Symbol symbol) {
		return null;
	}

	@Override
	public T transform(Transformer<T> rewriter, AndProposition and) {
		return null;
	}

	@Override
	public T transform(Transformer<T> rewriter, OrProposition or) {
		return null;
	}

	@Override
	public T transform(Transformer<T> rewriter, ImplicationProposition impl) {
		return null;
	}

	@Override
	public T transform(Transformer<T> rewriter, BiconditionalProposition bicond) {
		return null;
	}

	@Override
	public T transform(Transformer<T> rewriter, ConstantProposition constant) {
		return null;
	}

	@Override
	public T transform(Transformer<T> rewriter,
			ExistentialQuantifiedProposition exists) {
		return null;
	}

	@Override
	public T transform(Transformer<T> rewriter, UniversalQuantifiedProposition univ) {
		return null;
	}

	@Override
	public T transform(Transformer<T> rewriter, NotProposition not) {
		return null;
	}

	@Override
	public T transform(Transformer<T> rewriter, PredicateProposition predicate) {
		return null;
	}

	@Override
	public T transform(Transformer<T> rewriter, FunctionTerm func) {
		return null;
	}

	@Override
	public T transform(Transformer<T> rewriter, VariableTerm var) {
		return null;
	}

}

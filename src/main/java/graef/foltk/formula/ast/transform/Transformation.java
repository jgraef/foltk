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
import graef.foltk.formula.ast.proposition.Proposition;
import graef.foltk.formula.ast.proposition.UniversalQuantifiedProposition;
import graef.foltk.formula.ast.term.FunctionTerm;
import graef.foltk.formula.ast.term.VariableTerm;

public interface Transformation<T> {
	public T transform(Transformer<T> rewriter, Symbol symbol);
	public T transform(Transformer<T> rewriter, AndProposition and);
	public T transform(Transformer<T> rewriter, OrProposition or);
	public T transform(Transformer<T> rewriter, ImplicationProposition impl);
	public T transform(Transformer<T> rewriter, BiconditionalProposition bicond);
	public T transform(Transformer<T> rewriter, ConstantProposition constant);
	public T transform(Transformer<T> rewriter, ExistentialQuantifiedProposition exists);
	public T transform(Transformer<T> rewriter, UniversalQuantifiedProposition univ);
	public T transform(Transformer<T> rewriter, NotProposition not);
	public T transform(Transformer<T> rewriter, PredicateProposition predicate);
	public T transform(Transformer<T> rewriter, FunctionTerm func);
	public T transform(Transformer<T> rewriter, VariableTerm var);
}

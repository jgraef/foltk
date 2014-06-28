package graef.foltk.formula.ast.transform;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import graef.foltk.formula.ast.AstNode;
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
import graef.foltk.formula.ast.term.Term;
import graef.foltk.formula.ast.term.VariableTerm;

public class BaseRecursiveTermTransformation implements Transformation<Term> {

	@Override
	public Term transform(Transformer<Term> rewriter, Symbol symbol) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Term transform(Transformer<Term> rewriter, AndProposition and) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Term transform(Transformer<Term> rewriter, OrProposition or) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Term transform(Transformer<Term> rewriter,
			ImplicationProposition impl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Term transform(Transformer<Term> rewriter,
			BiconditionalProposition bicond) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Term transform(Transformer<Term> rewriter,
			ConstantProposition constant) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Term transform(Transformer<Term> rewriter,
			ExistentialQuantifiedProposition exists) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Term transform(Transformer<Term> rewriter,
			UniversalQuantifiedProposition univ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Term transform(Transformer<Term> rewriter, NotProposition not) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Term transform(Transformer<Term> rewriter,
			PredicateProposition predicate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Term transform(Transformer<Term> rewriter, FunctionTerm func) {
		List<Term> arguments = rewriter.rewriteList(func.getArguments());
		return new FunctionTerm(func.getToken(), func.getSymbol(), arguments);
	}

	@Override
	public Term transform(Transformer<Term> rewriter, VariableTerm var) {
		return var;
	}
}

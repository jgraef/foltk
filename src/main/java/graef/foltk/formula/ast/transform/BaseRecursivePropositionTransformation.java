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

public class BaseRecursivePropositionTransformation implements Transformation<Proposition> {

	@Override
	public Proposition transform(Rewriter<Proposition> rewriter, AndProposition and) {
		return new AndProposition(and.getToken(), rewriter.rewrite(and.getOperand(0)), rewriter.rewrite(and.getOperand(1)));
	}

	@Override
	public Proposition transform(Rewriter<Proposition> rewriter, OrProposition or) {
		return new OrProposition(or.getToken(), rewriter.rewrite(or.getOperand(0)), rewriter.rewrite(or.getOperand(1)));
	}

	@Override
	public Proposition transform(Rewriter<Proposition> rewriter, ImplicationProposition impl) {
		return new ImplicationProposition(impl.getToken(), rewriter.rewrite(impl.getOperand(0)), rewriter.rewrite(impl.getOperand(1)));
	}

	@Override
	public Proposition transform(Rewriter<Proposition> rewriter, BiconditionalProposition bicond) {
		return new BiconditionalProposition(bicond.getToken(), rewriter.rewrite(bicond.getOperand(0)), rewriter.rewrite(bicond.getOperand(1)));
	}

	@Override
	public Proposition transform(Rewriter<Proposition> rewriter, ConstantProposition constant) {
		return constant;
	}

	@Override
	public Proposition transform(Rewriter<Proposition> rewriter,
			ExistentialQuantifiedProposition exists) {
		return new ExistentialQuantifiedProposition(exists.getToken(), exists.getVariable(), rewriter.rewrite(exists.getOperand(0)), exists.getScope());
	}

	@Override
	public Proposition transform(Rewriter<Proposition> rewriter,
			UniversalQuantifiedProposition univ) {
		return new UniversalQuantifiedProposition(univ.getToken(), univ.getVariable(), rewriter.rewrite(univ.getOperand(0)), univ.getScope());
	}

	@Override
	public Proposition transform(Rewriter<Proposition> rewriter, NotProposition not) {
		return new NotProposition(not.getToken(), not.getOperand(0));
	}

	@Override
	public Proposition transform(Rewriter<Proposition> rewriter,
			PredicateProposition predicate) {
		return predicate;
	}

	@Override
	public Proposition transform(Rewriter<Proposition> rewriter, Symbol symbol) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Proposition transform(Rewriter<Proposition> rewriter,
			FunctionTerm func) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Proposition transform(Rewriter<Proposition> rewriter,
			VariableTerm var) {
		// TODO Auto-generated method stub
		return null;
	}

}

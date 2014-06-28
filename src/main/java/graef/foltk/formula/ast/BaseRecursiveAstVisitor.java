package graef.foltk.formula.ast;

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
import graef.foltk.formula.parser.Import;

public class BaseRecursiveAstVisitor implements AstVisitor {
	public void visitProposition(Proposition p) {
		for (Proposition c: p.getOperands()) {
			c.accept(this);
		}
	}
	
	@Override
	public void visit(Symbol symbol) {
	}

	@Override
	public void visit(AndProposition and) {
		visitProposition(and);
	}

	@Override
	public void visit(OrProposition or) {
		visitProposition(or);
	}

	@Override
	public void visit(ImplicationProposition impl) {
		visitProposition(impl);
	}

	@Override
	public void visit(BiconditionalProposition bicond) {
		visitProposition(bicond);
	}

	@Override
	public void visit(ConstantProposition constant) {
		visitProposition(constant);
	}

	@Override
	public void visit(ExistentialQuantifiedProposition exists) {
		visitProposition(exists);
	}

	@Override
	public void visit(UniversalQuantifiedProposition univ) {
		visitProposition(univ);
	}

	@Override
	public void visit(NotProposition not) {
		visitProposition(not);
	}

	@Override
	public void visit(PredicateProposition predicate) {
		for (Term t: predicate.getTerms()) {
			t.accept(this);
		}
	}

	@Override
	public void visit(FunctionTerm func) {
		for (Term t: func.getArguments()) {
			t.accept(this);
		}
	}

	@Override
	public void visit(VariableTerm var) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Import imprt) {
		// TODO Auto-generated method stub
		
	}

}

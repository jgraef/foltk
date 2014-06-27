package graef.foltk.formula.ast;

import graef.foltk.formula.ast.proposition.AndProposition;
import graef.foltk.formula.ast.proposition.BiconditionalProposition;
import graef.foltk.formula.ast.proposition.BinaryProposition;
import graef.foltk.formula.ast.proposition.ConstantProposition;
import graef.foltk.formula.ast.proposition.ExistentialQuantifiedProposition;
import graef.foltk.formula.ast.proposition.ImplicationProposition;
import graef.foltk.formula.ast.proposition.NotProposition;
import graef.foltk.formula.ast.proposition.OrProposition;
import graef.foltk.formula.ast.proposition.PredicateProposition;
import graef.foltk.formula.ast.proposition.Proposition;
import graef.foltk.formula.ast.proposition.QuantifiedProposition;
import graef.foltk.formula.ast.proposition.UniversalQuantifiedProposition;
import graef.foltk.formula.ast.term.FunctionTerm;
import graef.foltk.formula.ast.term.VariableTerm;

public interface AstVisitor {
	public void visit(Symbol symbol);
	public void visit(AndProposition and);
	public void visit(OrProposition or);
	public void visit(ImplicationProposition impl);
	public void visit(BiconditionalProposition bicond);
	public void visit(ConstantProposition constant);
	public void visit(ExistentialQuantifiedProposition exists);
	public void visit(UniversalQuantifiedProposition univ);
	public void visit(NotProposition not);
	public void visit(PredicateProposition predicate);
	public void visit(FunctionTerm func);
	public void visit(VariableTerm var);
}

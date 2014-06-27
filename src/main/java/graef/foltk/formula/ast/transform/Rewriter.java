package graef.foltk.formula.ast.transform;

import graef.foltk.formula.ast.AstNode;
import graef.foltk.formula.ast.AstVisitor;
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

import java.util.LinkedList;

public class Rewriter<T> {
	private final Transformation<T> transformation;
	private final LinkedList<T> stack;
	private final AstVisitor visitor;
	
	class RewriteVisitor implements AstVisitor {

		@Override
		public void visit(Symbol symbol) {
			stack.push(transformation.transform(Rewriter.this, symbol));
		}

		@Override
		public void visit(AndProposition and) {
			stack.push(transformation.transform(Rewriter.this, and));
		}

		@Override
		public void visit(OrProposition or) {
			stack.push(transformation.transform(Rewriter.this, or));			
		}

		@Override
		public void visit(ImplicationProposition impl) {
			stack.push(transformation.transform(Rewriter.this, impl));			
		}

		@Override
		public void visit(BiconditionalProposition bicond) {
			stack.push(transformation.transform(Rewriter.this, bicond));			
		}

		@Override
		public void visit(ConstantProposition constant) {
			stack.push(transformation.transform(Rewriter.this, constant));
		}

		@Override
		public void visit(ExistentialQuantifiedProposition exists) {
			stack.push(transformation.transform(Rewriter.this, exists));
		}

		@Override
		public void visit(UniversalQuantifiedProposition univ) {
			stack.push(transformation.transform(Rewriter.this, univ));			
		}

		@Override
		public void visit(NotProposition not) {
			stack.push(transformation.transform(Rewriter.this, not));
		}

		@Override
		public void visit(PredicateProposition predicate) {
			stack.push(transformation.transform(Rewriter.this, predicate));
		}

		@Override
		public void visit(FunctionTerm func) {
			stack.push(transformation.transform(Rewriter.this, func));
		}

		@Override
		public void visit(VariableTerm var) {
			stack.push(transformation.transform(Rewriter.this, var));
		}
		
	}
	
	
	public Rewriter(Transformation<T> transformation) {
		this.transformation = transformation;
		stack = new LinkedList<>();
		visitor = new RewriteVisitor();
	}
	
	public T rewrite(AstNode node) {
		node.accept(visitor);
		return stack.pop();
	}

}

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
import graef.foltk.formula.parser.Import;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Transformer<T> {
	private final Transformation<T> transformation;
	private final LinkedList<T> stack;
	private final AstVisitor visitor;
	
	class TransformerVisitor implements AstVisitor {

		@Override
		public void visit(Symbol symbol) {
			stack.push(transformation.transform(Transformer.this, symbol));
		}

		@Override
		public void visit(AndProposition and) {
			stack.push(transformation.transform(Transformer.this, and));
		}

		@Override
		public void visit(OrProposition or) {
			stack.push(transformation.transform(Transformer.this, or));			
		}

		@Override
		public void visit(ImplicationProposition impl) {
			stack.push(transformation.transform(Transformer.this, impl));			
		}

		@Override
		public void visit(BiconditionalProposition bicond) {
			stack.push(transformation.transform(Transformer.this, bicond));			
		}

		@Override
		public void visit(ConstantProposition constant) {
			stack.push(transformation.transform(Transformer.this, constant));
		}

		@Override
		public void visit(ExistentialQuantifiedProposition exists) {
			stack.push(transformation.transform(Transformer.this, exists));
		}

		@Override
		public void visit(UniversalQuantifiedProposition univ) {
			stack.push(transformation.transform(Transformer.this, univ));			
		}

		@Override
		public void visit(NotProposition not) {
			stack.push(transformation.transform(Transformer.this, not));
		}

		@Override
		public void visit(PredicateProposition predicate) {
			stack.push(transformation.transform(Transformer.this, predicate));
		}

		@Override
		public void visit(FunctionTerm func) {
			stack.push(transformation.transform(Transformer.this, func));
		}

		@Override
		public void visit(VariableTerm var) {
			stack.push(transformation.transform(Transformer.this, var));
		}

		@Override
		public void visit(Import imprt) {
		}
		
	}
	
	
	public Transformer(Transformation<T> transformation) {
		this.transformation = transformation;
		stack = new LinkedList<>();
		visitor = new TransformerVisitor();
	}
	
	public synchronized T rewrite(AstNode node) {
		node.accept(visitor);
		return stack.pop();
	}
	
	public List<T> rewriteList(List<? extends AstNode> nodes) {
		List<T> rnodes = new ArrayList<T>(nodes.size());
		for (AstNode n: nodes) {
			rnodes.add(rewrite(n));
		}
		return rnodes;
	}

}

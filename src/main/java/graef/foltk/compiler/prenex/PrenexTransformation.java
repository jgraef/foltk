package graef.foltk.compiler.prenex;

import java.util.ArrayList;
import java.util.List;

import graef.foltk.formula.ast.AstNode;
import graef.foltk.formula.ast.AstVisitor;
import graef.foltk.formula.ast.proposition.AndProposition;
import graef.foltk.formula.ast.proposition.BiconditionalProposition;
import graef.foltk.formula.ast.proposition.ExistentialQuantifiedProposition;
import graef.foltk.formula.ast.proposition.ImplicationProposition;
import graef.foltk.formula.ast.proposition.NotProposition;
import graef.foltk.formula.ast.proposition.OrProposition;
import graef.foltk.formula.ast.proposition.Proposition;
import graef.foltk.formula.ast.proposition.QuantifiedProposition;
import graef.foltk.formula.ast.proposition.UniversalQuantifiedProposition;
import graef.foltk.formula.ast.transform.BaseRecursivePropositionTransformation;
import graef.foltk.formula.ast.transform.NullTransformation;
import graef.foltk.formula.ast.transform.Rewriter;
import graef.foltk.formula.ast.transform.Transformation;
import graef.foltk.formula.lexer.Token;

/**
 * TODO:
 *   Quantoren können viel effezienter weggeknuschpert werden. Einfach den Baum in pre-order ablaufen
 *   und Quantoren entfernen - dabei aber auch notieren.
 * 
 * @author janosch
 *
 */
public class PrenexTransformation {
	/* Step 1: Eliminate biconditional */
	class PrenexStep1 extends BaseRecursivePropositionTransformation {
		@Override
		public Proposition transform(Rewriter<Proposition> rewriter, BiconditionalProposition bicond) {
			// a <=> b  =  (a => b) & (b => a)
			Proposition a = rewriter.rewrite(bicond.getOperand(0));
			Proposition b = rewriter.rewrite(bicond.getOperand(1));
			return new AndProposition(Token.DUMMY,
					new ImplicationProposition(Token.DUMMY, a, b),
					new ImplicationProposition(Token.DUMMY, b, a));
		}
	}
	
	
	/* Step 2: Eliminate implication */
	class PrenexStep2 extends BaseRecursivePropositionTransformation {
		@Override
		public Proposition transform(Rewriter<Proposition> rewriter, ImplicationProposition impl) {
			// a => b  =  !a | b
			return new OrProposition(Token.DUMMY,
					new NotProposition(Token.DUMMY, rewriter.rewrite(impl.getOperand(0))),
					rewriter.rewrite(impl.getOperand(1)));
		}
	}
	
	
	/* Step 3: Move negation inwards */
	class PrenexStep3 extends BaseRecursivePropositionTransformation {
		class PrenexStep3Part2 extends NullTransformation<Proposition> {
			@Override
			public Proposition transform(Rewriter<Proposition> rewriter, NotProposition not) {
				// !!x  =  x
				return not.getOperand(0);
			}
			
			@Override
			public Proposition transform(Rewriter<Proposition> rewriter, AndProposition and) {
				// !(x & y)  =  !x | !y
				return new OrProposition(Token.DUMMY,
						new NotProposition(Token.DUMMY, and.getOperand(0)),
						new NotProposition(Token.DUMMY, and.getOperand(1)));
			}
			
			@Override
			public Proposition transform(Rewriter<Proposition> rewriter, OrProposition or) {
				// !(x | y)  =  !x & !y
				return new AndProposition(Token.DUMMY,
						new NotProposition(Token.DUMMY, or.getOperand(0)),
						new NotProposition(Token.DUMMY, or.getOperand(1)));
			}
		}
		
		private Rewriter<Proposition> r2 = new Rewriter<>(new PrenexStep3Part2());
		
		@Override
		public Proposition transform(Rewriter<Proposition> rewriter, NotProposition not) {
			// !x = r2(x)
			// for r2 see rewriting rules above
			Proposition p = r2.rewrite(not.getOperand(0));
			return p == null ? not : p;
		}
	}
	
	/**
	 * Step 4: move quantifiers outwards
	 * remove quantifiers while collecting them.
	 * This assumes that {@link BaseRecursivePropositionTransformation} actually visits parents
	 * before their children.
	 */
	class PrenexStep4 extends BaseRecursivePropositionTransformation {
		private final ArrayList<Quantifier> quantifiers = new ArrayList<Quantifier>();
		
		@Override
		public Proposition transform(Rewriter<Proposition> rewriter, UniversalQuantifiedProposition univ) {
			quantifiers.add(new UniversalQuantifier(univ));
			return univ.getOperand(0);
		}
		
		@Override
		public Proposition transform(Rewriter<Proposition> rewriter, ExistentialQuantifiedProposition exists) {
			quantifiers.add(new ExistentialQuantifier(exists));
			return exists.getOperand(0);
		}
		
		public List<Quantifier> getQuantifiers() {
			return quantifiers;
		}
	}
	
	
	private final List<Transformation<Proposition>> steps;
	private final PrenexStep4 step4;
	
	public PrenexTransformation() {
		steps = new ArrayList<>(3);
		steps.add(new PrenexStep1());
		steps.add(new PrenexStep2());
		steps.add(new PrenexStep3());
		step4 = new PrenexStep4();
	}
	
	public PrenexNormalForm transform(Proposition p) {
		AstNode node = p;
		for (Transformation<Proposition> t: steps) {
			Rewriter<Proposition> r = new Rewriter<>(t);
			node = r.rewrite(node);
		}
		
		Rewriter<Proposition> r = new Rewriter<>(step4);
		Proposition matrix = (Proposition)r.rewrite(node);
		List<Quantifier> quantifiers = step4.getQuantifiers();
		return new PrenexNormalForm(quantifiers, matrix);
	}
}

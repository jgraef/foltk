package graef.foltk.compiler.prenex;

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
	
	/* Step 4: move quantifiers outwards */
	class PrenexStep4 extends BaseRecursivePropositionTransformation {
		// TODO: rename variables!
		// maybe not necessary, since scopes are preserved
		
		private int ai, bi;
		
		/**
		 * @param reversed  If true, move quantifiers in second operator outwards
		 */
		public PrenexStep4(boolean reversed) {
			ai = reversed ? 1 : 0;
			bi = 1 - ai;
		}
		
		/**
		 * This transformation actually transforms by moving the quantifier outwards.
		 * Which operator that has to be moved inwards is specified by overwriting the body method
		 */
		abstract class PrenexStep3Part2 extends NullTransformation<Proposition> {
			protected Proposition b;
			
			public PrenexStep3Part2(Proposition b) {
				this.b = b;
			}
			
			public abstract Proposition body(QuantifiedProposition q);
			
			@Override
			public Proposition transform(Rewriter<Proposition> rewriter, UniversalQuantifiedProposition univ) {
				return new UniversalQuantifiedProposition(Token.DUMMY, univ.getVariable(), body(univ), univ.getScope());
			}
			
			@Override
			public Proposition transform(Rewriter<Proposition> rewriter, ExistentialQuantifiedProposition exists) {
				return new ExistentialQuantifiedProposition(Token.DUMMY, exists.getVariable(), body(exists), exists.getScope());
			}
		}
		
		@Override
		/**
		 * Apply transformation for and
		 */
		public Proposition transform(final Rewriter<Proposition> rewriter, AndProposition and) {
			Proposition a = rewriter.rewrite(and.getOperand(ai));
			Proposition b = rewriter.rewrite(and.getOperand(bi));
			Rewriter<Proposition> r2 = new Rewriter<>(new PrenexStep3Part2(b) {
				@Override
				public Proposition body(QuantifiedProposition q) {
					return (Proposition)rewriter.rewrite(new AndProposition(Token.DUMMY, q.getOperand(0), b));
				}
			});
			Proposition p = r2.rewrite(a);
			return p == null ? and : p;
		}
		
		@Override
		/**
		 * Apply transformation for or
		 */
		public Proposition transform(final Rewriter<Proposition> rewriter, OrProposition or) {
			Proposition a = rewriter.rewrite(or.getOperand(ai));
			Proposition b = rewriter.rewrite(or.getOperand(bi));
			Rewriter<Proposition> r2 = new Rewriter<>(new PrenexStep3Part2(b) {
				@Override
				public Proposition body(QuantifiedProposition q) {
					return (Proposition)rewriter.rewrite(new OrProposition(Token.DUMMY, q.getOperand(0), b));
				}
			});
			Proposition p = r2.rewrite(a);
			return p == null ? or : p;
		}
	}
	
	
	public PrenexTransformation() {

	}
	
	public Proposition transform(Proposition p) {
		throw new UnsupportedOperationException("not yet implemented");
	}
}

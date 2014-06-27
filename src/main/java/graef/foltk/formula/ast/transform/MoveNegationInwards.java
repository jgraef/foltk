package graef.foltk.formula.ast.transform;

import graef.foltk.formula.ast.proposition.AndProposition;
import graef.foltk.formula.ast.proposition.ExistentialQuantifiedProposition;
import graef.foltk.formula.ast.proposition.NotProposition;
import graef.foltk.formula.ast.proposition.OrProposition;
import graef.foltk.formula.ast.proposition.Proposition;
import graef.foltk.formula.ast.proposition.UniversalQuantifiedProposition;
import graef.foltk.formula.lexer.Token;
import graef.foltk.formula.lexer.TokenType;

/**
 * @note Assumes that biconditionals and implications are already eliminated
 */
public class MoveNegationInwards extends BaseRecursivePropositionTransformation {
	class InnerTransformation extends NullTransformation<Proposition> {
		@Override
		public Proposition transform(Transformer<Proposition> rewriter,
				NotProposition not) {
			// !!x = x
			return not.getOperand(0);
		}

		@Override
		public Proposition transform(Transformer<Proposition> rewriter,
				AndProposition and) {
			// !(x & y) = !x | !y
			return new OrProposition(new Token(TokenType.OR, "|"),
					new NotProposition(new Token(TokenType.NOT, "!"),
							and.getOperand(0)), new NotProposition(new Token(
							TokenType.NOT, "!"), and.getOperand(1)));
		}

		@Override
		public Proposition transform(Transformer<Proposition> rewriter,
				OrProposition or) {
			// !(x | y) = !x & !y
			return new AndProposition(new Token(TokenType.AND, "&"),
					new NotProposition(new Token(TokenType.NOT, "!"),
							or.getOperand(0)), new NotProposition(new Token(
							TokenType.NOT, "!"), or.getOperand(1)));
		}

		@Override
		public Proposition transform(Transformer<Proposition> rewriter,
				UniversalQuantifiedProposition univ) {
			return new ExistentialQuantifiedProposition(new Token(
					TokenType.EXISTS, "exists"), univ.getVariable(),
					new NotProposition(new Token(TokenType.NOT, "!"), univ
							.getOperand(0)), univ.getScope());
		}
		
		@Override
		public Proposition transform(Transformer<Proposition> rewriter,
				ExistentialQuantifiedProposition exists) {
			return new UniversalQuantifiedProposition(new Token(
					TokenType.FORALL, "forall"), exists.getVariable(),
					new NotProposition(new Token(TokenType.NOT, "!"), exists
							.getOperand(0)), exists.getScope());
		}
	}

	private Transformer<Proposition> r2 = new Transformer<>(
			new InnerTransformation());

	@Override
	public Proposition transform(Transformer<Proposition> rewriter,
			NotProposition not) {
		// !x = r2(x)
		// for r2 see rewriting rules above
		Proposition p = r2.rewrite(not.getOperand(0));
		return p == null ? not : rewriter.rewrite(p);
	}
}

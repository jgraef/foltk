package graef.foltk.formula.ast.transform;

import graef.foltk.formula.ast.proposition.ImplicationProposition;
import graef.foltk.formula.ast.proposition.NotProposition;
import graef.foltk.formula.ast.proposition.OrProposition;
import graef.foltk.formula.ast.proposition.Proposition;
import graef.foltk.formula.lexer.Token;
import graef.foltk.formula.lexer.TokenType;

public class EliminateImplication extends
		BaseRecursivePropositionTransformation {
	@Override
	public Proposition transform(Transformer<Proposition> rewriter,
			ImplicationProposition impl) {
		// a => b = !a | b
		return new OrProposition(new Token(TokenType.OR, "|"), new NotProposition(new Token(TokenType.NOT, "!"),
				rewriter.rewrite(impl.getOperand(0))), rewriter.rewrite(impl
				.getOperand(1)));
	}
}

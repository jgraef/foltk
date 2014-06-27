package graef.foltk.formula.ast.transform;

import graef.foltk.formula.ast.proposition.AndProposition;
import graef.foltk.formula.ast.proposition.BiconditionalProposition;
import graef.foltk.formula.ast.proposition.ImplicationProposition;
import graef.foltk.formula.ast.proposition.Proposition;
import graef.foltk.formula.lexer.Token;
import graef.foltk.formula.lexer.TokenType;

public class EliminateBiconditional extends BaseRecursivePropositionTransformation {
	@Override
	public Proposition transform(Transformer<Proposition> rewriter, BiconditionalProposition bicond) {
		// a <=> b  =  (a => b) & (b => a)
		Proposition a = rewriter.rewrite(bicond.getOperand(0));
		Proposition b = rewriter.rewrite(bicond.getOperand(1));
		return new AndProposition(new Token(TokenType.AND, "&"),
				new ImplicationProposition(new Token(TokenType.IMPL, "=>"), a, b),
				new ImplicationProposition(new Token(TokenType.IMPL, "=>"), b, a));
	}
}

package graef.foltk.compiler.prenex;

import java.util.ArrayList;
import java.util.List;

import graef.foltk.formula.ast.AstNode;
import graef.foltk.formula.ast.proposition.AndProposition;
import graef.foltk.formula.ast.proposition.BiconditionalProposition;
import graef.foltk.formula.ast.proposition.ExistentialQuantifiedProposition;
import graef.foltk.formula.ast.proposition.ImplicationProposition;
import graef.foltk.formula.ast.proposition.NotProposition;
import graef.foltk.formula.ast.proposition.OrProposition;
import graef.foltk.formula.ast.proposition.Proposition;
import graef.foltk.formula.ast.proposition.UniversalQuantifiedProposition;
import graef.foltk.formula.ast.transform.BaseRecursivePropositionTransformation;
import graef.foltk.formula.ast.transform.EliminateBiconditional;
import graef.foltk.formula.ast.transform.EliminateImplication;
import graef.foltk.formula.ast.transform.MoveNegationInwards;
import graef.foltk.formula.ast.transform.NullTransformation;
import graef.foltk.formula.ast.transform.Transformer;
import graef.foltk.formula.ast.transform.Transformation;
import graef.foltk.formula.lexer.Token;


public class PrenexTransformation {
	/**
	 * Step 4: move quantifiers outwards
	 * remove quantifiers while collecting them.
	 * This assumes that {@link BaseRecursivePropositionTransformation} actually visits parents
	 * before their children.
	 */
	class CollectQuantifiers extends BaseRecursivePropositionTransformation {
		private final ArrayList<Quantifier> quantifiers = new ArrayList<Quantifier>();
		
		@Override
		public Proposition transform(Transformer<Proposition> rewriter, UniversalQuantifiedProposition univ) {
			quantifiers.add(new UniversalQuantifier(univ));
			return rewriter.rewrite(univ.getOperand(0));
		}
		
		@Override
		public Proposition transform(Transformer<Proposition> rewriter, ExistentialQuantifiedProposition exists) {
			quantifiers.add(new ExistentialQuantifier(exists));
			return rewriter.rewrite(exists.getOperand(0));
		}
		
		public List<Quantifier> getQuantifiers() {
			return quantifiers;
		}
	}
	
	
	private final List<Transformation<Proposition>> steps;
	private final CollectQuantifiers step4;
	
	public PrenexTransformation() {
		steps = new ArrayList<>(3);
		steps.add(new EliminateBiconditional());
		steps.add(new EliminateImplication());
		steps.add(new MoveNegationInwards());
		step4 = new CollectQuantifiers();
	}
	
	public PrenexNormalForm transform(Proposition p) {
		AstNode node = p;
		for (Transformation<Proposition> t: steps) {
			Transformer<Proposition> r = new Transformer<>(t);
			node = r.rewrite(node);
		}
		
		Transformer<Proposition> r = new Transformer<>(step4);
		Proposition matrix = (Proposition)r.rewrite(node);
		List<Quantifier> quantifiers = step4.getQuantifiers();
		return new PrenexNormalForm(quantifiers, matrix);
	}
}

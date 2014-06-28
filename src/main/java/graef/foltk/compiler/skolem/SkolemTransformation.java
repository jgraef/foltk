package graef.foltk.compiler.skolem;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import graef.foltk.compiler.prenex.ExistentialQuantifier;
import graef.foltk.compiler.prenex.PrenexNormalForm;
import graef.foltk.compiler.prenex.Quantifier;
import graef.foltk.compiler.prenex.QuantorVisitor;
import graef.foltk.compiler.prenex.UniversalQuantifier;
import graef.foltk.formula.ast.Symbol;
import graef.foltk.formula.ast.proposition.PredicateProposition;
import graef.foltk.formula.ast.proposition.Proposition;
import graef.foltk.formula.ast.term.FunctionTerm;
import graef.foltk.formula.ast.term.Term;
import graef.foltk.formula.ast.term.VariableTerm;
import graef.foltk.formula.ast.transform.BaseRecursivePropositionTransformation;
import graef.foltk.formula.ast.transform.BaseRecursiveTermTransformation;
import graef.foltk.formula.ast.transform.Transformer;
import graef.foltk.formula.lexer.Token;
import graef.foltk.formula.lexer.TokenType;

public class SkolemTransformation {
	private int functionId = 1;
	
	class TermTransformation extends BaseRecursiveTermTransformation {
		private final List<UniversalQuantifier> univ;
		private final ExistentialQuantifier exist;
		private final String functionName;
		
		public TermTransformation(List<UniversalQuantifier> univ, ExistentialQuantifier exist) {
			this.univ = univ;
			this.exist = exist;
			this.functionName = "_s" + functionId;
			functionId++;
		}
		
		@Override
		public Term transform(Transformer<Term> transformer, VariableTerm var) {
			System.out.println("skolemizing variable " + exist.getVariable());
			System.out.println("found variable: " + var);
			if (var.getSymbol().equals(exist.getVariable())) {
				Token t = new Token(TokenType.SYMBOL, functionName);
				List<Term> depVars = new ArrayList<>();
				for (UniversalQuantifier uq: univ) {
					Symbol s = uq.getVariable();
					depVars.add(new VariableTerm(new Token(TokenType.SYMBOL, s.getIdentifier()), s));
				}
				return new FunctionTerm(t, new Symbol(t, functionName), depVars);
			}
			else {
				return var;
			}
		}
	}
	
	class PropositionTransformation extends BaseRecursivePropositionTransformation {
		private final Transformer<Term> r2;
		
		public PropositionTransformation(List<UniversalQuantifier> univ, ExistentialQuantifier e) {
			this.r2 = new Transformer<>(new TermTransformation(univ, e));
		}
		
		@Override
		public Proposition transform(Transformer<Proposition> transformer, PredicateProposition predicate) {
			List<Term> terms = r2.rewriteList(predicate.getTerms());
			return new PredicateProposition(predicate.getToken(), predicate.getSymbol(), terms);
		}
	}
	
	class VisitQuantifiers implements QuantorVisitor {
		private List<UniversalQuantifier> universal = new ArrayList<UniversalQuantifier>();
		private Proposition matrix;
		
		public VisitQuantifiers(Proposition matrix) {
			this.matrix = matrix;
		}
		
		@Override
		public void visit(ExistentialQuantifier exists) {
			System.out.println("found existential quantifier: " + exists + " " + matrix);
			Transformer<Proposition> r = new Transformer<>(new PropositionTransformation(universal, exists));
			matrix = r.rewrite(matrix);
			System.out.println("rewrote matrix to: " + matrix);
		}

		@Override
		public void visit(UniversalQuantifier univ) {
			System.out.println("found universal quantifier: " + univ);
			universal.add(univ);
		}
		
		public Proposition getMatrix() {
			return matrix;
		}
		
		public List<UniversalQuantifier> getUniversalQuantifiers() {
			return universal;
		}
		
	}
	
	
	public static SkolemTransformation INSTANCE = new SkolemTransformation();
	public SkolemTransformation() {
	}
	
	public SkolemNormalForm transform(PrenexNormalForm pnf) {
		VisitQuantifiers vq = new VisitQuantifiers(pnf.getMatrix());
		System.out.println("Prenex Normal Form: " + pnf.getMatrix());
		for (Quantifier q: pnf.getQuantifiers()) {
			System.out.println("> " + q);
			q.accept(vq);
		}
		return new SkolemNormalForm(vq.getUniversalQuantifiers(), vq.getMatrix());
	}
}

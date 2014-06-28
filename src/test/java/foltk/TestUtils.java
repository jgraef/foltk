package foltk;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import graef.foltk.compiler.prenex.PrenexNormalForm;
import graef.foltk.compiler.prenex.PrenexTransformation;
import graef.foltk.compiler.skolem.SkolemNormalForm;
import graef.foltk.compiler.skolem.SkolemTransformation;
import graef.foltk.formula.ast.AstVisitor;
import graef.foltk.formula.ast.BaseRecursiveAstVisitor;
import graef.foltk.formula.ast.Declaration;
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
import graef.foltk.formula.lexer.Lexer;
import graef.foltk.formula.lexer.LexerException;
import graef.foltk.formula.parser.Parser;
import graef.foltk.formula.parser.ParserException;
import graef.foltk.formula.parser.TypeException;
import static org.junit.Assert.*;

public class TestUtils {
	public static Parser createParser(String code) throws IOException, LexerException {
		Lexer l = new Lexer("test", new StringReader(code));
		return new Parser(l);
	}
	
	public static Proposition parse(String code) throws IOException, LexerException, ParserException, TypeException {
		return createParser(code).parseProposition();
	}
	
	public static PrenexNormalForm parseToPrenex(String code) throws IOException, LexerException, ParserException, TypeException {
		Proposition prop = TestUtils.parse(code);
		return new PrenexTransformation().transform(prop);
	}
	
	public static SkolemNormalForm parseToSkolem(String code) throws IOException, LexerException, ParserException, TypeException {
		PrenexNormalForm pnf = TestUtils.parseToPrenex(code);
		return new SkolemTransformation().transform(pnf);
	}

	public static List<Declaration> parseToDeclarations(String code) throws IOException, LexerException, ParserException, TypeException {
		Parser parser = TestUtils.createParser(code);
		Proposition prop = parser.parseProposition();
		final List<Declaration> declarations = new ArrayList<>(parser.getRootScope().getDeclarations());
		prop.accept(new BaseRecursiveAstVisitor() {
			@Override
			public void visit(UniversalQuantifiedProposition univ) {
				/*System.out.println("found univ: " + univ);
				for (Declaration d: univ.getScope().getDeclarations()) {
					System.out.println("> " + d);
				}*/
				declarations.addAll(univ.getScope().getDeclarations());
				super.visit(univ);
			}
			
			@Override
			public void visit(ExistentialQuantifiedProposition exists) {
				declarations.addAll(exists.getScope().getDeclarations());
				super.visit(exists);
			}
			
			@Override
			public void visit(PredicateProposition pred) {
			}
		});
		
		Collections.sort(declarations, new Comparator<Declaration>() {
			@Override
			public int compare(Declaration d1, Declaration d2) {
				return d1.getSymbol().compareTo(d2.getSymbol());
			}
		});
		
		return declarations;
	}
	
	public static void assertEqualsList(List<?> a, List<?> b) {
		Iterator<?> ia = a.iterator();
		Iterator<?> ib = b.iterator();
		for (int i = 0; ia.hasNext() || ib.hasNext(); i++) {
			if (!ia.hasNext() && ib.hasNext()) {
				fail("List is longer than expected: " + ib.next());
			}
			else if (ia.hasNext() && !ib.hasNext()) {
				fail("List is shorter than expexted: " + ia.next());
			}
			else {
				assertEquals("Element at index " + i + " differ", ia.next(), ib.next());
			}
		}
		
	}
}

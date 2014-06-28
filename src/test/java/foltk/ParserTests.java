package foltk;

import static org.junit.Assert.*;
import graef.foltk.formula.ast.AstVisitor;
import graef.foltk.formula.ast.Declaration;
import graef.foltk.formula.ast.Symbol;
import graef.foltk.formula.ast.SymbolType;
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

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class ParserTests {
	@Test
	public void testTerm() throws IOException, LexerException, ParserException, TypeException {
		String s = "Dog(Lassie);\n"
				+ "Chases(Lassie, Garfield);\n"
				+ "Eats(Garfield, FoodOf(Garfield));\n";
		Proposition p = TestUtils.parse(s);	
	}
	
	@Test
	public void testProposition() throws IOException, LexerException, ParserException, TypeException {
		String s = "A | !B & C;\n";
		Proposition p = TestUtils.parse(s);
		assertEquals("(A) | ((!(B)) & (C))", p.toString());
	}
	
	//@Test
	public void testPropositionComplex() throws IOException, LexerException, ParserException, TypeException {
		String s = "!((forall x A => B => P(x)) | (C & (!D <=> exists x P(x))));\n";		
		Proposition p = TestUtils.parse(s);
	
		// TODO
		System.out.println(p.toString());
		assertEquals("(A) | ((!(B)) & (C))", p.toString());
	}
	
	@Test
	public void testImplication() throws IOException, LexerException, ParserException, TypeException {
		String s = "A => B => C;\n";
		Proposition p = TestUtils.parse(s);
		assertEquals("(A) => ((B) => (C))", p.toString());
	}
	
	@Test
	public void testDeclarations() throws IOException, LexerException, ParserException, TypeException {
		String s = "forall x exists y P(x, f(y), c) & A";
		List<Declaration> got = TestUtils.parseToDeclarations(s);
		
		for (Declaration d: got) {
			System.out.println(d);
		}
		
		List<Declaration> expected = new ArrayList<>();
		expected.add(new Declaration("A", SymbolType.PREDICATE, 0));
		expected.add(new Declaration("P", SymbolType.PREDICATE, 3));
		expected.add(new Declaration("c", SymbolType.FUNCTION, 0));
		expected.add(new Declaration("f", SymbolType.FUNCTION, 1));
		expected.add(new Declaration("x", SymbolType.VARIABLE, 0));
		expected.add(new Declaration("y", SymbolType.VARIABLE, 0));
		TestUtils.assertEqualsList(expected, got);
	}

}

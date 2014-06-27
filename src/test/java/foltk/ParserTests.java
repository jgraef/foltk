package foltk;

import static org.junit.Assert.*;
import graef.foltk.formula.ast.Declaration;
import graef.foltk.formula.ast.SymbolType;
import graef.foltk.formula.ast.proposition.Proposition;
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
	public Parser createParser(String code) throws IOException, LexerException {
		Lexer l = new Lexer("test", new StringReader(code));
		return new Parser(l);
	}	
	
	@Test
	public void testTerm() throws IOException, LexerException, ParserException, TypeException {
		String s = "Dog(Lassie);\n"
				+ "Chases(Lassie, Garfield);\n"
				+ "Eats(Garfield, FoodOf(Garfield));\n";
		
		Parser p = createParser(s);
		p.parseUnit();		
	}
	
	@Test
	public void testProposition() throws IOException, LexerException, ParserException, TypeException {
		String s = "A | !B & C;\n";
		
		Parser parser = createParser(s);
		List<Proposition> unit = parser.parseUnit();
		Proposition p = unit.iterator().next();
	
		assertEquals("(A) | ((!(B)) & (C))", p.toString());
	}
	
	//@Test
	public void testPropositionComplex() throws IOException, LexerException, ParserException, TypeException {
		String s = "!((forall x A => B => P(x)) | (C & (!D <=> exists x P(x))));\n";
		
		Parser parser = createParser(s);
		List<Proposition> unit = parser.parseUnit();
		Proposition p = unit.iterator().next();
	
		// TODO
		System.out.println(p.toString());
		assertEquals("(A) | ((!(B)) & (C))", p.toString());
	}
	
	@Test
	public void testImplication() throws IOException, LexerException, ParserException, TypeException {
		String s = "A => B => C;\n";
		
		Parser parser = createParser(s);
		List<Proposition> unit = parser.parseUnit();
		Proposition p = unit.iterator().next();
	
		assertEquals("(A) => ((B) => (C))", p.toString());
	}

}

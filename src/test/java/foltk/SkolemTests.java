package foltk;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import graef.foltk.compiler.skolem.SkolemNormalForm;
import graef.foltk.formula.lexer.LexerException;
import graef.foltk.formula.parser.ParserException;
import graef.foltk.formula.parser.TypeException;

public class SkolemTests {
	@Test
	public void testSimple() throws IOException, LexerException, ParserException, TypeException {
		SkolemNormalForm snf = TestUtils.parseToSkolem("exists x P(x)");
		assertEquals("P(_s1)", snf.toString());
	}
	
	@Test
	public void testWithUniversal() throws IOException, LexerException, ParserException, TypeException {
		SkolemNormalForm snf = TestUtils.parseToSkolem("forall x exists y P(x, y)");
		assertEquals("forall x P(x, _s1(x))", snf.toString());
	}
	
	@Test
	public void testWithMultipleUniversal() throws IOException, LexerException, ParserException, TypeException {
		SkolemNormalForm snf = TestUtils.parseToSkolem("forall x forall y exists z P(x, y, z)");
		assertEquals("forall x forall y P(x, y, _s1(x, y))", snf.toString());
	}
	
	@Test
	public void testWithInnerUniversal() throws IOException, LexerException, ParserException, TypeException {
		SkolemNormalForm snf = TestUtils.parseToSkolem("forall x exists y forall z P(x, y, z)");
		assertEquals("forall x forall z P(x, _s1(x), z)", snf.toString());
	}
	
}

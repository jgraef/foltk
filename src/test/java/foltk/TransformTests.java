package foltk;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;

import graef.foltk.formula.ast.proposition.Proposition;
import graef.foltk.formula.ast.transform.EliminateBiconditional;
import graef.foltk.formula.ast.transform.EliminateImplication;
import graef.foltk.formula.ast.transform.MoveNegationInwards;
import graef.foltk.formula.ast.transform.Transformation;
import graef.foltk.formula.ast.transform.Transformer;
import graef.foltk.formula.lexer.Lexer;
import graef.foltk.formula.lexer.LexerException;
import graef.foltk.formula.parser.Parser;
import graef.foltk.formula.parser.ParserException;
import graef.foltk.formula.parser.TypeException;

import org.junit.Test;

public class TransformTests {
	private Proposition rewrite(Transformation<Proposition> transformation, String code) throws IOException, LexerException, ParserException, TypeException {
		Transformer<Proposition> transformer = new Transformer<>(transformation);
		return transformer.rewrite(TestUtils.parse(code));
	}
	
	@Test
	public void testEliminateImplication() throws ParserException, IOException, LexerException, TypeException {
		String code = "(a & b) => c";
		Proposition tProp = rewrite(new EliminateImplication(), code);
		assertEquals("(!((a) & (b))) | (c)", tProp.toString());
	}
	
	@Test
	public void testEliminateBiconditional() throws ParserException, IOException, LexerException, TypeException {
		String code = "(a & b) <=> (c => b)";
		Proposition tProp = rewrite(new EliminateBiconditional(), code);
		assertEquals("(((a) & (b)) => ((c) => (b))) & (((c) => (b)) => ((a) & (b)))", tProp.toString());
	}
	
	@Test
	public void testMoveNegationInwards() throws IOException, LexerException, ParserException, TypeException {
		String code = "!(a | (a & b))";
		Proposition tProp = rewrite(new MoveNegationInwards(), code);
		assertEquals("(!(a)) & ((!(a)) | (!(b)))", tProp.toString());
	}
	
	@Test
	public void testNegationQuantifiers() throws IOException, LexerException, ParserException, TypeException {
		String code = "!((forall x P(x)) & (exists y Q(y)))";
		Proposition tProp = rewrite(new MoveNegationInwards(), code);
		assertEquals("(exists x (!(P(x)))) | (forall y (!(Q(y))))", tProp.toString());
	}
}

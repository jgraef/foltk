package foltk;

import static org.junit.Assert.assertEquals;
import graef.foltk.compiler.prenex.PrenexNormalForm;
import graef.foltk.compiler.prenex.PrenexTransformation;
import graef.foltk.formula.ast.proposition.Proposition;
import graef.foltk.formula.ast.transform.MoveNegationInwards;
import graef.foltk.formula.ast.transform.Transformation;
import graef.foltk.formula.ast.transform.Transformer;
import graef.foltk.formula.lexer.Lexer;
import graef.foltk.formula.lexer.LexerException;
import graef.foltk.formula.parser.Parser;
import graef.foltk.formula.parser.ParserException;
import graef.foltk.formula.parser.TypeException;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;

public class PrenexTests {
	@Test
	public void testSimple() throws IOException, LexerException, ParserException, TypeException {
		String code = "!((forall x P(x)) & (exists y Q(y)))";
		PrenexNormalForm pnf = TestUtils.parseToPrenex(code);
		assertEquals("(!(P(x))) | (!(Q(y)))", pnf.getMatrix().toString());
		assertEquals("exists x forall y (!(P(x))) | (!(Q(y)))", pnf.toString());
	}
	
	@Test
	public void testSimple2() throws IOException, LexerException, ParserException, TypeException {
		String code = "forall x exists y P(x, y)";
		PrenexNormalForm pnf = TestUtils.parseToPrenex(code);
		assertEquals("P(x, y)", pnf.getMatrix().toString());
		assertEquals("forall x exists y P(x, y)", pnf.toString());
	}
}

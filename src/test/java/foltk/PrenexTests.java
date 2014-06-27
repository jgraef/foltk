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
	private Proposition parse(String code) throws IOException, LexerException, ParserException, TypeException {
		Lexer lexer = new Lexer("test", new StringReader(code));
		Parser parser = new Parser(lexer);
		return parser.parseProposition();
	}
	
	private PrenexNormalForm transform(String code) throws IOException, LexerException, ParserException, TypeException {
		Proposition prop = parse(code);
		return PrenexTransformation.INSTANCE.transform(prop);
	}
	
	@Test
	public void testSimple() throws IOException, LexerException, ParserException, TypeException {
		String code = "!((forall x P(x)) & (exists y Q(y)))";
		PrenexNormalForm pnf = transform(code);
		assertEquals("exists x forall y (!(P(x))) | (!(Q(y)))", pnf.toString());
	}
}

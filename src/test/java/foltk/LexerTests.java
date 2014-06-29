package foltk;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import graef.foltk.formula.Location;
import graef.foltk.formula.ast.Declaration;
import graef.foltk.formula.lexer.Lexer;
import graef.foltk.formula.lexer.LexerException;
import graef.foltk.formula.lexer.Token;
import graef.foltk.formula.lexer.TokenType;

import org.junit.*;

public class LexerTests {
	@Test
	public void testAllTokensTypes() throws IOException, LexerException {
		String s = "; , Dog Lessie x Owner()true false forall exists!& |=><=> 1234";
		List<TokenType> expected = new ArrayList<>(17);
		expected.add(TokenType.SEMICOLON);
		expected.add(TokenType.COLON);
		expected.add(TokenType.SYMBOL);
		expected.add(TokenType.SYMBOL);
		expected.add(TokenType.SYMBOL);
		expected.add(TokenType.SYMBOL);
		expected.add(TokenType.LPAREN);
		expected.add(TokenType.RPAREN);
		expected.add(TokenType.TRUE);
		expected.add(TokenType.FALSE);
		expected.add(TokenType.FORALL);
		expected.add(TokenType.EXISTS);
		expected.add(TokenType.NOT);
		expected.add(TokenType.AND);
		expected.add(TokenType.OR);
		expected.add(TokenType.IMPL);
		expected.add(TokenType.BICOND);
		expected.add(TokenType.NUMBER);
		Iterator<TokenType> itExpected = expected.iterator();
		
		Lexer l = new Lexer("test", new StringReader(s));
		Iterator<Token> itGot = l.readAll().iterator();
		
		while (itGot.hasNext() || itExpected.hasNext()) {
			if (!itGot.hasNext() || !itExpected.hasNext()) {
				fail();
			}
			assertEquals(itExpected.next(), itGot.next().getType());
		}
	}
	
	@Test
	public void testInvalidToken() throws IOException {
		String s = "*";
		Lexer l = new Lexer("test", new StringReader(s));
		try {
			l.readAll();
			fail();
		} catch (LexerException e) {
		}
	}
	
	@Test
	public void testStringEOF() throws IOException, LexerException {
		String s = "Lessie";
		Lexer l = new Lexer("test", new StringReader(s));
		Token t = l.next();
		
		assertEquals(new Location("test", 1, 1), t.getLocation());
		assertEquals(TokenType.SYMBOL, t.getType());
		assertEquals("Lessie", t.toString());
	}
	
	@Test
	public void testBicondEOF() throws IOException {
		String s = "<=";
		Lexer l = new Lexer("test", new StringReader(s));
		try {
			l.readAll();
			fail();
		} catch (LexerException e) {
		}
	}
	
	@Test
	public void testNumber() throws IOException, LexerException {
		String s = " 12345 ,";
		
		Lexer l = new Lexer("test", new StringReader(s));
		Token t = l.next();
		
		assertEquals(TokenType.NUMBER, t.getType());
		assertEquals("12345", t.toString());
	}
	
	@Test
	public void testTokens() throws IOException, LexerException {
		String s = "   Dog\n <=>\r\n;";
		
		Lexer l = new Lexer("test", new StringReader(s));
		
		Token t1 = l.next();
		assertEquals(new Location("test", 1, 4), t1.getLocation());
		assertEquals(TokenType.SYMBOL, t1.getType());
		assertEquals("Dog", t1.toString());
		
		Token t2 = l.next();
		assertEquals(new Location("test", 2, 2), t2.getLocation());
		assertEquals(TokenType.BICOND, t2.getType());
		assertEquals("<=>", t2.toString());
		
		Token t3 = l.next();
		assertEquals(new Location("test", 3, 1), t3.getLocation());
		assertEquals(TokenType.SEMICOLON, t3.getType());
		assertEquals(";", t3.toString());
		
		Token tEof = l.next();
		assertEquals(new Location("test", 3, 2), tEof.getLocation());
		assertEquals(TokenType.EOF, tEof.getType());
		
		tEof = l.next();
		assertEquals(new Location("test", 3, 2), tEof.getLocation());
		assertEquals(TokenType.EOF, tEof.getType());
	}
	
	@Test
	public void testInternalize() throws IOException, LexerException {
		String s = "Dog Dog";
		
		Lexer l = new Lexer("test", new StringReader(s));
		Token t1 = l.next();
		Token t2 = l.next();
		assertSame(t1.toString(), t2.toString());
	}
	
	@Test
	public void testSingleLineComment() throws IOException, LexerException {
		String s = "& // foo \n !";
		Lexer l = new Lexer("test", new StringReader(s));
		assertEquals(TokenType.AND, l.next().getType());
		assertEquals(TokenType.NOT, l.next().getType());
		assertEquals(TokenType.EOF, l.next().getType());
	}
	
	@Test
	public void testMultiLineComment() throws IOException, LexerException {
		String s = "& /* foo \n ! \n Lassie */ |";
		Lexer l = new Lexer("test", new StringReader(s));
		assertEquals(TokenType.AND, l.next().getType());
		assertEquals(TokenType.OR, l.next().getType());
		assertEquals(TokenType.EOF, l.next().getType());
	}
}

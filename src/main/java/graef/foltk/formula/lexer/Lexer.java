package graef.foltk.formula.lexer;

import graef.foltk.formula.Location;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Lexer {
	private final String name;
	private int line;
	private int col;
	private final Reader in;
	private int c;
	private Location loc;
	private final Internalizer<String> symbolInternalizer;
	private static final Map<String, TokenType> keywords = new HashMap<>();
	static {
		keywords.put("true", TokenType.TRUE);
		keywords.put("false", TokenType.FALSE);
		keywords.put("forall", TokenType.FORALL);
		keywords.put("exists", TokenType.EXISTS);
		/*keywords.put("pred", TokenType.PREDICATE_DECL);
		keywords.put("var", TokenType.VARIABLE_DECL);
		keywords.put("func", TokenType.FUNCTION_DECL);
		keywords.put("const", TokenType.CONSTANT_DECL);*/
	}

	public Lexer(String name, InputStream in)
			throws IOException {
		this(name, new InputStreamReader(in));
	}

	public Lexer(String name, Reader in)
			throws IOException {
		this.name = name;
		this.in = in;
		this.line = 1;
		this.col = 1;
		this.symbolInternalizer = new Internalizer<>();
		
		nextChar();
	}

	private int nextChar() throws IOException {
		int c = in.read();
		loc = new Location(name, line, col);
		if (c != -1) {
			if (c == '\r' || (c == '\n' && this.c != '\r')) {
				line++;
				col = 1;
			}
			else if (c != '\n') {
				col++;
			}
		}

		this.c = c;
		return c;
	}

	private void skipWhitespace() throws IOException {
		while (Character.isWhitespace(c)) {
			nextChar();
		}
	}

	private static boolean isSymbolStart(int c) {
		return Character.isAlphabetic(c);
	}

	private static boolean isSymbolPart(int c) {
		return Character.isAlphabetic(c) || Character.isDigit(c) || c == '_';
	}
	
	public static boolean isValidSymbol(String symbol) {
		if (symbol.length() == 0 || !isSymbolStart(symbol.codePointAt(0))) {
			return false;
		}
		for (int i = 1; i < symbol.length(); i++) {
			if (!isSymbolPart(symbol.codePointAt(i))) {
				return false;
			}
		}
		return true;
	}

	private String lexSymbol() throws IOException {
		assert isSymbolStart(c);
		StringBuilder sb = new StringBuilder();
		sb.appendCodePoint(c);

		while (isSymbolPart(nextChar())) {
			sb.appendCodePoint(c);
		}

		return sb.toString();
	}
	
	private String lexNumber() throws IOException {
		assert Character.isDigit(c);
		StringBuilder sb = new StringBuilder();
		sb.appendCodePoint(c);
		
		while (Character.isDigit(nextChar())) {
			sb.appendCodePoint(c);
		}
		
		return sb.toString();
	}

	public Token next() throws IOException, LexerException {
		skipWhitespace();

		int fc = c;
		Location loc = getLocation();

		/* lex a symbol or keyword */
		if (isSymbolStart(c)) {
			String name = lexSymbol();
			TokenType tKeyword = keywords.get(name);
			if (tKeyword != null) {
				return new Token(loc, tKeyword, name);
			}
			else {
				return new Token(loc, TokenType.SYMBOL, symbolInternalizer.internalize(name));
			}
		}
		else if (Character.isDigit(c)) {
			String num = lexNumber();
			return new Token(loc, TokenType.NUMBER, num);
		}

		switch (c) {
		case -1:
			nextChar();
			return new Token(loc, TokenType.EOF, "<EOF>");
		case ';':
			nextChar();
			return new Token(loc, TokenType.SEMICOLON, fc);
		case ',':
			nextChar();
			return new Token(loc, TokenType.COLON, fc);
		case '(':
			nextChar();
			return new Token(loc, TokenType.LPAREN, fc);
		case ')':
			nextChar();
			return new Token(loc, TokenType.RPAREN, fc);
		case '!':
			nextChar();
			return new Token(loc, TokenType.NOT, fc);
		case '&':
			nextChar();
			return new Token(loc, TokenType.AND, fc);
		case '|':
			nextChar();
			return new Token(loc, TokenType.OR, fc);
		case '=':
			nextChar();
			expect('>');
			return new Token(loc, TokenType.IMPL, "=>");
		case '<':
			nextChar();
			expect('=');
			expect('>');
			return new Token(loc, TokenType.BICOND, "<=>");
		default:
			throw new LexerException(c, loc);
		}
	}

	private void expect(int expected) throws LexerException, IOException {
		if (c != expected) {
			throw new LexerException(expected, c, getLocation());
		}
		nextChar();
	}

	public Location getLocation() {
		return loc;
	}
	
	public List<Token> readAll() throws IOException, LexerException {
		List<Token> tokens = new LinkedList<Token>();
		while (true) {
			Token token = next();
			if (token.getType() == TokenType.EOF) {
				return tokens;
			}
			tokens.add(token);
		}
	}
}

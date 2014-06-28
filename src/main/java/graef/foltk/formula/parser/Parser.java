package graef.foltk.formula.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
import graef.foltk.formula.ast.proposition.QuantifiedProposition;
import graef.foltk.formula.ast.proposition.UniversalQuantifiedProposition;
import graef.foltk.formula.ast.term.FunctionTerm;
import graef.foltk.formula.ast.term.Term;
import graef.foltk.formula.ast.term.VariableTerm;
import graef.foltk.formula.lexer.Lexer;
import graef.foltk.formula.lexer.LexerException;
import graef.foltk.formula.lexer.Token;
import graef.foltk.formula.lexer.TokenType;

public class Parser {
	private final Lexer lexer;
	private Token t;
	private final Scope rootScope;
	private Scope scope;

	public Parser(Lexer lexer) throws IOException, LexerException {
		this.lexer = lexer;
		rootScope = new Scope();
		scope = rootScope;

		nextToken();
	}

	private Token nextToken() throws IOException, LexerException {
		t = lexer.next();
		//System.out.println("read: " + t);
		return t;
	}

	private Token expect(TokenType expected) throws IOException, LexerException,
			ParserException {
		Token tGot = t;
		if (t.getType() != expected) {
			throw new ParserException(expected, t);
			// System.err.println(e.getMessage());
		}
		nextToken();
		return tGot;
	}
	
	public TranslationUnit parseTranslationUnit() throws IOException, LexerException, ParserException, TypeException {
		List<Proposition> propositions = new ArrayList<>();
		List<Import> imports = new ArrayList<>();
		
		while (t.getType() != TokenType.EOF) {
			switch (t.getType()) {
			case IMPORT:
				Token tImport = t;
				String name = expect(TokenType.STRING).toString();
				imports.add(new Import(tImport, name));
				break;
			default:
				propositions.add(parseProposition());
				break;
			}
				
			expect(TokenType.SEMICOLON);
		}
		
		return new TranslationUnit(propositions, imports, rootScope);
	}

	public Proposition parseProposition() throws ParserException, IOException,
			LexerException, TypeException {
		return parseBinaryProposition(parseUnaryProposition(), 0);
	}

	private Proposition parseUnaryProposition() throws ParserException,
			IOException, LexerException, TypeException {
		Proposition p;

		switch (t.getType()) {
		case LPAREN:
			nextToken();
			p = parseProposition();
			expect(TokenType.RPAREN);
			return p;
		case TRUE:
			nextToken();
			p = new ConstantProposition(t, true);
			return p;
		case FALSE:
			nextToken();
			p = new ConstantProposition(t, false);
			return p;
		case NOT:
			nextToken();
			p = new NotProposition(t, parseUnaryProposition());
			return p;
		case SYMBOL:
			return parsePredicate();
		case FORALL:
		case EXISTS:
			return parseQuantifiedProposition();
		default:
			throw new ParserException(t);
		}
	}

	private Proposition parseQuantifiedProposition() throws ParserException,
			IOException, LexerException, TypeException {
		Token tQuantifier = t;

		Token tVar = nextToken();
		nextToken();
		if (tVar.getType() != TokenType.SYMBOL) {
			throw new ParserException(TokenType.SYMBOL, tVar);
		}
		
		scope = scope.newScope();
		Declaration decl = new Declaration(tVar.toString(), SymbolType.VARIABLE, 0);
		decl = scope.checkAndPut(decl);
		Symbol var = new Symbol(tVar, decl.getSymbol());
		Proposition proposition = parseProposition();

		QuantifiedProposition qp;
		switch (tQuantifier.getType()) {
		case FORALL:
			qp = new UniversalQuantifiedProposition(tQuantifier, var,
					proposition, scope);
			break;
		case EXISTS:
			qp = new ExistentialQuantifiedProposition(tQuantifier, var,
					proposition, scope);
			break;
		default:
			throw new ParserException(tQuantifier);
		}
		
		scope = scope.getParent();
		return qp;
	}

	private Proposition parsePredicate() throws IOException, LexerException,
			ParserException, TypeException {
		Token tSymbol = t;
		nextToken();
		List<Term> terms = parseTermList();

		Declaration decl = new Declaration(tSymbol.toString(), SymbolType.PREDICATE, terms.size());
		decl = scope.checkAndPut(decl);
		
		Symbol symbol = new Symbol(tSymbol, decl.getSymbol());
		return new PredicateProposition(symbol.getToken(), symbol, terms);
	}

	private boolean isBinaryOperator(Token t) {
		switch (t.getType()) {
		case AND:
		case BICOND:
		case IMPL:
		case OR:
			return true;
		default:
			return false;
		}
	}

	private Proposition parseBinaryProposition(Proposition lhs, int minPrec)
			throws ParserException, IOException, LexerException, TypeException {

		while (isBinaryOperator(t) && t.getType().getPrecedence() >= minPrec) {
			Token op = t;
			nextToken();
			Proposition rhs = parseUnaryProposition();
			
			while (isBinaryOperator(t)
					&& (t.getType().getPrecedence() > op.getType()
							.getPrecedence() || (t.getType()
							.isRightAssociative() && t.getType()
							.getPrecedence() == op.getType().getPrecedence()))) {
				rhs = parseBinaryProposition(rhs, t.getType().getPrecedence());
			}
			
			switch (op.getType()) {
			case AND:
				lhs = new AndProposition(op, lhs, rhs);
				break;
			case OR:
				lhs = new OrProposition(op, lhs, rhs);
				break;
			case IMPL:
				lhs = new ImplicationProposition(op, lhs, rhs);
				break;
			case BICOND:
				lhs = new BiconditionalProposition(op, lhs, rhs);
				break;
			default:
				throw new ParserException(t);
			}
		}
		return lhs;
	}

	public Term parseTerm() throws ParserException, IOException,
			LexerException, TypeException {
		Token tSymbol = t;
		expect(TokenType.SYMBOL);
		
		Declaration decl = scope.lookup(tSymbol.toString());
		if (decl == null || decl.getType() == SymbolType.FUNCTION) {
			List<Term> terms = parseTermList();
			String name = decl != null ? decl.getSymbol() : tSymbol.toString();
			Declaration decl2 = new Declaration(name, SymbolType.FUNCTION, terms.size());
			decl = scope.checkAndPut(decl2);
			return new FunctionTerm(tSymbol, new Symbol(tSymbol, name), terms);
		}
		else if (decl.getType() == SymbolType.VARIABLE) {
			return new VariableTerm(tSymbol, new Symbol(tSymbol, decl.getSymbol()));
		}
		else {
			throw new TypeException(decl);
		}
	}

	private List<Term> parseTermList() throws IOException, LexerException,
			ParserException, TypeException {
		List<Term> terms = new LinkedList<>();

		if (t.getType() == TokenType.LPAREN) {
			nextToken();
			while (true) {
				terms.add(parseTerm());
				if (t.getType() != TokenType.COLON) {
					break;
				}
				nextToken();
			}
			expect(TokenType.RPAREN);
		}
		return terms;
	}
	
	public Scope getScope() {
		return scope;
	}
	
	public Scope getRootScope() {
		return rootScope;
	}
}

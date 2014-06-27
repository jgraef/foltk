package graef.foltk.compiler.prenex;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import graef.foltk.formula.ast.AstNode;
import graef.foltk.formula.ast.AstVisitor;
import graef.foltk.formula.ast.Symbol;
import graef.foltk.formula.ast.proposition.AndProposition;
import graef.foltk.formula.ast.proposition.BiconditionalProposition;
import graef.foltk.formula.ast.proposition.BinaryProposition;
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
import graef.foltk.formula.ast.term.VariableTerm;
import graef.foltk.formula.lexer.Lexer;
import graef.foltk.formula.lexer.LexerException;
import graef.foltk.formula.parser.Parser;
import graef.foltk.formula.parser.ParserException;
import graef.foltk.formula.parser.TypeException;

public class PrenexNormalForm {

	
	public static Proposition compute(Proposition p) {
		return p;
	}
	
	
	public static void main(String[] args) throws IOException, LexerException, ParserException, TypeException {
		String code = "!((forall x A => B => P(x)) | (C & (!D <=> exists x P(x))));\n";
		Lexer lexer = new Lexer("prenex-test", new StringReader(code));
		Parser parser = new Parser(lexer);
		Proposition p = parser.parseProposition();
		
		System.out.println("before: " + p);
		p = compute(p);
		System.out.println("after: " + p);
	}
}

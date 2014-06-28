package graef.foltk.formula.parser;

import graef.foltk.formula.ast.AstNode;
import graef.foltk.formula.ast.AstVisitor;
import graef.foltk.formula.lexer.Token;

public class Import extends AstNode {
	private String unitName;
	
	public Import(Token token, String unitName) {
		super(token);
	}

	public String toString() {
		return "import \"" + unitName + "\"";
	}

	@Override
	public void accept(AstVisitor visitor) {
		visitor.visit(this);
	}
}

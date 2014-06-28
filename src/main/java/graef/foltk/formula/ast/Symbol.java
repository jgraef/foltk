package graef.foltk.formula.ast;

import graef.foltk.formula.lexer.Token;

public class Symbol extends AstNode {
	private final String ident;
	
	public Symbol(Token token, String ident) {
		super(token);
		this.ident = ident;
	}
	
	public String getIdentifier() {
		return ident;
	}
	
	public String toString() {
		return ident;
	}

	@Override
	public void accept(AstVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ident == null) ? 0 : ident.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Symbol other = (Symbol) obj;
		if (ident == null) {
			if (other.ident != null)
				return false;
		} else if (!ident.equals(other.ident))
			return false;
		return true;
	}
}

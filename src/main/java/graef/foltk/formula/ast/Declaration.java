package graef.foltk.formula.ast;

import graef.foltk.formula.lexer.Lexer;
import graef.foltk.formula.lexer.Token;

public class Declaration {
	private final Symbol firstOccurence;
	private final String symbol;
	private final SymbolType type;
	private final int arity;
	
	public Declaration(Symbol firstOccurence, String symbol, SymbolType type, int arity) {
		if (arity < 0) {
			throw new IllegalArgumentException("arity < 0");
		}
		else if (!Lexer.isValidSymbol(symbol)) {
			throw new IllegalArgumentException("Invalid symbol name: " + symbol);
		}
		else if (type == SymbolType.VARIABLE && arity > 0) {
			throw new IllegalArgumentException("variable with arity > 0");
		}
		
		this.firstOccurence = firstOccurence;
		this.symbol = symbol;
		this.type = type;
		this.arity = arity;
	}
	
	public Declaration(String symbol, SymbolType type, int arity) {
		this(null, symbol, type, arity);
	}
	
	public Symbol getFirstOccurence() {
		return this.firstOccurence;
	}
	
	public String getSymbol() {
		return symbol;
	}

	public SymbolType getType() {
		return type;
	}

	public int getArity() {
		return arity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + arity;
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Declaration other = (Declaration) obj;
		if (arity != other.arity)
			return false;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Declaration [symbol=" + symbol + ", type=" + type + ", arity="
				+ arity + "]";
	}
	
	
}

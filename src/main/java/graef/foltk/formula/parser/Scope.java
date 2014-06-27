package graef.foltk.formula.parser;

import graef.foltk.formula.ast.Declaration;
import graef.foltk.formula.ast.SymbolType;

import java.util.HashMap;
import java.util.Map;

public class Scope {
	private final Map<String, Declaration> declarations;
	private final Scope parent;
	private final Scope root;
	
	public Scope(Scope parent) {
		this.declarations = new HashMap<>();
		this.parent = parent;
		this.root = parent == null ? this : parent.getRoot();
	}
	
	public Scope() {
		this(null);
	}
	
	public Scope getRoot() {
		return root;
	}
	
	public Scope getParent() {
		return parent;
	}
	
	public Declaration lookup(String symbol) {
		Declaration decl = declarations.get(symbol);
		if (decl == null) {
			return decl;
		}
		else if (parent != null) {
			return parent.lookup(symbol);
		}
		else {
			return null;
		}
	}
	
	public void put(Declaration decl) {
		if (decl.getType() == SymbolType.VARIABLE || root == this) {
			declarations.put(decl.getSymbol(), decl);
		}
		else {
			root.put(decl);
		}
	}
	
	public Declaration checkAndPut(Declaration decl) throws TypeException {
		Declaration decl2 = lookup(decl.getSymbol());
		if (decl2 == null) {
			put(decl);
			return decl;
		}
		else if (!decl.equals(decl2)) {
			throw new TypeException(decl2, decl);
		}
		else {
			return decl2;
		}
	}

	public Scope newScope() {
		return new Scope(this);
	}
	
	
}

package graef.foltk.formula.parser;

import graef.foltk.formula.ast.proposition.Proposition;

import java.util.Collections;
import java.util.List;

public class TranslationUnit {
	private List<Proposition> propositions;
	private List<Import> imports;
	private Scope rootScope;
	
	public TranslationUnit(List<Proposition> propositions,
			List<Import> imports, Scope rootScope) {
		super();
		this.propositions = Collections.unmodifiableList(propositions);
		this.imports = Collections.unmodifiableList(imports);
		this.rootScope = rootScope;
	}

	public List<Proposition> getPropositions() {
		return propositions;
	}

	public List<Import> getImports() {
		return imports;
	}

	public Scope getRootScope() {
		return rootScope;
	}
}

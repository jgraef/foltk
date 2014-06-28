package graef.foltk.compiler.prenex;

public interface QuantorVisitor {
	public void visit(ExistentialQuantifier exists);
	public void visit(UniversalQuantifier univ);
}

package propascript.ast;

public class Variable extends Expr {
	private final String identifier;

	public Variable(String identifier) {
		this.identifier = identifier;
	}

	public String getIdentifier() {
		return identifier;
	}


	@Override
	public String toString() {
		return identifier;
	}

	@Override
	public void apply(Visitor visitor) {
		visitor.visit(this);
	}
}

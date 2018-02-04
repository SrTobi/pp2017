package propascript.ast;

public class Constant extends Expr {
	private final int value;

	public Constant(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "" + value;
	}

	@Override
	public void apply(Visitor visitor) {
		visitor.visit(this);
	}
}

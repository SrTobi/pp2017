package propascript.ast;

public class AssignExpr extends BinaryExpr {
	private final Variable left;
	private final Expr right;

	public AssignExpr(Variable left, Expr right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public Variable getLeft() {
		return left;
	}

	@Override
	public Expr getRight() {
		return right;
	}

	@Override
	public void apply(Visitor visitor) {
		visitor.visit(this);
	}
}

package propascript.ast;

public class SubExpr extends BinaryExpr {
	private final Expr left;
	private final Expr right;

	public SubExpr(Expr left, Expr right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public Expr getLeft() {
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

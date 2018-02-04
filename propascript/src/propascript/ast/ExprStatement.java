package propascript.ast;

public class ExprStatement extends Statement {
	private final Expr expr;

	public ExprStatement(Expr expr) {
		this.expr = expr;
	}

	public Expr getExpr() {
		return expr;
	}

	@Override
	public String toString() {
		return expr.toString() + ";";
	}

	@Override
	public void apply(Visitor visitor) {
		visitor.visit(this);
	}
}

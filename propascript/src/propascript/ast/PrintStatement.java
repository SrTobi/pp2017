package propascript.ast;

public class PrintStatement extends Statement {
	private final Expr expr;

	public PrintStatement(Expr expr) {
		this.expr = expr;
	}

	public Expr getExpr() {
		return expr;
	}

	@Override
	public String toString() {
		return "print " + expr + ";";
	}

	@Override
	public void apply(Visitor visitor) {
		visitor.visit(this);
	}
}

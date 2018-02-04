package propascript.ast;

public abstract class BinaryExpr extends Expr {
	public abstract Expr getLeft();
	public abstract Expr getRight();
}

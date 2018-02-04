package propascript.visitors;

import propascript.ast.*;

/**
 * @deprecated
 *  This class is not used any more.
 *  It was used to check whether there exists an assignment to an expression which is not a variable.
 *  This is now done directly in the Parser, which has two advantages:
 *    - The AssignmentExpr can now have Variable as Type for left
 *    - On an error, we are able to output the line number
 *
 */
public class LValueAssignChecker implements Visitor {
	public static boolean isLValue(Expr expr) {
		return expr instanceof Variable;
	}

	@Override
	public void visit(Program program) {
		program.getStatements().forEach((stmt) -> stmt.apply(this));
	}

	@Override
	public void visit(ExprStatement stmt) {
		stmt.getExpr().apply(this);
	}

	@Override
	public void visit(WhileStatement stmt) {
		stmt.getCondition().apply(this);
		stmt.getStatements().forEach((s) -> s.apply(this));
	}

	@Override
	public void visit(PrintStatement stmt) {
		stmt.getExpr().apply(this);
	}

	@Override
	public void visit(AssignExpr expr) {
		if (!isLValue(expr.getLeft())) {
			throw new Error("Left side of assignment is no l-value");
		}
		expr.getRight().apply(this);
	}

	@Override
	public void visit(SubExpr expr) {
		expr.getLeft().apply(this);
		expr.getRight().apply(this);
	}

	@Override
	public void visit(MulExpr expr) {
		expr.getLeft().apply(this);
		expr.getRight().apply(this);
	}

	@Override
	public void visit(Constant expr) {
		// nothing to do
	}

	@Override
	public void visit(Variable expr) {
		// nothing to do
	}
}

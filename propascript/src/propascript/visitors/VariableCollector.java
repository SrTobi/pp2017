package propascript.visitors;

import propascript.ast.*;

import java.util.HashMap;
import java.util.Map;

public class VariableCollector implements Visitor {
	private Map<String, Integer> vars = new HashMap<>();
	private int nextIdx = 0;

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
		expr.getLeft().apply(this);
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
		String id = expr.getIdentifier();
		if (!vars.containsKey(id)) {
			vars.put(id, nextIdx++);
		}
	}

	public Map<String, Integer> getVars() {
		return vars;
	}
}

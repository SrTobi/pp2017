package propascript.visitors;

import propascript.ast.*;

import java.util.HashMap;
import java.util.Map;

public class VariableCollector implements Visitor {
	private Map<String, Integer> vars = new HashMap<>();
	private int nextIdx = 0;

	@Override
	public void visit(Program program) {
	}

	@Override
	public void visit(ExprStatement stmt) {
	}

	@Override
	public void visit(WhileStatement stmt) {
	}

	@Override
	public void visit(PrintStatement stmt) {
	}

	@Override
	public void visit(AssignExpr expr) {
	}

	@Override
	public void visit(SubExpr expr) {
	}

	@Override
	public void visit(MulExpr expr) {
	}

	@Override
	public void visit(Constant expr) {
		// nothing to do
	}

	@Override
	public void visit(Variable expr) {
	}

	public Map<String, Integer> getVars() {
		return vars;
	}
}

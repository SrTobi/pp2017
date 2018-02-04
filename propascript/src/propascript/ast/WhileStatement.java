package propascript.ast;

import java.util.List;

public class WhileStatement extends Statement {
	private final Expr condition;
	private final List<Statement> statements;

	public WhileStatement(Expr condition, List<Statement> statements) {
		this.condition = condition;
		this.statements = statements;
	}

	public Expr getCondition() {
		return condition;
	}

	public List<Statement> getStatements() {
		return statements;
	}

	@Override
	public String toString() {
		return "while (" + condition + ") { ... }";
	}

	@Override
	public void apply(Visitor visitor) {
		visitor.visit(this);
	}
}

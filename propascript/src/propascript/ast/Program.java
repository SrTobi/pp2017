package propascript.ast;

import java.util.List;

public class Program extends Node {

	private List<Statement> statements;

	public Program(List<Statement> statements) {
		this.statements = statements;
	}

	public List<Statement> getStatements() {
		return statements;
	}

	@Override
	public String toString() {
		switch (statements.size()) {
			case 0:
				return ";";
			case 1:
				return statements.get(0) + ";";
			default:
				return statements.get(0) + "; ...";
		}
	}

	@Override
	public void apply(Visitor visitor) {
		visitor.visit(this);
	}
}

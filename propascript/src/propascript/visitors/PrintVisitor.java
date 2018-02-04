package propascript.visitors;

import propascript.ast.*;

import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class PrintVisitor implements Visitor {

	private StringBuilder builder = new StringBuilder();
	private int indent = 0;

	private static int ASSIGN_PRECEDENCE = 0;
	private static int SUB_PRECEDENCE = 1;
	private static int MUL_PRECEDENCE = 2;

	private int curPrecedence = ASSIGN_PRECEDENCE;

	private void write(String txt) {
		builder.append(txt);
	}

	private void beginLine() {
		for (int i = 0; i < indent; ++i) {
			write("  ");
		}
	}
	private void endLine() {
		write("\n");
	}

	private void writeStatements(List<Statement> stmts) {
		for (Statement stmt : stmts) {
			beginLine();
			stmt.apply(this);
			endLine();
		}
	}

	@Override
	public String toString() {
		return builder.toString();
	}

	@Override
	public void visit(Program program) {
		writeStatements(program.getStatements());
	}

	@Override
	public void visit(ExprStatement stmt) {
		stmt.getExpr().apply(this);
	}

	@Override
	public void visit(WhileStatement stmt) {
		write("while (");
		stmt.getCondition().apply(this);
		write(") {");
		endLine();
		++indent;
		writeStatements(stmt.getStatements());
		--indent;
		beginLine();
		write("}");
	}

	@Override
	public void visit(PrintStatement stmt) {
		write("print ");
		stmt.getExpr().apply(this);
	}

	private void writeBinaryExpr(BinaryExpr expr, String sym, int opPrecedence) {
		int precedence = curPrecedence;
		curPrecedence = opPrecedence;
		if (precedence > opPrecedence) {
			write("(");
		}

		expr.getLeft().apply(this);
		write(" ");
		write(sym);
		write(" ");
		expr.getRight().apply(this);

		if (precedence > opPrecedence) {
			write(")");
		}
		curPrecedence = precedence;
	}

	@Override
	public void visit(AssignExpr expr) {
		writeBinaryExpr(expr, "=", ASSIGN_PRECEDENCE);
	}

	@Override
	public void visit(SubExpr expr) {
		writeBinaryExpr(expr, "-", SUB_PRECEDENCE);
	}

	@Override
	public void visit(MulExpr expr) {
		writeBinaryExpr(expr, "*", MUL_PRECEDENCE);
	}

	@Override
	public void visit(Constant expr) {
		write("" + expr.getValue());
	}

	@Override
	public void visit(Variable expr) {
		write(expr.getIdentifier());
	}
}

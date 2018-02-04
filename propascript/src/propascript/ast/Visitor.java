package propascript.ast;

public interface Visitor {
	void visit(Program program);

	void visit(ExprStatement stmt);
	void visit(WhileStatement stmt);
	void visit(PrintStatement stmt);

	void visit(AssignExpr expr);
	void visit(SubExpr expr);
	void visit(MulExpr expr);
	void visit(Constant expr);
	void visit(Variable expr);
}

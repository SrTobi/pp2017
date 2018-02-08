package propascript.visitors;

import propascript.ast.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.Map;

public class CodeGenVisitor implements Visitor {
	private final String className;
	private final Map<String, Integer> vars;
	private final boolean verbose;
	private StringBuilder builder = new StringBuilder();
	private int maxStack = 1;
	private int curStack = 0;
	private int nextLabelId = 0;

	public CodeGenVisitor(String className, Map<String, Integer> vars, boolean verbose) {
		this.className = className;
		this.vars = vars;
		this.verbose = verbose;
	}

	public void allocStack(int count) {
		curStack += count;
		maxStack = Math.max(curStack, maxStack);
	}

	public void deallocStack(int count) {
		curStack -= count;
	}

	private void ln() {
		builder.append("\n");
	}

	private void meta(String line) {
		builder.append(line);
		ln();
	}

	private void label(String name) {
		builder.append("  ").append(name).append(":");
		ln();
	}

	private void inst(String line) {
		builder.append("    ").append(line);
		ln();
	}

	private void inst(String line, String comment) {
		builder.append(String.format("    %-10s ; %s", line, comment));
		ln();
	}

	private void comment(String txt) {
		builder.append("    ; ").append(txt);
		ln();
	}

	private void commentVerbose(String txt) {
		if (verbose) {
			comment(txt);
		}
	}

	@Override
	public void visit(Program program) {
		// write meta
		meta(".class public " + className);
		meta(".super java/lang/Object");
		ln();

		// write constructor
		meta(".method public <init>()V");
		inst("aload_0");
		inst("invokenonvirtual java/lang/Object/<init>()V");
		inst("return");
		meta(".end method");
		ln();

		// main method
		meta(".method public static main([Ljava/lang/String;)V");
		// we don't know the stack limit yet... we will replace this later
		// with the actual stack size
		inst(".limit stack $$[stack-limit]$$");
		inst(".limit locals " + Math.max(1, vars.size()));
		ln();

		// initialize local variables
		comment("Initialize local variables");
		for (int i = 0; i < vars.size(); ++i) {
			inst("iconst_0");
			inst("istore " + i);
		}

		ln();
		visitStatements(program.getStatements());
		inst("return");
		meta(".end method");
	}

	private void visitStatements(List<Statement> statements) {
		for (Statement stmt : statements) {
			stmt.apply(this);
		}
	}

	@Override
	public void visit(ExprStatement stmt) {
		comment(stmt.toString());
		allocStack(1);
		stmt.getExpr().apply(this);
		inst("pop");
		deallocStack(1);
		ln();
	}

	@Override
	public void visit(WhileStatement stmt) {
		comment("while " + stmt.getCondition());
		throw new NotImplementedException();
	}

	@Override
	public void visit(PrintStatement stmt) {
		comment(stmt.toString());
		allocStack(2);
		inst("getstatic java/lang/System/out Ljava/io/PrintStream;");
		stmt.getExpr().apply(this);
		inst("invokevirtual java/io/PrintStream/println(I)V");
		deallocStack(2);
		ln();
	}

	@Override
	public void visit(AssignExpr expr) {
		expr.getRight().apply(this);
		allocStack(1);
		inst("dup");
		inst("istore " + vars.get(expr.getLeft().getIdentifier()));
		deallocStack(1);
	}

	@Override
	public void visit(SubExpr expr) {
		expr.getLeft().apply(this);
		allocStack(1);
		expr.getRight().apply(this);
		inst("isub", expr.toString());
		deallocStack(1);
	}

	@Override
	public void visit(MulExpr expr) {
		expr.getLeft().apply(this);
		allocStack(1);
		expr.getRight().apply(this);
		inst("imul", expr.toString());
		deallocStack(1);
	}

	@Override
	public void visit(Constant expr) {
		inst("ldc " + expr.getValue());
	}

	@Override
	public void visit(Variable expr) {
		inst("iload " + vars.get(expr.getIdentifier()), expr.toString());
	}

	@Override
	public String toString() {
		return builder.toString().replace("$$[stack-limit]$$", Integer.toString(maxStack));
	}
}

package propascript.ast;

import propascript.visitors.PrintVisitor;

public abstract class Node {
	public abstract void apply(Visitor visitor);

	@Override
	public String toString() {
		PrintVisitor pv = new PrintVisitor();
		apply(pv);
		return pv.toString();
	}
}

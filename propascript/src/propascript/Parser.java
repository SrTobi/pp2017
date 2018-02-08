package propascript;


import jas.Var;
import propascript.ast.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static propascript.TokenType.*;

public class Parser {
	private Lexer lexer;

	public Parser(Lexer lexer) {
		this.lexer = lexer;
	}

	public Program parse() throws ParseException {
		List<Statement> statements = parseStatements();
		return new Program(statements);
	}

	private void error(String message) throws ParseException {
		throw new ParseException(message, lexer.getLine());
	}

	private void expect(TokenType type) throws ParseException {
		if (lexer.getType() != type) {
			throw new ParseException("Expected " + type + " ; found " + lexer.getType(), lexer.getLine());
		}
	}

	private void consume(TokenType type) throws ParseException {
		expect(type);
		lexer.nextToken();
	}

	private List<Statement> parseStatements() throws ParseException {
		List<Statement> statements = new ArrayList<>();

		while (lexer.getType() != EOF && lexer.getType() != CURLY_RIGHT) {
			Statement stmt = parseStatement();
			if (stmt != null) {
				statements.add(stmt);
			}
			consume(EOS);
		}

		return statements;
	}

	private Statement parseStatement() throws ParseException {
		switch (lexer.getType()) {
			case EOS:
				return null;
			case PRINT:
				lexer.nextToken();
				return new PrintStatement(parseExpr());
			case WHILE:
				lexer.nextToken();
				Expr condition = parseExpr();
				consume(CURLY_LEFT);
				List<Statement> stmts = parseStatements();
				consume(CURLY_RIGHT);
				return new WhileStatement(condition, stmts);
			default:
				return new ExprStatement(parseExpr());
		}
	}

	private Expr parseExpr() throws ParseException {
		return parseAssignExpr();
	}

	private Expr parseAssignExpr() throws ParseException {
		Expr left = parseMinusExpr();
		if (lexer.getType() == ASSIGN) {
			lexer.nextToken();
			if (!(left instanceof Variable)) {
				error("Can't assign to non assignable expression");
			}
			Expr right = parseAssignExpr();
			return new AssignExpr((Variable)left, right);
		}
		return left;
	}

	private Expr parseMinusExpr() throws ParseException {
		Expr left = parseMulExpr();
		if (lexer.getType() == MINUS) {
			lexer.nextToken();
			Expr right = parseMinusExpr();
			return new SubExpr(left, right);
		}
		return left;
	}

	private Expr parseMulExpr() throws ParseException {
		Expr left = parseValue();
		if (lexer.getType() == MUL) {
			lexer.nextToken();
			Expr right = parseMulExpr();
			return new MulExpr(left, right);
		}
		return left;
	}

	private Expr parseValue() throws ParseException {
		switch (lexer.getType()) {
			case BRACKET_LEFT:
				lexer.nextToken();
				Expr expr = parseExpr();
				consume(BRACKET_RIGHT);
				return expr;
			case IDENTIFIER:
				String id = lexer.getValue();
				lexer.nextToken();
				return new Variable(id);
			case NUMBER:
				int num = lexer.getIntValue();
				lexer.nextToken();
				return new Constant(num);
			default:
				error("expected (, identifier, number but found " + lexer.getType());
				return null;
		}
	}
}

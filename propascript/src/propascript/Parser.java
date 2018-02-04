package propascript;


import propascript.ast.*;

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
		while (lexer.getType() != TokenType.EOF &&
				lexer.getType() != TokenType.CURLY_RIGHT) {
			Statement stmt = parseStatement();
			if (stmt != null)
				statements.add(stmt);
			if (lexer.getType() != TokenType.CURLY_RIGHT) {
				consume(TokenType.EOS);
			}
		}
		return statements;
	}

	private Statement parseStatement() throws ParseException {
		switch (lexer.getType()) {
			case WHILE:
				return parseIf();
			case EOS:
			case EOF:
				return null;
			case PRINT:
				return parsePrint();
			case IDENTIFIER:
			case BRACKET_LEFT:
			case NUMBER:
				return new ExprStatement(parseExpr());
			default:
				error("Expected statement but found " + lexer.getType());
		}
		return null;
	}

	private Statement parsePrint() throws ParseException {
		consume(PRINT);
		return new PrintStatement(parseExpr());
	}


	private WhileStatement parseIf() throws ParseException {
		consume(WHILE);
		consume(BRACKET_LEFT);
		Expr condition = parseExpr();
		consume(BRACKET_RIGHT);
		consume(CURLY_LEFT);
		List<Statement> statements = parseStatements();
		consume(CURLY_RIGHT);
		return new WhileStatement(condition, statements);
	}

	private Expr parseExpr() throws ParseException {
		return parseAssignExpr();
	}

	private Expr parseAssignExpr() throws ParseException {
		Expr left = parseMinusExpr();
		if (lexer.getType() == ASSIGN) {
			if (!(left instanceof Variable)) {
				error("Assignment target is not a lvalue");
			}
			Variable var = (Variable)left;
			lexer.nextToken();
			Expr right = parseAssignExpr();
			return new AssignExpr(var, right);
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
				String identifier = lexer.getValue();
				lexer.nextToken();
				return new Variable(identifier);

			case NUMBER:
				int value = lexer.getIntValue();
				lexer.nextToken();
				return new Constant(value);

			default:
				error("Expected bracket, identifier or number");
				return null;
		}
	}
}

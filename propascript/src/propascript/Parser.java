package propascript;


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
		// not implemented
		return statements;
	}

	private Statement parseStatement() throws ParseException {
		throw new NotImplementedException();
	}

	private Statement parsePrint() throws ParseException {
		throw new NotImplementedException();
	}


	private WhileStatement parseWhile() throws ParseException {
		throw new NotImplementedException();
	}

	private Expr parseExpr() throws ParseException {
		throw new NotImplementedException();
	}

	private Expr parseAssignExpr() throws ParseException {
		throw new NotImplementedException();
	}

	private Expr parseMinusExpr() throws ParseException {
		throw new NotImplementedException();
	}

	private Expr parseMulExpr() throws ParseException {
		throw new NotImplementedException();
	}

	private Expr parseValue() throws ParseException {
		throw new NotImplementedException();
	}
}

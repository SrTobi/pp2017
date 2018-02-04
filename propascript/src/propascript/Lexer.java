package propascript;

import java.text.ParseException;

public class Lexer {
	private final String source;
	private TokenType curToken = null;
	private String curValue = null;
	private int curIndex = 0;
	private int curLine = 1;

	public Lexer(String source) throws ParseException {
		this.source = source + "\n";
		nextToken();
	}

	public void nextToken() throws ParseException {
		if (curIndex >= source.length()) {
			curToken = TokenType.EOF;
			return;
		}
		// skip whitespaces
		char c;
		while ((c = source.charAt(curIndex)) == ' ' || c == '\t' || c == '\r') {
			++curIndex;
		}

		curValue = null;

		switch (c) {
			case '\n':
				++curLine;
			case ';':
				curToken = TokenType.EOS;
				break;
			case '(':
				curToken = TokenType.BRACKET_LEFT;
				break;
			case ')':
				curToken = TokenType.BRACKET_RIGHT;
				break;
			case '{':
				curToken = TokenType.CURLY_LEFT;
				break;
			case '}':
				curToken = TokenType.CURLY_RIGHT;
				break;
			case '-':
				curToken = TokenType.MINUS;
				break;
			case '*':
				curToken = TokenType.MUL;
				break;
			case '=':
				curToken = TokenType.ASSIGN;
				break;
			default:
				// identifier or number
				if (Character.isJavaIdentifierStart(c)) {
					// parse identifier or keyword
					StringBuilder valueBuilder = new StringBuilder("" + c);
					++curIndex;
					while (Character.isJavaIdentifierPart(c = source.charAt(curIndex))) {
						++curIndex;
						valueBuilder.append(c);
					}

					String value = valueBuilder.toString();

					switch (value) {
						case "while":
							curToken = TokenType.WHILE;
							return;
						case "print":
							curToken = TokenType.PRINT;
							return;
						default:
					}
					curValue = value;
					curToken = TokenType.IDENTIFIER;
					return;
				}

				// needs to be a number
				if (!Character.isDigit(c)) {
					throw new ParseException("expected digit but found " + c, getLine());
				}

				StringBuilder valueBuilder = new StringBuilder();
				while (Character.isDigit(c = source.charAt(curIndex))) {
					valueBuilder.append(c);
					++curIndex;
				}
				curValue = valueBuilder.toString();
				curToken = TokenType.NUMBER;
				return;

		}
		++curIndex;
	}

	public TokenType getType() {
		if (curToken == null) {
			throw new IllegalArgumentException("no current type");
		}

		return curToken;
	}

	public String getValue() {
		if (curValue == null) {
			throw new IllegalArgumentException(getType() + " has no value");
		}

		return curValue;
	}

	public int getIntValue() {
		String value = getValue();
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("expected int value");
		}
	}

	public int getLine() {
		return curLine;
	}
}

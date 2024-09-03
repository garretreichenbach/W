package winterpreter;

import winterpreter.token.Token;
import winterpreter.token.TokenType;

import java.util.ArrayList;
import java.util.List;

/**
 * [Description]
 *
 * @author TheDerpGamer
 */
public class Scanner {

	private final WSrc source;
	private final List<Token> tokens = new ArrayList<>();
	private int start;
	private int current;
	private int line = 1;

	Scanner(WSrc source) {
		this.source = source;
	}

	List<Token> scanTokens() {
		while(!isAtEnd()) {
			// We are at the beginning of the next lexeme.
			start = current;
			scanToken();
		}

		tokens.add(new Token(TokenType.EOF, "", null, line));
		return tokens;
	}

	private boolean isAtEnd() {
		return current >= source.toString().length();
	}

	private char advance() {
		return source.toString().charAt(current++);
	}

	private void addToken(TokenType type) {
		String text = source.toString().substring(start, current);
		tokens.add(new Token(type, text, type.literal, line));
	}

	private void addToken(TokenType type, Object literal) {
		String text = source.toString().substring(start, current);
		tokens.add(new Token(type, text, literal, line));
	}

	private void scanToken() {
		char c = advance();
		switch(c) {
			case '(':
				addToken(TokenType.PARENTHESIS_OPEN);
				break;
			case ')':
				addToken(TokenType.PARENTHESIS_CLOSE);
				break;
			case '{':
				addToken(TokenType.BRACE_OPEN);
				break;
			case '}':
				addToken(TokenType.BRACE_CLOSE);
				break;
			case ',':
				addToken(TokenType.COMMA);
				break;
			case '.':
				addToken(TokenType.DOT);
				break;
			case '-':
				if(match('-')) {
					if(match('=')) addToken(TokenType.MINUS_MINUS_EQUAL);
					else addToken(TokenType.MINUS_MINUS);
				} else if(match('=')) {
					addToken(TokenType.MINUS_EQUAL);
				} else {
					if(match('[')) {
						//Get the values between the brackets
						while(peek() != ']' && !isAtEnd()) advance();
						if(isAtEnd()) {
							WInterpreter.error(source, line, "Unterminated iterator.");
							return;
						}
						advance();
						String value = source.toString().substring(start + 1, current - 1);
						//Check if this is a list or a range
						if(value.contains(",")) {
							//List
							String[] values = value.split(",");
							List<Object> list = new ArrayList<>();
							for(String val : values) {
								if(val.startsWith("'") && val.endsWith("'")) list.add(val.substring(1, val.length() - 1));
								else if(val.contains(" -> ")) {
									String[] range = val.split(" -> ");
									if(range.length == 2) {
										try {
											int start = Integer.parseInt(range[0]);
											int end = Integer.parseInt(range[1]);
											for(int i = start; i < end; i ++) list.add(i);
										} catch(NumberFormatException e) {
											WInterpreter.error(source, line, "Invalid range in iterator.");
										}
									} else WInterpreter.error(source, line, "Invalid range in iterator.");
								} else {
									try {
										list.add(Double.parseDouble(val));
									} catch(NumberFormatException e) {
										WInterpreter.error(source, line, "Invalid value in iterator.");
									}
								}
							}
							addToken(TokenType.MINUS, list);
						} else if(value.contains(" -> ")) {
							//Range
							String[] range = value.split(" -> ");
							if(range.length == 2) {
								try {
									double start = Double.parseDouble(range[0]);
									double end = Double.parseDouble(range[1]);
									List<Object> list = new ArrayList<>();
									for(double i = start; i < end; i ++) list.add(i);
									addToken(TokenType.MINUS, list);
								} catch(NumberFormatException e) {
									WInterpreter.error(source, line, "Invalid range in iterator.");
								}
							} else WInterpreter.error(source, line, "Invalid range in iterator.");
						} else WInterpreter.error(source, line, "Invalid iterator.");
					} else addToken(TokenType.MINUS);
				}
				break;
			case '+':
				if(match('+')) {
					if(match('=')) addToken(TokenType.PLUS_PLUS_EQUAL);
					else addToken(TokenType.PLUS_PLUS);
				} else {
					if(match('=')) addToken(TokenType.PLUS_EQUAL);
					else addToken(TokenType.PLUS);
				}
				break;
			case '*':
				if(match('=')) addToken(TokenType.STAR_EQUAL);
				else addToken(TokenType.STAR);
				break;
			case '!':
				if(match('=')) addToken(TokenType.EXCLAMATION_EQUAL);
				else addToken(TokenType.EXCLAMATION);
				break;
			case '=':
				if(match('>')) addToken(TokenType.TRANSFORMER);
				else if(match('=')) addToken(TokenType.EQUAL_EQUAL);
				else addToken(TokenType.EQUAL);
				break;
			case '>':
				if(match('=')) addToken(TokenType.GREATER_EQUAL);
				else addToken(TokenType.GREATER);
				break;
			case '<':
				if(match('=')) addToken(TokenType.LESS_EQUAL);
				else addToken(TokenType.LESS);
				break;
			case '/':
				if(match('=')) addToken(TokenType.SLASH_EQUAL);
				else addToken(TokenType.SLASH);
				break;
			case '%':
				if(match('=')) addToken(TokenType.PERCENT_EQUAL);
				else addToken(TokenType.PERCENT);
				break;
			case '#':
				while(peek() != '\n' && !isAtEnd()) advance();
				break;
			case '?':
				addToken(TokenType.QUESTION);
				break;
			case ':':
				addToken(TokenType.COLON);
				break;
			case ' ':
			case '\r':
			case '\t':
				break;
			case '\n':
				line++;
				break;
			case '"':
				string();
				break;
			default:
				if(isDigit(c)) number();
				else if(isAlpha(c)) identifier();
				else WInterpreter.error(source, line, "Unexpected character '" + c + "'");
				break;
		}
	}

	private void identifier() {
		while(isAlphaNumeric(peek())) advance();
		TokenType type;
		try {
			String text = source.toString().substring(start, current);
			type = TokenType.valueOf(text.toUpperCase());
		} catch(Exception exception) {
			type = TokenType.IDENTIFIER;
		}
		addToken(type);
	}

	private boolean isAlpha(char c) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_';
	}

	private boolean isAlphaNumeric(char c) {
		return isAlpha(c) || isDigit(c);
	}

	private boolean isDigit(char c) {
		return c >= '0' && c <= '9';
	}

	private void number() {
		while(isDigit(peek())) advance();
		if(peek() == '.' && isDigit(peekNext())) {
			do advance();
			while(isDigit(peek()));
		}
		addToken(TokenType.NUMBER, Double.parseDouble(source.toString().substring(start, current)));
	}

	private char peek() {
		if(isAtEnd()) return '\0';
		return source.toString().charAt(current);
	}

	private char peekNext() {
		if(current + 1 >= source.toString().length()) return '\0';
		return source.toString().charAt(current + 1);
	}

	private void string() {
		while(peek() != '"' && !isAtEnd()) {
			if(peek() == '\n') line++;
			advance();
		}
		if(isAtEnd()) {
			WInterpreter.error(source, line, "Unterminated string.");
			return;
		}
		advance();
		String value = source.toString().substring(start + 1, current - 1);
		addToken(TokenType.STRING, value);
	}

	private boolean match(char expected) {
		if(isAtEnd()) return false;
		if(source.toString().charAt(current) != expected) return false;
		current++;
		return true;
	}
}

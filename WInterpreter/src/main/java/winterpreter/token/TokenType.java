package winterpreter.token;

/**
 * Defines the token types for the W language.
 * <br/>Tokens can be combined in a variety of ways for more complex operations.
 * <ul>
 *     <li>
 *         '->' (To) - Represents an iterative process, where the left side is the initial value and the right side is the final value.
 *         <br/>Example: for(i = 0 -> 10) (i starts at 0 and goes to 10)
 *         <br/>Example: for(i = 0 -2> ?) (i starts at 0 and goes to ? in steps of 2)
 *         <br/>To can also work as a boundary for arrays to define their range.
 *         <br/>Example: array = int[0 -> 10] (array is an integer array from 0 to 10)
 *         <br/>Example: array = int[0 -2> ?] (array is an integer array from 0 to ? in steps of 2)
 *         <br/>You can make the range inclusive by adding a '+' after the arrow.
 *         <br/>Example: array = int[0 ->+ 10] (array is an integer array from 0 to 10 inclusive)
 *         <br/>Example: array = int[0 -2>+ ?] (array is an integer array from 0 to ? in steps of 2 inclusive)
 *         <br/>You can also make the range exclusive by adding a '-' after the arrow.
 *         <br/>Example: array = int[0 ->- 10] (array is an integer array from 0 to 10 exclusive)
 *         <br/>Example: array = int[0 -2>- ?] (array is an integer array from 0 to ? in steps of 2 exclusive)
 *         <br/>You can also iterate through string arrays.
 *         <br/>Example: for(i = string['a' -> 'z']) (i starts at 'a' and goes to 'z')
 *         <br/>Example: for(i = 'apple' -> 'banana' : ['apple', 'pear', 'banana']) (i starts at 'apple' and goes to 'banana', requires an array of strings to be passed)
 *     </li>
 *     <li>
 *         ':' (Map) - Maps one value to another, essentially “linking” them. Can be used with both values and types.
 *         <br/>Example: 1 : 2 (1 maps to 2)
 *         <br/>Example: int : string[] (int maps to string array)
 *         <br/>Example: int[] : 1 (int array maps to 1)
 *     </li>
 * </ul>
 */
public enum TokenType {
	//Misc. Syntax
	PARENTHESIS_OPEN('('),
	PARENTHESIS_CLOSE(')'),
	BRACKET_OPEN('['),
	BRACKET_CLOSE(']'),
	BRACE_OPEN('{'),
	BRACE_CLOSE('}'),
	COMMA(','),
	DOT('.'),

	//Operators
	MINUS('-'),
	MINUS_EQUAL("-="),
	MINUS_MINUS("--"),
	MINUS_MINUS_EQUAL("--="),
	PLUS('+'),
	PLUS_EQUAL("+="),
	PLUS_PLUS("++"),
	PLUS_PLUS_EQUAL("++="),
	STAR('*'),
	STAR_EQUAL("*="),
	EOF("EOF"),
	EXCLAMATION("!"),
	EXCLAMATION_EQUAL("!="),
	EQUAL('='),
	EQUAL_EQUAL("=="),
	GREATER('>'),
	GREATER_EQUAL(">="),
	LESS('<'),
	LESS_EQUAL("<="),
	SLASH('/'),
	SLASH_EQUAL("/="),
	PERCENT('%'),
	PERCENT_EQUAL("%="),
	HASH('#'),
	QUESTION('?'),
	COLON(':'),
	ARROW("->"),
	ARROW_STEP("->"),
	TRANSFORMER("=>"),

	//Keywords
	AND("and"),
	OR("or"),
	IF("if"),
	ELSE("else"),
	ELSE_IF("else if"),
	FOR("for"),
	WHILE("while"),
	DO("do"),
	TRY("try"),
	CATCH("catch"),
	FINALLY("finally"),
	THROW("throw"),
	RETURN("return"),
	THIS("this"),

	//Data Types
	STRING(null),
	NUMBER(null),
	IDENTIFIER(null);

	public final Object literal;

	TokenType(Object literal) {
		this.literal = literal;
	}
}

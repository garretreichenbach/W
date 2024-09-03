package winterpreter.logging;

import winterpreter.WFile;

/**
 * [Description]
 */
public class Warning implements LogMessage {

	private final WFile source;
	private final int line;
	private final String message;

	public Warning(WFile source, int line, String message) {
		this.source = source;
		this.line = line;
		this.message = message;
	}

		@Override
	public String toString() {
		return "[Warning]: " + source + " at line " + line + ": " + message;
	}

	public WFile getSource() {
		return source;
	}

	public int getLine() {
		return line;
	}

	public String getMessage() {
		return message;
	}
}

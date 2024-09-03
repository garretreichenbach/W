package winterpreter.logging;

import winterpreter.WFile;
import winterpreter.WSrc;

/**
 * [Description]
 */
public class Error implements LogMessage {

	private final WFile source;
	private final int line;
	private final String message;

	public Error(WSrc source, int line, String message) {
		this.source = source;
		this.line = line;
		this.message = message;
	}

	public Error(WFile source, int line, String message) {
		this.source = source;
		this.line = line;
		this.message = message;
	}

	@Override
	public String toString() {
		return "[Error]: " + source + " at line " + line + ": " + message;
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

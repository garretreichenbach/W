package winterpreter;

import java.io.File;

/**
 * Represents a source (not compiled) W file.
 */
public class WSrc extends WFile {

	private String text;

	public WSrc(File file, String text) {
		super(file);
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}

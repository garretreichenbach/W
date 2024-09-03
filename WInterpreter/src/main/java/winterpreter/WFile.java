package winterpreter;

import java.io.File;

/**
 * [Description]
 *
 * @author TheDerpGamer
 */
public abstract class WFile {

	protected File file;

	public WFile(File file) {
		this.file = file;
	}

	public String getFileName() {
		return file.getName();
	}
}

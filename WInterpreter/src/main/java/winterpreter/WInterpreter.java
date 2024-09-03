package winterpreter;

import winterpreter.logging.Error;
import winterpreter.logging.LogMessage;
import winterpreter.logging.Warning;
import winterpreter.token.Token;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/**
 * [Description]
 *
 * @author TheDerpGamer
 */
public class WInterpreter {

	static List<Error> errors = new LinkedList<>();
	static List<Warning> warnings = new LinkedList<>();

	public static void main(String[] args) throws IOException {
		if(args.length > 1) {
			System.out.println("Usage: wint [script]");
			System.exit(64);
		} else if(args.length == 1) {
			run(new File(args[0]));
		} else {
			runPrompt();
		}
	}

	private static void runPrompt() throws IOException {
		InputStreamReader input = new InputStreamReader(System.in, StandardCharsets.UTF_8);
		BufferedReader reader = new BufferedReader(input);

	}

	private static void run(File source) {
		WSrc file = new WSrc(source, readSource(source));
		Scanner scanner = new Scanner(file);
		List<Token> tokens = scanner.scanTokens();
		for(Token token : tokens) {
			System.out.println(token);
		}
	}

	private static String readSource(File source) {
		try {
			byte[] bytes = Files.readAllBytes(Paths.get(source.getPath()));
			return new String(bytes, Charset.defaultCharset());
		} catch(IOException exception) {
			exception.printStackTrace();
			return null;
		}
	}

	static void error(WFile source, int line, String message) {
		Error error = new Error(source, line, message);
		report(error);
		errors.add(error);
	}

	static void warning(WFile source, int line, String message) {
		Warning warning = new Warning(source, line, message);
		report(warning);
		warnings.add(warning);
	}

	private static void report(LogMessage message) {
		System.err.println(message);
		//Todo: Handle user-defined error handling and logging
	}
}

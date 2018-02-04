package propascript;

import jasmin.Main;
import propascript.ast.Program;
import propascript.visitors.CodeGenVisitor;
import propascript.visitors.PrintVisitor;
import propascript.visitors.VariableCollector;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Compiler {
	private final boolean printAst;
	private final boolean terminalMode;
	private final boolean verbose;

	public Compiler(boolean printAst, boolean terminalMode, boolean verbose) {
		this.printAst = printAst;
		this.terminalMode = terminalMode;
		this.verbose = verbose;
	}

	private void verbose(String message) {
		if (verbose) {
			System.out.println(message);
		}
	}

	public void compile(String className,
                        Path sourcePath,
                        Path jasmOutputPath,
                        Path classOutputPath) {
		// read file
		String source = null;
		try {
			verbose("Read file " + sourcePath + "...");
			source = readFile(sourcePath);
			verbose(" -> Success (" + source.length() + " characters read)");
		} catch (IOException e) {
			System.err.println("Failed to read file " + sourcePath);
			return;
		}

		// lex & parse
		Program ast = null;
		try {
			Lexer lexer = new Lexer(source);
			Parser parser = new Parser(lexer);
			verbose("Parsing...");
			ast = parser.parse();
			verbose(" -> Success");
		} catch (ParseException e) {
			System.err.println("Error in line " + e.getErrorOffset() + ": " + e.getMessage());
			return;
		}

		if (printAst) {
			PrintVisitor printer = new PrintVisitor();
			ast.apply(printer);
			System.out.println(printer.toString());
			return;
		}

		// collect variables
		verbose("Collect variables...");
		VariableCollector varCollector = new VariableCollector();
		ast.apply(varCollector);
		Map<String, Integer> vars = varCollector.getVars();
		verbose(" -> Done (Found " + vars.size() + " variable)");


		// generate jasm
		CodeGenVisitor codeGen = new CodeGenVisitor(className, vars, true);
		verbose("Generate jasm...");
		ast.apply(codeGen);
		String jasm = codeGen.toString();
		verbose(" -> Success");

		if (terminalMode) {
			System.out.println(jasm);
		} else {
			try {
				writeFile(jasmOutputPath, jasm);
			} catch (IOException e) {
				System.err.println("Failed to write jasm to " + jasmOutputPath);
				System.err.println("  Reason: "+ e.getMessage());
				return;
			}

			verbose("Start jasmin...");
			Main.main(new String[] { jasmOutputPath.toString() });
			verbose("Done.");
		}
	}

	private static void writeFile(Path path, String content) throws IOException {
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(path.toFile()), "utf-8"))) {
			writer.write(content);
		}
	}

	static String readFile(Path path)
			throws IOException
	{
		byte[] encoded = Files.readAllBytes(path);
		return new String(encoded, "utf-8");
	}

	private static Path withExtension(Path path, String ext) {
		String name = path.getFileName().toString();
		int dot = name.lastIndexOf(".");
		if (dot >= 0) {
			name = name.substring(0, dot);
		}
		return path.resolveSibling(name + ext);
	}

	public static void main(String... args) {
		Set<String> options = Arrays.stream(args).filter((arg) -> arg.startsWith("-")).collect(Collectors.toSet());
		String[] nonoptions = Arrays.stream(args).filter((arg) -> !arg.startsWith("-")).toArray(String[]::new);

		// nonoptions
		if (nonoptions.length <= 0 || options.contains("-h")) {
			System.out.println("Usage:");
			System.out.println("  prapac <input file> [<output file>] [<output class file>]");
			System.out.println();
			System.out.println("Options:");
			System.out.println("  -h     This help");
			System.out.println("  -ast   Prints the parsed ast");
			System.out.println("  -tty   Only output jasm on the terminal");
			System.out.println("  -v     Verbose mode");
			return;
		}
		Path sourcePath = Paths.get(nonoptions[0]);

		Path jasmOutput = withExtension(sourcePath, ".jasm");
		if (nonoptions.length >= 2) {
			jasmOutput = Paths.get(nonoptions[1]);
		}

		Path classOutput = withExtension(sourcePath, ".class");
		if (nonoptions.length >= 3) {
			jasmOutput = Paths.get(nonoptions[2]);
		}

		// options
		boolean printAst = options.contains("-ast");
		boolean terminalMode = options.contains("-tty");
		boolean verbose = options.contains("-v");

		String className = classOutput.getFileName().toString().replaceFirst("\\.[^.]+$", "");
		Compiler compiler = new Compiler(printAst, terminalMode, verbose);
		compiler.compile(className, sourcePath, jasmOutput, classOutput);
	}
}

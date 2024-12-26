import ast.ProgramNode;
import dataModel.Token;
import interpreter.Interpreter;
import lexer.Lexer;
import org.junit.Test;
import parser.Parser;
import reader.SourceReader;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

public class SimpleInterpreterTest {
    @Test
    public void testProgramFromFile() throws Exception {
        // Suppose the file is at "src/test/resources/example_program.txt"
        // or some known location on your system.
        String filePath = "src/test/resources/code.txt";

        // 1) Read the file content
        SourceReader sr = new SourceReader(filePath);
        String programText = sr.read();

        // 2) Tokenize
        Lexer lexer = new Lexer(programText);
        List<Token> tokens = lexer.tokenize();

        // 3) Parse
        Parser parser = new Parser(tokens);
        ProgramNode ast = parser.parseProgram();

        // 4) Interpret (capture output)
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        Interpreter interpreter = new Interpreter();
        interpreter.interpret(ast);

        // restore System.out
        System.setOut(originalOut);

        // Verify output
        String[] lines = outContent.toString().trim().split("\\r?\\n");
        String[] expected = {
                "1",
                "2",
                "3",
                "3",
                "2",
                "null",
                "1"
        };
        assertArrayEquals(expected, lines);
    }
}

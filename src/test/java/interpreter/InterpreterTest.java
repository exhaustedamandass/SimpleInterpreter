package interpreter;

import ast.ProgramNode;
import lexer.Lexer;
import org.junit.Test;
import parser.Parser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class InterpreterTest {
    @Test
    public void testPrintNull() {
        String input = "print x\n";  // x isn't defined -> should print null
        // capture console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // run
        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer.tokenize());
        ProgramNode ast = parser.parseProgram();
        Interpreter interpreter = new Interpreter();
        interpreter.interpret(ast);

        // restore System.out
        System.setOut(System.out);
        String output = outContent.toString().trim();
        assertEquals("null", output);
    }

    @Test
    public void testAssignmentAndPrint() {
        String input = "x = 42\nprint x\n";
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // run
        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer.tokenize());
        ProgramNode ast = parser.parseProgram();
        Interpreter interpreter = new Interpreter();
        interpreter.interpret(ast);

        // restore
        System.setOut(System.out);
        String output = outContent.toString().trim();
        // should be "42"
        assertEquals("42", output);
    }

    @Test
    public void testScopeBehavior() {
        // from the example in the problem statement
        String input =
                "x = 1\n" +
                        "print x\n" +
                        "scope {\n" +
                        "  x = 2\n" +
                        "  print x\n" +
                        "  scope {\n" +
                        "    x = 3\n" +
                        "    y = x\n" +
                        "    print x\n" +
                        "    print y\n" +
                        "  }\n" +
                        "  print x\n" +
                        "  print y\n" +
                        "}\n" +
                        "print x\n";

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // run
        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer.tokenize());
        ProgramNode ast = parser.parseProgram();
        Interpreter interpreter = new Interpreter();
        interpreter.interpret(ast);

        // restore
        System.setOut(System.out);
        String output = outContent.toString().trim();

        // Expected:
        // 1
        // 2
        // 3
        // 3
        // 2
        // null
        // 1
        String[] lines = output.split("\\r?\\n");
        assertArrayEquals(new String[] {
                "1", "2", "3", "3", "2", "null", "1"
        }, lines);
    }
}


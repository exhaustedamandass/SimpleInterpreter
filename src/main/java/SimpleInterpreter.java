import ast.ProgramNode;
import dataModel.Token;
import interpreter.Interpreter;
import lexer.Lexer;
import parser.Parser;
import reader.SourceReader;

import java.io.IOException;
import java.util.List;

public class SimpleInterpreter {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java demo.interpreter.Main <filename>");
            System.exit(1);
        }

        String filename = args[0];
        SourceReader reader = new SourceReader(filename);

        try {
            // 1) Read source file
            String programText = reader.read();

            // 2) Lex
            Lexer lexer = new Lexer(programText);
            List<Token> tokens = lexer.tokenize();

            // 3) Parse
            Parser parser = new Parser(tokens);
            ProgramNode ast = parser.parseProgram();

            // 4) Interpret
            Interpreter interpreter = new Interpreter();
            interpreter.interpret(ast);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

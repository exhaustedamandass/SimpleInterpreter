package parser;

import ast.*;
import dataModel.Token;
import lexer.Lexer;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void testAssignmentAndPrint() {
        String input = "x = 42\nprint x\n";
        // 1) tokenize
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        // 2) parse
        Parser parser = new Parser(tokens);
        ProgramNode program = parser.parseProgram();

        List<ASTNode> stmts = program.getStatements();
        assertEquals(2, stmts.size());

        // First statement: assignment
        assertTrue(stmts.get(0) instanceof AssignmentNode);
        AssignmentNode assign = (AssignmentNode) stmts.get(0);
        assertEquals("x", assign.getLeftVar());
        assertNull(assign.getRightVar());
        assertEquals(42, assign.getIntValue().longValue());

        // Second statement: print
        assertTrue(stmts.get(1) instanceof PrintNode);
        PrintNode pn = (PrintNode) stmts.get(1);
        assertEquals("x", pn.getVarName());
    }

    @Test
    public void testScopeParsing() {
        String input = "scope {\n  x = 1\n}\n";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        Parser parser = new Parser(tokens);
        ProgramNode program = parser.parseProgram();

        List<ASTNode> stmts = program.getStatements();
        assertEquals(1, stmts.size());
        assertTrue(stmts.get(0) instanceof ScopeNode);

        ScopeNode scope = (ScopeNode) stmts.get(0);
        assertEquals(1, scope.getStatements().size());
        assertTrue(scope.getStatements().get(0) instanceof AssignmentNode);
    }
}

package parser;

import ast.*;
import dataModel.Token;
import dataModel.TokenType;

import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int current;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.current = 0;
    }

    public ProgramNode parseProgram() {
        ProgramNode program = new ProgramNode();
        while (!isAtEnd() && peek().getType() != TokenType.EOF) {
            ASTNode stmt = parseStatement();
            if (stmt != null) {
                program.addStatement(stmt);
            }
            // We skip any extra newlines
            while (!isAtEnd() && peek().getType() == TokenType.NEWLINE) {
                advance();
            }
        }
        return program;
    }

    private ASTNode parseStatement() {
        Token token = peek();

        switch (token.getType()) {
            case IDENTIFIER:
                // Potentially an assignment
                return parseAssignment();

            case PRINT:
                // print statement
                return parsePrint();

            case SCOPE:
                // scope { ... }
                return parseScope();// skip

            default:
                // skip or handle error
                advance();
                return null;
        }
    }

    private ASTNode parseAssignment() {
        // We expect something like x = ...
        Token leftVar = advance(); // x
        consume(TokenType.EQUALS, "Expected '=' after variable name.");

        // Next could be a number or an identifier
        Token next = peek();
        if (next.getType() == TokenType.NUMBER) {
            Token numberToken = advance();
            // x = 42
            return new AssignmentNode(
                    leftVar.getText(),
                    null,
                    Integer.valueOf(numberToken.getText())
            );
        } else if (next.getType() == TokenType.IDENTIFIER) {
            Token rightVar = advance();
            // x = y
            return new AssignmentNode(
                    leftVar.getText(),
                    rightVar.getText(),
                    null
            );
        } else {
            // error or treat as null assignment?
            // Let's just interpret that as x = null
            return new AssignmentNode(
                    leftVar.getText(),
                    null,
                    null
            );
        }
    }

    private ASTNode parsePrint() {
        // expect "print x"
        advance(); // consume "print"
        Token varToken = consume(TokenType.IDENTIFIER, "Expected identifier after 'print'.");
        return new PrintNode(varToken.getText());
    }

    private ASTNode parseScope() {
        // we have 'scope' token
        advance(); // consume 'scope'
        // next should be '{'
        consume(TokenType.LBRACE, "Expected '{' after 'scope'");

        ScopeNode scopeNode = new ScopeNode();

        // read statements until we see '}'
        while (!isAtEnd() && peek().getType() != TokenType.RBRACE) {
            ASTNode stmt = parseStatement();
            if (stmt != null) {
                scopeNode.addStatement(stmt);
            }
            // skip newlines
            while (!isAtEnd() && peek().getType() == TokenType.NEWLINE) {
                advance();
            }
        }

        // consume '}'
        consume(TokenType.RBRACE, "Expected '}' to close scope.");
        return scopeNode;
    }

    // Helper methods

    private Token peek() {
        return tokens.get(current);
    }

    private Token advance() {
        Token t = tokens.get(current);
        current = Math.min(current + 1, tokens.size() - 1);
        return t;
    }

    private Token consume(TokenType type, String errMessage) {
        if (peek().getType() == type) {
            return advance();
        }
        // In real code, throw an exception or handle gracefully
        throw new RuntimeException(errMessage + " Found: " + peek());
    }

    private boolean isAtEnd() {
        return current >= tokens.size() || peek().getType() == TokenType.EOF;
    }
}

package lexer;

import dataModel.Token;
import dataModel.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String input;
    private int currentPos;

    public Lexer(String input) {
        this.input = input;
        this.currentPos = 0;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (!isAtEnd()) {
            char c = peek();

            // Skip whitespace that is not a newline
            if (Character.isWhitespace(c) && c != '\n' && c != '\r') {
                advance();
                continue;
            }

            if (c == '\n' || c == '\r') {
                tokens.add(new Token(TokenType.NEWLINE, "\\n"));
                advance();
            }
            else if (c == '=') {
                tokens.add(new Token(TokenType.EQUALS, "="));
                advance();
            }
            else if (c == '{') {
                tokens.add(new Token(TokenType.LBRACE, "{"));
                advance();
            }
            else if (c == '}') {
                tokens.add(new Token(TokenType.RBRACE, "}"));
                advance();
            }
            else {
                // Potentially an identifier (including 'print', 'scope') or a number
                if (Character.isDigit(c) || (c == '-' && Character.isDigit(peekNext()))) {
                    // Number (consider optional minus sign)
                    String number = readNumber();
                    tokens.add(new Token(TokenType.NUMBER, number));
                } else if (Character.isLetter(c)) {
                    // Identifier or keywords (print, scope)
                    String word = readIdentifier();
                    switch (word) {
                        case "print":
                            tokens.add(new Token(TokenType.PRINT, word));
                            break;
                        case "scope":
                            tokens.add(new Token(TokenType.SCOPE, word));
                            break;
                        default:
                            tokens.add(new Token(TokenType.IDENTIFIER, word));
                            break;
                    }
                } else {
                    // We can decide to handle error or skip unknown
                    advance();
                }
            }
        }

        tokens.add(new Token(TokenType.EOF, "EOF"));
        return tokens;
    }

    private boolean isAtEnd() {
        return currentPos >= input.length();
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return input.charAt(currentPos);
    }

    private char peekNext() {
        if (currentPos + 1 >= input.length()) return '\0';
        return input.charAt(currentPos + 1);
    }

    private char advance() {
        return input.charAt(currentPos++);
    }

    private String readNumber() {
        StringBuilder sb = new StringBuilder();
        if (peek() == '-') {
            sb.append(advance()); // consume '-'
        }
        while (!isAtEnd() && Character.isDigit(peek())) {
            sb.append(advance());
        }
        return sb.toString();
    }

    private String readIdentifier() {
        StringBuilder sb = new StringBuilder();
        while (!isAtEnd() && Character.isLetterOrDigit(peek())) {
            sb.append(advance());
        }
        return sb.toString();
    }
}
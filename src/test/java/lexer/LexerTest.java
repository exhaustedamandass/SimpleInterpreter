package lexer;

import dataModel.Token;
import dataModel.TokenType;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class LexerTest {

    @Test
    public void testSimpleTokenize() {
        String input = "x = 42\nprint x\n";
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        // We expect something like:
        // IDENTIFIER(x), EQUALS(=), NUMBER(42), NEWLINE, PRINT, IDENTIFIER(x), NEWLINE, EOF
        assertEquals(8, tokens.size());

        assertEquals(TokenType.IDENTIFIER, tokens.get(0).getType());
        assertEquals("x", tokens.get(0).getText());

        assertEquals(TokenType.EQUALS, tokens.get(1).getType());
        assertEquals("=", tokens.get(1).getText());

        assertEquals(TokenType.NUMBER, tokens.get(2).getType());
        assertEquals("42", tokens.get(2).getText());

        assertEquals(TokenType.NEWLINE, tokens.get(3).getType());

        assertEquals(TokenType.PRINT, tokens.get(4).getType());
        assertEquals("print", tokens.get(4).getText());

        assertEquals(TokenType.IDENTIFIER, tokens.get(5).getType());
        assertEquals("x", tokens.get(5).getText());

        assertEquals(TokenType.NEWLINE, tokens.get(6).getType());
        assertEquals(TokenType.EOF, tokens.get(7).getType());
    }
}

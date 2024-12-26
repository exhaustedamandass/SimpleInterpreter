package dataModel;

public enum TokenType {
    IDENTIFIER,      // e.g. x, y, foo
    NUMBER,          // e.g. 42, 13
    EQUALS,          // =
    PRINT,           // print
    SCOPE,           // scope
    LBRACE,          // {
    RBRACE,          // }
    NEWLINE,         // newline
    EOF              // End of file/input
}

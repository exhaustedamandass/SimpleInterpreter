# Simple Language Interpreter

JetBrains test task

## Project structure

```

SimpleInterpreter
| .idea/
| src/
|---- main/
|     | java/
|         | ast/
|             |---- AssignmentNode.java
|             |---- ASTNode.java
|             |---- PrintNode.java
|             |---- ProgramNode.java
|             |---- ScopeNode.java
|         | dataModel/
|             |---- Token.java
|             |---- TokenType.java
|         | interpreter/
|             |---- Interpreter.java
|         | lexer/
|             |---- Lexer.java
|         | parser/
|             |---- Parser.java
|         | reader/
|             |---- SourceReader.java
|         |---- SimpleInterpreter.java
|     | resources/
|         |---- code.txt
| test/
|---- java/
|     | interpreter/
|         |---- InterpreterTest.java
|     | lexer/
|         |---- LexerTest.java
|     | parser/
|         |---- ParserTest.java
|     |---- SimpleInterpreterTest.java
|---- resources/
| target/
|---- .gitignore
|---- pom.xml
|---- README.md

```

## Main parts

1. Lexer (tokenizes the input)
2. Parser (produces an AST)
3. Interpreter (executes the AST)

## Usage

1. Configure the run configuration
2. As the argument to the run configuration, specify path to your code
3. Click Run

## Testing

1. Run mvn test in the terminal


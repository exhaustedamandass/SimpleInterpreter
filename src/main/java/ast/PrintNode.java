package ast;

public class PrintNode implements ASTNode {
    private final String varName;

    public PrintNode(String varName) {
        this.varName = varName;
    }

    public String getVarName() {
        return varName;
    }
}
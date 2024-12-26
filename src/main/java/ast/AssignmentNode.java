package ast;

public class AssignmentNode implements ASTNode {
    private final String leftVar;
    private final String rightVar;  // null if it’s an integer assignment
    private final Integer intValue; // null if it’s a var-to-var assignment

    public AssignmentNode(String leftVar, String rightVar, Integer intValue) {
        this.leftVar = leftVar;
        this.rightVar = rightVar;
        this.intValue = intValue;
    }

    public String getLeftVar() {
        return leftVar;
    }

    public String getRightVar() {
        return rightVar;
    }

    public Integer getIntValue() {
        return intValue;
    }
}

package interpreter;

import ast.*;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Interpreter {
    // Each element in the stack is a map representing variables in that scope.
    // The top of the stack is the current scope.
    private final Deque<Map<String, Integer>> scopeStack = new ArrayDeque<>();

    public Interpreter() {
        // push the global scope
        scopeStack.push(new HashMap<>());
    }

    public void interpret(ProgramNode program) {
        for (ASTNode stmt : program.getStatements()) {
            execute(stmt);
        }
    }

    private void execute(ASTNode node) {
        if (node instanceof AssignmentNode) {
            executeAssignment((AssignmentNode) node);
        } else if (node instanceof PrintNode) {
            executePrint((PrintNode) node);
        } else if (node instanceof ScopeNode) {
            executeScope((ScopeNode) node);
        }
    }

    private void executeAssignment(AssignmentNode assignment) {
        String leftVar = assignment.getLeftVar();
        String rightVar = assignment.getRightVar();
        Integer intValue = assignment.getIntValue();

        if (rightVar != null) {
            // x = y
            Integer val = lookupVariable(rightVar);
            setVariable(leftVar, val);
        } else if (intValue != null) {
            // x = 42
            setVariable(leftVar, intValue);
        } else {
            // x = null
            setVariable(leftVar, null);
        }
    }

    private void executePrint(PrintNode print) {
        Integer val = lookupVariable(print.getVarName());
        if (val == null) {
            System.out.println("null");
        } else {
            System.out.println(val);
        }
    }

    private void executeScope(ScopeNode scopeNode) {
        // push a new scope
        scopeStack.push(new HashMap<>());
        // execute statements inside
        for (ASTNode stmt : scopeNode.getStatements()) {
            execute(stmt);
        }
        // pop scope
        scopeStack.pop();
    }

    private void setVariable(String varName, Integer val) {
        // set in the topmost scope
        scopeStack.peek().put(varName, val);
    }

    private Integer lookupVariable(String varName) {
        // search from top to bottom
        for (Map<String, Integer> scope : scopeStack) {
            if (scope.containsKey(varName)) {
                return scope.get(varName);
            }
        }
        return null; // not found
    }
}

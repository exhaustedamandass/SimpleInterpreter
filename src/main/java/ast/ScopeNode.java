package ast;

import java.util.ArrayList;
import java.util.List;

public class ScopeNode implements ASTNode {
    private final List<ASTNode> statements = new ArrayList<>();

    public void addStatement(ASTNode stmt) {
        statements.add(stmt);
    }

    public List<ASTNode> getStatements() {
        return statements;
    }
}
